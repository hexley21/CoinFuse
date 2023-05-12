package com.hxl.coinfuse.ui.fragments.coins.main;

import static com.hxl.coinfuse.ui.fragments.navigation.NavigationFragment.coinArgKey;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.paging.LoadState;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.search.SearchView;
import com.hxl.coinfuse.R;
import com.hxl.coinfuse.base.BaseFragment;
import com.hxl.coinfuse.databinding.FragmentCoinMenuBinding;
import com.hxl.coinfuse.ui.fragments.coins.main.adapter.CoinAdapter;
import com.hxl.coinfuse.ui.fragments.coins.main.adapter.CoinSearchAdapter;
import com.hxl.coinfuse.util.EspressoIdlingResource;
import com.hxl.presentation.viewmodels.CoinsMenuViewModel;

import java.util.ArrayList;
import java.util.function.Function;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;

@AndroidEntryPoint
public class CoinsMenuFragment extends BaseFragment<FragmentCoinMenuBinding, CoinsMenuViewModel> {
    @Override
    protected FragmentCoinMenuBinding setViewBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentCoinMenuBinding.inflate(inflater, container, false);
    }

    @Override
    protected CoinsMenuViewModel setViewModel() {
        return new ViewModelProvider(this).get(CoinsMenuViewModel.class);
    }

    private static final String TAG = "CoinsMenuFragment";


    private final Function<Bundle, Void> insertSearchFunction = bundle -> {
        insertSearchQuery(bundle.getString(coinArgKey));
        return null;
    };
    
    private CoinAdapter coinMenuAdapter;
    private CoinSearchAdapter coinSearchAdapter;
    private CoinSearchAdapter searchHistoryCoinsAdapter;
    private SwipeRefreshLayout refreshLayout;
    private View loadingView;
    private OnBackPressedCallback callback;
    private NavController navController;
    private int pbVisibility = View.VISIBLE;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        coinMenuAdapter = new CoinAdapter();
        coinSearchAdapter = new CoinSearchAdapter(insertSearchFunction);
        searchHistoryCoinsAdapter =  new CoinSearchAdapter(insertSearchFunction);
    }

    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        refreshLayout = binding.srlCoins;
        loadingView = binding.shimmerCoins;
        loadingView.setVisibility(pbVisibility);

        if (navController == null) {
            navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_main);
            coinMenuAdapter.setNavController(navController);
            coinSearchAdapter.setNavController(navController);
            searchHistoryCoinsAdapter.setNavController(navController);
        }
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

        coinsRv.setAdapter(coinMenuAdapter);
        searchRv.setAdapter(coinSearchAdapter);
        historyRv.setAdapter(searchHistoryCoinsAdapter);

        coinMenuAdapter.addLoadStateListener(combinedLoadStates -> {
            if (combinedLoadStates.getRefresh() instanceof LoadState.NotLoading) {
                loadingView.setVisibility(View.GONE);
                refreshLayout.setRefreshing(false);
            }
            return null;
        });

        if (coinMenuAdapter.getItemCount() == 0) {
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
        vm.coinStream.observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                pagingData -> coinMenuAdapter.submitData(getLifecycle(), pagingData),
                e -> {
                    Log.e(TAG, "updateCoins: failed", e);

                    loadingView.setVisibility(View.GONE);
                    refreshLayout.setRefreshing(false);

                    visibilityCoinError(e.getMessage());
                },
                () -> {},
                compositeDisposable
        );
    }

    private void searchCoins(String query) {
        EspressoIdlingResource.increment();
        vm.searchCoins(query)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        coins -> {
                            coinSearchAdapter.setList(coins);

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
        vm.getCoinsBySearchHistory()
                .observeOn(AndroidSchedulers.mainThread())
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
        coinSearchAdapter.setList(new ArrayList<>());
    }

    private void visibilityCoinError(String error) {
        binding.shimmerCoins.setVisibility(View.GONE);
        binding.textCoinError.setVisibility(View.VISIBLE);
        binding.iconCoinError.setVisibility(View.VISIBLE);
        binding.setCoinError(error);
    }

    private void setVisibilityErrorRefresh() {
        binding.textCoinError.setVisibility(View.GONE);
        binding.iconCoinError.setVisibility(View.GONE);
    }
}