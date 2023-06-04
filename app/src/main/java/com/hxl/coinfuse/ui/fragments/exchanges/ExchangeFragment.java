package com.hxl.coinfuse.ui.fragments.exchanges;

import static com.hxl.coinfuse.ui.fragments.navigation.NavigationFragment.orderByArgKey;
import static com.hxl.coinfuse.ui.fragments.navigation.NavigationFragment.sortByArgKey;
import static com.hxl.coinfuse.ui.fragments.navigation.NavigationFragment.sortCallbackArgKey;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.hxl.coinfuse.R;
import com.hxl.coinfuse.base.BaseFragment;
import com.hxl.coinfuse.databinding.FragmentExchangeBinding;
import com.hxl.coinfuse.ui.dialogs.SortCallback;
import com.hxl.coinfuse.util.EspressoIdlingResource;
import com.hxl.coinfuse.util.UiUtils;
import com.hxl.coinfuse.util.PingUtil;
import com.hxl.presentation.OrderBy;
import com.hxl.presentation.exchange.ExchangeSortBy;
import com.hxl.presentation.livedata.DataState;
import com.hxl.presentation.viewmodels.ExchangesViewModel;

import java.net.UnknownHostException;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ExchangeFragment extends BaseFragment<FragmentExchangeBinding, ExchangesViewModel> {

    // region binding & view-model
    @Override
    protected FragmentExchangeBinding setViewBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentExchangeBinding.inflate(inflater, container, false);
    }

    @Override
    protected Class<ExchangesViewModel> setViewModelClass() {
        return ExchangesViewModel.class;
    }

    // endregion

    private ExchangeAdapter exchangeAdapter;
    private NavController navController;
    private boolean hasLoaded = false;

    private ExchangeSortBy finalSortBy = ExchangeSortBy.RANK;
    private OrderBy finalOrderBy = OrderBy.ASC;

    private int chipVisibility = View.GONE;

    public void setNavController(NavController navController) {
        this.navController = navController;
    }

    private NavController getNavController() {
        if (navController == null) {
            navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_main);
        }

        return navController;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        exchangeAdapter = new ExchangeAdapter();
        exchangeAdapter.addOnDataChangeListener((old, cur) -> binding.rvExchanges.scrollToPosition(0));
    }

    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        initPage();
        binding.chipExchangeSortDelete.setVisibility(chipVisibility);
        binding.rvExchanges.setAdapter(exchangeAdapter);

        if (!vm.getCurrentExchanges().hasObservers()) {
            vm.getCurrentExchanges().observe(requireActivity(), exchanges -> {
                if (exchanges.getState() == DataState.SUCCESS) {
                    if (exchanges.getData().isEmpty()) {
                        showError(new IllegalStateException(UiUtils.getString(requireContext(), R.string.error_no_data)));
                        hidePageLoading();
                        return;
                    }
                    exchangeAdapter.setList(exchanges.getData());
                    hideError();
                } else if (exchanges.getState() == DataState.ERROR) {
                    showError(exchanges.getError());
                }
                hidePageLoading();
            });
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (exchangeAdapter.getList().isEmpty()) {
            fetchExchanges();
        }

        binding.chipExchangeSort.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putParcelable(sortCallbackArgKey, (SortCallback<ExchangeSortBy>) this::fetchExchangesCallback);

            bundle.putSerializable(sortByArgKey, finalSortBy);
            bundle.putSerializable(orderByArgKey, finalOrderBy);

            getNavController().navigate(R.id.navigation_to_exchangeSortDialog, bundle);
        });

        binding.chipExchangeSortDelete.setOnClickListener(v -> {
            finalOrderBy = OrderBy.ASC;
            finalSortBy = ExchangeSortBy.RANK;
            fetchExchanges();
            chipVisibility = View.GONE;
            binding.chipExchangeSortDelete.setVisibility(View.GONE);
        });

        binding.srlExchanges.setOnRefreshListener(() -> {
            if (!PingUtil.isOnline()) {
                showSnackBar(UiUtils.getString(requireContext(), R.string.error_no_main_data));
            }
            fetchExchanges();
        });

        binding.textErrorExchange.setOnClickListener(v -> {
            fetchExchanges();
            binding.srlExchanges.setRefreshing(true);
        });
        binding.iconErrorExchange.setOnClickListener(v -> {
            fetchExchanges();
            binding.srlExchanges.setRefreshing(true);
        });
        binding.iconErrorWifiExchange.setOnClickListener(v -> {
            fetchExchanges();
            binding.srlExchanges.setRefreshing(true);
        });
    }

    private void fetchExchangesCallback(ExchangeSortBy sortBy, OrderBy orderBy) {
        if ((finalSortBy != sortBy) || (finalOrderBy != orderBy)) {
            chipVisibility = View.VISIBLE;
            binding.chipExchangeSortDelete.setVisibility(View.VISIBLE);
            finalSortBy = sortBy;
            finalOrderBy = orderBy;
            fetchExchanges();
        }
    }

    private void fetchExchanges() {
        vm.fetchExchanges(finalSortBy, finalOrderBy);
    }

    // region visibility management
    private void initPage() {
        if (!hasLoaded) {
            EspressoIdlingResource.increment();
            binding.srlExchanges.setVisibility(View.GONE);
            binding.shimmerExchanges.setVisibility(View.VISIBLE);
            exchangeAdapter.setNavController(getNavController());
            return;
        }
        binding.shimmerExchanges.setVisibility(View.GONE);
        binding.srlExchanges.setVisibility(View.VISIBLE);
    }
    private void hidePageLoading() {
        binding.shimmerExchanges.setVisibility(View.GONE);
        binding.srlExchanges.setVisibility(View.VISIBLE);
        binding.srlExchanges.setRefreshing(false);
        hasLoaded = true;
        EspressoIdlingResource.decrement();
    }

    private void showError(Throwable e) {
        binding.srlExchanges.setRefreshing(false);

        if (exchangeAdapter.getItemCount() > 0) {
            if (e instanceof UnknownHostException) {
                showSnackBar(UiUtils.getString(requireContext(), R.string.error_no_internet));
                return;
            }
            showSnackBar(e.getMessage());
            return;
        }

        binding.textErrorExchange.setVisibility(View.VISIBLE);
        if (e instanceof UnknownHostException) {
            binding.iconErrorExchange.setVisibility(View.GONE);
            binding.iconErrorWifiExchange.setVisibility(View.VISIBLE);
            binding.setErrorText(UiUtils.getString(requireContext(), R.string.error_no_internet));
            return;
        }
        binding.iconErrorWifiExchange.setVisibility(View.GONE);
        binding.iconErrorExchange.setVisibility(View.VISIBLE);
        binding.setErrorText(e.getMessage());
        EspressoIdlingResource.decrement();

    }

    private void hideError() {
        binding.iconErrorExchange.setVisibility(View.GONE);
        binding.iconErrorWifiExchange.setVisibility(View.GONE);
        binding.textErrorExchange.setVisibility(View.GONE);
    }
    // endregion
}
