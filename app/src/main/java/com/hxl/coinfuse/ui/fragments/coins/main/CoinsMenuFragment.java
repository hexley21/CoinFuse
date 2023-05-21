package com.hxl.coinfuse.ui.fragments.coins.main;

import static com.hxl.coinfuse.ui.fragments.navigation.NavigationFragment.coinArgKey;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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

import com.google.android.material.search.SearchView;
import com.hxl.coinfuse.R;
import com.hxl.coinfuse.base.BaseFragment;
import com.hxl.coinfuse.databinding.FragmentCoinMenuBinding;
import com.hxl.coinfuse.ui.fragments.coins.main.adapter.CoinAdapter;
import com.hxl.coinfuse.ui.fragments.coins.main.adapter.CoinSearchAdapter;
import com.hxl.coinfuse.util.UiUtils;
import com.hxl.presentation.livedata.DataState;
import com.hxl.presentation.viewmodels.CoinsMenuViewModel;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.function.Consumer;

import dagger.hilt.android.AndroidEntryPoint;

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

    private final Consumer<Bundle> insertSearchFunction = bundle -> vm.insertCoinSearchQuery(bundle.getString(coinArgKey));
    
    private CoinAdapter coinMenuAdapter;
    private CoinSearchAdapter coinSearchAdapter;
    private CoinSearchAdapter searchHistoryCoinsAdapter;
    private OnBackPressedCallback callback;
    private NavController navController;
    private boolean hasLoaded = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        coinMenuAdapter = new CoinAdapter();
        coinSearchAdapter = new CoinSearchAdapter(insertSearchFunction);
        searchHistoryCoinsAdapter =  new CoinSearchAdapter(insertSearchFunction, () -> vm.fetchCoinSearchHistory());
    }

    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        initPage();

        if (navController == null) {
            navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_main);
            coinMenuAdapter.setNavController(navController);
            coinSearchAdapter.setNavController(navController);
            searchHistoryCoinsAdapter.setNavController(navController);
        }

        if (!vm.getCurrentCoins().hasObservers()) {
            vm.getCurrentCoins().observe(requireActivity(), coins -> {
                if (coins.getState() == DataState.SUCCESS) {
                    coinMenuAdapter.submitData(getLifecycle(), coins.getData());
                    coinMenuAdapter.addLoadStateListener(loadStates -> {
                        if (loadStates.getRefresh() instanceof LoadState.NotLoading) {
                            hidePageLoading();
                            hidePageError();
                        } else if (loadStates.getRefresh() instanceof LoadState.Error) {
                            showPageError(((LoadState.Error) loadStates.getRefresh()).getError());
                            hidePageLoading();
                        }
                        return null;
                    });
                } else if (coins.getState() == DataState.ERROR) {
                    showSnackBar(UiUtils.getString(requireContext(), R.string.error_something));
                }
            });
        }

        if (!vm.getCurrentCoinSearch().hasObservers()) {
            vm.getCurrentCoinSearch().observe(requireActivity(), search -> {
                if (search.getState() == DataState.SUCCESS) {
                    coinSearchAdapter.setList(search.getData());
                } else if (search.getState() == DataState.ERROR) {
                    showSnackBar(search.getError().getMessage());
                }
            });
        }

        if (!vm.getCurrentCoinsSearchHistory().hasObservers()) {
            vm.getCurrentCoinsSearchHistory().observe(requireActivity(), searchHistory -> {
                if (searchHistory.getState() == DataState.SUCCESS) {
                    searchHistoryCoinsAdapter.setList(searchHistory.getData());
                } else if (searchHistory.getState() == DataState.ERROR) {
                    showSnackBar(searchHistory.getError().getMessage());
                }
            });
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
        binding.rvCoins.setAdapter(coinMenuAdapter);
        binding.rvCoinSearch.setAdapter(coinSearchAdapter);
        binding.rvCoinHistory.setAdapter(searchHistoryCoinsAdapter);

        if (coinMenuAdapter.getItemCount() == 0)
            vm.pageCoins();

        binding.srlCoins.setOnRefreshListener(() -> {
            vm.clearCompositeDisposable();
            vm.pageCoins();
        });

        final View.OnClickListener errorClick = v -> {
            vm.pageCoins();
            binding.srlCoins.setRefreshing(true);
        };

        binding.textCoinError.setOnClickListener(errorClick);
        binding.iconCoinNoWifi.setOnClickListener(errorClick);
        binding.iconCoinError.setOnClickListener(errorClick);

        binding.searchView.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    vm.fetchCoinSearch(s.toString());
                } else clearSearchRvData();
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        final ImageButton searchClearBtn = binding.searchView.findViewById(com.google.android.material.R.id.search_view_clear_button);
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

        vm.fetchCoinSearchHistory();
    }


    private void clearSearchRvData() {
        coinSearchAdapter.setList(new ArrayList<>());
    }

    // region visibility management
    private void initPage() {
        if (!hasLoaded) {
            binding.srlCoins.setVisibility(View.GONE);
            binding.shimmerCoins.setVisibility(View.VISIBLE);
            return;
        }
        binding.srlCoins.setVisibility(View.VISIBLE);
        binding.shimmerCoins.setVisibility(View.GONE);

    }
    private void hidePageLoading() {
        binding.srlCoins.setVisibility(View.VISIBLE);
        binding.shimmerCoins.setVisibility(View.GONE);
        binding.srlCoins.setRefreshing(false);
        hasLoaded = true;
    }

    private void showPageError(Throwable e) {
        binding.srlCoins.setVisibility(View.VISIBLE);
        binding.shimmerCoins.setVisibility(View.GONE);
        binding.srlCoins.setRefreshing(false);

        if (coinMenuAdapter.getItemCount() > 0) {

            showSnackBar(e.getMessage());
            return;
        }

        binding.textCoinError.setVisibility(View.VISIBLE);
        if (e instanceof UnknownHostException) {
            binding.iconCoinError.setVisibility(View.GONE);
            binding.iconCoinNoWifi.setVisibility(View.VISIBLE);
            binding.setCoinError(UiUtils.getString(requireContext(), R.string.error_no_main_data));
            return;
        }
        binding.iconCoinNoWifi.setVisibility(View.GONE);
        binding.iconCoinError.setVisibility(View.VISIBLE);
        binding.setCoinError(e.getMessage());
    }

    private void hidePageError() {
        binding.iconCoinError.setVisibility(View.GONE);
        binding.iconCoinNoWifi.setVisibility(View.GONE);
        binding.textCoinError.setVisibility(View.GONE);
    }
    // endregion
}