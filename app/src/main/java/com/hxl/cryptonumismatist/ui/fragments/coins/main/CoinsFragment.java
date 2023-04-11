package com.hxl.cryptonumismatist.ui.fragments.coins.main;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.hxl.cryptonumismatist.R;
import com.hxl.cryptonumismatist.base.BaseFragment;
import com.hxl.cryptonumismatist.databinding.FragmentCoinsBinding;
import com.hxl.cryptonumismatist.util.EspressoIdlingResource;
import com.hxl.domain.model.Coin;
import com.hxl.presentation.viewmodels.CoinsMenuViewModel;

import java.util.List;
import java.util.function.Function;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;

@AndroidEntryPoint
public class CoinsFragment extends BaseFragment<FragmentCoinsBinding, CoinsMenuViewModel> {
    @Override
    protected FragmentCoinsBinding setViewBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentCoinsBinding.inflate(inflater, container, false);
    }

    @Override
    protected CoinsMenuViewModel setViewModel() {
        return new ViewModelProvider(this).get(CoinsMenuViewModel.class);
    }

    @Inject
    CoinsAdapter coinsAdapter;
    SwipeRefreshLayout refreshLayout;
    ProgressBar progressBar;
    private int pbVisibility = View.VISIBLE;

    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        refreshLayout = binding.srlCoins;
        progressBar = binding.pbCoins;
        progressBar.setVisibility(pbVisibility);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Function<Bundle, Void> navigateToDetails = bundle -> {
            Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_main)
                    .navigate(R.id.navigationFragment_to_coinDetailsFragment, bundle);
            return null;
        };
        RecyclerView coinsRv = binding.rvCoins;
        coinsAdapter.setNavigateToDetails(navigateToDetails);
        coinsRv.setLayoutManager(new LinearLayoutManager(requireContext()));
        coinsRv.setAdapter(coinsAdapter);
        updateCoins();
        pbVisibility = View.GONE;
        binding.searchView.getEditText().setOnEditorActionListener((v, actionId, event) -> {
            searchCoins(v.getText().toString());
            return false;
        });
        binding.srlCoins.setOnRefreshListener(this::updateCoins);
    }

    private void updateCoins() {
        vm.getCoins()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<Coin>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        EspressoIdlingResource.increment();
                    }
                    @Override
                    public void onSuccess(List<Coin> coins) {
                        EspressoIdlingResource.decrement();
                        coinsAdapter.setList(coins);
                        progressBar.setVisibility(View.GONE);
                        refreshLayout.setRefreshing(false);
                    }
                    @Override
                    public void onError(Throwable e) {
                        Log.e("CoinsFragment", e.getMessage(), e);
                        progressBar.setVisibility(View.GONE);
                        refreshLayout.setRefreshing(false);
                    }
                });
    }

    private void searchCoins(String query) {
        vm.searchCoins(query)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<Coin>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        EspressoIdlingResource.increment();
                    }
                    @Override
                    public void onSuccess(List<Coin> coins) {
                        EspressoIdlingResource.decrement();
                        coinsAdapter.setList(coins);
                    }
                    @Override
                    public void onError(Throwable e) {
                        Log.e("CoinsFragment", e.getMessage(), e);
                    }
                });
    }
}