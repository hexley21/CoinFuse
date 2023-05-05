package com.hxl.cryptonumismatist.ui.fragments.coins.main;

import static com.hxl.cryptonumismatist.ui.fragments.navigation.NavigationFragment.coinArgKey;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.search.SearchView;
import com.hxl.cryptonumismatist.R;
import com.hxl.cryptonumismatist.base.BaseFragment;
import com.hxl.cryptonumismatist.databinding.FragmentCoinsMenuBinding;
import com.hxl.cryptonumismatist.util.EspressoIdlingResource;
import com.hxl.presentation.viewmodels.CoinsMenuViewModel;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;

@AndroidEntryPoint
public class CoinsMenuFragment extends BaseFragment<FragmentCoinsMenuBinding, CoinsMenuViewModel> {
    @Override
    protected FragmentCoinsMenuBinding setViewBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentCoinsMenuBinding.inflate(inflater, container, false);
    }

    @Override
    protected CoinsMenuViewModel setViewModel() {
        return new ViewModelProvider(this).get(CoinsMenuViewModel.class);
    }

    private static final String TAG = "CoinsMenuFragment";
    @Inject
    CoinAdapter coinsMenuAdapter;
    @Inject
    CoinsSearchAdapter coinsSearchAdapter;
    @Inject
    CoinsSearchAdapter searchHistoryCoinsAdapter;
    SwipeRefreshLayout refreshLayout;
    ProgressBar progressBar;
    OnBackPressedCallback callback;
    NavController navController;
    private int pbVisibility = View.VISIBLE;

    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        refreshLayout = binding.srlCoins;
        progressBar = binding.pbCoins;
        progressBar.setVisibility(pbVisibility);
        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_main);
        callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                binding.searchView.hide();
            }
        };
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        RecyclerView coinsRv = binding.rvCoins;
        RecyclerView searchRv = binding.rvCoinSearch;
        RecyclerView historyRv = binding.rvCoinHistory;

        coinsMenuAdapter.setNavController(navController);

        Function<Bundle, Void> onClick = bundle -> {
            navController.navigate(R.id.navigationFragment_to_coinDetailsFragment, bundle);
            insertSearchQuery(bundle.getString(coinArgKey));
            return null;
        };

        coinsSearchAdapter.setOnClick(onClick);
        coinsSearchAdapter.setNavController(navController);
        searchHistoryCoinsAdapter.setOnClick(onClick);
        searchHistoryCoinsAdapter.setNavController(navController);

        coinsRv.setLayoutManager(new LinearLayoutManager(requireContext()));
        coinsRv.setAdapter(coinsMenuAdapter);
        searchRv.setLayoutManager(new LinearLayoutManager(requireContext()));
        searchRv.setAdapter(coinsSearchAdapter);
        historyRv.setLayoutManager(new LinearLayoutManager(requireContext()));
        historyRv.setAdapter(searchHistoryCoinsAdapter);

        if (!(coinsMenuAdapter.getItemCount() > 0)) {
            updateCoins();
        }
        getSearchHistory();
        pbVisibility = View.GONE;

        binding.searchView.getEditText().setOnEditorActionListener((v, actionId, event) -> {
            searchCoins(v.getText().toString());
            return false;
        });
        binding.srlCoins.setOnRefreshListener(() -> {
            compositeDisposable.clear();
            setVisibilityErrorRefresh();
            updateCoins();
        });

        ImageButton searchClearBtn = binding.searchView.findViewById(com.google.android.material.R.id.search_view_clear_button);
        searchClearBtn.setOnClickListener(l -> {
            binding.searchView.clearText();
            binding.searchView.clearFocusAndHideKeyboard();
            clearSearchRvData();
        });

        binding.searchView.addTransitionListener((searchView, previousState, newState) -> {
            if (newState == SearchView.TransitionState.SHOWING) {
                requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), callback);
                Log.d(TAG, "searchView.transitionListener: back callback - added");
            }
            else if (newState == SearchView.TransitionState.HIDING) {
                callback.remove();
                clearSearchRvData();
                Log.d(TAG, "searchView.addTransitionListener: back callback - removed");
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (binding.searchView.isShowing()) {
            requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), callback);
            Log.d(TAG, "onResume: back callback - added");
        }
    }

    private void updateCoins() {
        vm.flowable.subscribe(
                pagingData -> {
                    coinsMenuAdapter.submitData(getLifecycle(), pagingData);

                    progressBar.setVisibility(View.GONE);
                    refreshLayout.setRefreshing(false);
                },
                e -> {
                    Log.e(TAG, "updateCoins: failed", e);

                    visibilityCoinError(e.getMessage());
                },
                () -> {
                    progressBar.setVisibility(View.GONE);
                    refreshLayout.setRefreshing(false);
                },
                compositeDisposable
        );
    }

    private void searchCoins(String query) {
        EspressoIdlingResource.increment();
        vm.searchCoins(query)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        coins -> {
                            coinsSearchAdapter.setList(coins);

                            EspressoIdlingResource.decrement();
                        },
                        e -> {
                            Log.e(TAG, e.getMessage(), e);

                            EspressoIdlingResource.decrement();
                        },
                        compositeDisposable
                );
    }

    private void getSearchHistory() {
        EspressoIdlingResource.increment();
        vm.getCoinSearchHistory()
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(h -> {
                    List<String> queries = h.stream().map(x -> x.value).collect(Collectors.toList());
                    return vm.getCoins(queries).map(c -> {
                        c.sort(Comparator.comparingLong(coin -> {
                            int index = queries.indexOf(coin.id);
                            return index >= 0 ? -h.get(index).timestamp : Long.MIN_VALUE;
                        }));
                        return c;
                    });
                })
                .subscribe(
                        coins -> {
                            searchHistoryCoinsAdapter.setList(coins);
                            EspressoIdlingResource.decrement();
                        },
                        e -> {
                            Log.e(TAG, e.getMessage(), e);

                            EspressoIdlingResource.decrement();
                        },
                        compositeDisposable
                );
    }

    private void insertSearchQuery(String query) {
        vm.insertCoinSearchQuery(query)
                .subscribe(
                        () -> Log.d(TAG, "insertSearchQuery: was successful"),
                        e -> Log.e(TAG, "insertSearchQuery: failed", e),
                        compositeDisposable
                );
    }

    private void clearSearchRvData() {
        coinsSearchAdapter.setList(new ArrayList<>());
    }

    private void visibilityCoinError(String error) {
        binding.pbCoins.setVisibility(View.GONE);
        binding.textCoinError.setVisibility(View.VISIBLE);
        binding.iconCoinError.setVisibility(View.VISIBLE);
        binding.setCoinError(error);
    }

    private void setVisibilityErrorRefresh() {
        binding.textCoinError.setVisibility(View.GONE);
        binding.iconCoinError.setVisibility(View.GONE);
    }
}