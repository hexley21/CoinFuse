package com.hxl.coinfuse.ui.fragments.exchanges;

import static com.hxl.coinfuse.ui.fragments.navigation.NavigationFragment.orderByArgKey;
import static com.hxl.coinfuse.ui.fragments.navigation.NavigationFragment.sortByArgKey;
import static com.hxl.coinfuse.ui.fragments.navigation.NavigationFragment.sortCallbackArgKey;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.RecyclerView;

import com.hxl.coinfuse.R;
import com.hxl.coinfuse.base.BaseFragment;
import com.hxl.coinfuse.databinding.FragmentExchangeBinding;
import com.hxl.coinfuse.ui.dialogs.SortCallback;
import com.hxl.coinfuse.util.EspressoIdlingResource;
import com.hxl.domain.model.Exchange;
import com.hxl.presentation.OrderBy;
import com.hxl.presentation.exchange.ExchangeSortBy;
import com.hxl.presentation.viewmodels.ExchangesViewModel;

import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.functions.Consumer;

@AndroidEntryPoint
public class ExchangeFragment extends BaseFragment<FragmentExchangeBinding, ExchangesViewModel> {

    // region binding & view-model
    @Override
    protected FragmentExchangeBinding setViewBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentExchangeBinding.inflate(inflater, container, false);
    }

    @Override
    protected ExchangesViewModel setViewModel() {
        return new ViewModelProvider(this).get(ExchangesViewModel.class);
    }
    // endregion

    private final String TAG = "ExchangesFragment";
    private ExchangeAdapter exchangeAdapter;
    private NavController navController;

    private ExchangeSortBy finalSortBy = ExchangeSortBy.RANK;
    private OrderBy finalOrderBy = OrderBy.ASC;

    private int loadingVisibility = View.VISIBLE;
    private int chipVisibility = View.GONE;


    private final AsyncListDiffer.ListListener<Exchange> onDataChange = (old, cur) -> {
//        binding.shimmerCoins.setVisibility(View.GONE);
        binding.srlExchanges.setRefreshing(false);
        binding.rvExchanges.scrollToPosition(0);
    };


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        exchangeAdapter = new ExchangeAdapter();
        exchangeAdapter.addOnDataChangeListener(onDataChange);
    }

    @Override
    protected void onCreateView(Bundle savedInstanceState) {
//        binding.shimmerCoins.setVisibility(loadingVisibility);
        binding.chipExchangeSortDelete.setVisibility(chipVisibility);
        if (exchangeAdapter.getNavController() == null) {
            navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_main);
            exchangeAdapter.setNavController(navController);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView rvExchanges = binding.rvExchanges;
        rvExchanges.setAdapter(exchangeAdapter);

        if (exchangeAdapter.getList().isEmpty()) {
            getExchanges();
        }

        binding.chipExchangeSort.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putParcelable(sortCallbackArgKey, (SortCallback<ExchangeSortBy>) this::fetchExchangesCallback);

            bundle.putSerializable(sortByArgKey, finalSortBy);
            bundle.putSerializable(orderByArgKey, finalOrderBy);

            navController.navigate(R.id.navigation_to_exchangeSortDialog, bundle);
        });

        loadingVisibility = View.GONE;

        binding.srlExchanges.setOnRefreshListener(() -> {
            setPbVisibilityErrorRefresh();
            getExchanges();
        });

        binding.chipExchangeSortDelete.setOnClickListener(v -> {
            finalOrderBy = OrderBy.ASC;
            finalSortBy = ExchangeSortBy.RANK;
            getExchanges();
            chipVisibility = View.GONE;
            binding.chipExchangeSortDelete.setVisibility(View.GONE);
        });

    }

    private void fetchExchangesCallback(ExchangeSortBy sortBy, OrderBy orderBy) {
        if ((finalSortBy != sortBy) || (finalOrderBy != orderBy)) {
            chipVisibility = View.VISIBLE;
            binding.chipExchangeSortDelete.setVisibility(View.VISIBLE);
            finalSortBy = sortBy;
            finalOrderBy = orderBy;
            getExchanges();
        }
    }


    private void getExchanges() {
        EspressoIdlingResource.increment();
        vm.getExchanges(finalSortBy, finalOrderBy)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        exchangeConsumer,
                        exchangeErrorConsumer,
                        compositeDisposable
                );
    }

    private final Consumer<List<Exchange>> exchangeConsumer = exchanges -> {
            exchangeAdapter.setList(exchanges);
            setPbVisibilityErrorRefresh();
            if (exchanges.isEmpty()) {
                visibilityError(getResources().getString(R.string.error_no_data));
            }

            Log.d(TAG, "fetchExchanges: success");
            EspressoIdlingResource.decrement();
        };


    private final Consumer<Throwable> exchangeErrorConsumer = e -> {
//        binding.shimmerCoins.setVisibility(View.GONE);
        binding.srlExchanges.setRefreshing(false);
        visibilityError(e.getMessage());
        Log.e(TAG, "fetchExchanges: failed", e);
        EspressoIdlingResource.decrement();
    };

    private void visibilityError(String error) {
//        binding.shimmerCoins.setVisibility(View.GONE);
        binding.rvExchanges.setVisibility(View.GONE);
        binding.textErrorExchange.setVisibility(View.VISIBLE);
        binding.iconErrorExchange.setVisibility(View.VISIBLE);
        binding.setErrorExchangeText(error);
    }

    private void setPbVisibilityErrorRefresh() {
        binding.rvExchanges.setVisibility(View.VISIBLE);
        binding.textErrorExchange.setVisibility(View.GONE);
        binding.iconErrorExchange.setVisibility(View.GONE);
    }
}
