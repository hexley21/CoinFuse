package com.hxl.coinfuse.ui.fragments.coins.details.exchanges;

import static com.hxl.coinfuse.ui.fragments.navigation.NavigationFragment.coinArgKey;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.snackbar.Snackbar;
import com.hxl.coinfuse.R;
import com.hxl.coinfuse.base.BaseFragment;
import com.hxl.coinfuse.databinding.FragmentCoinExchangesBinding;
import com.hxl.coinfuse.ui.fragments.exchanges.details.TradesAdapter;
import com.hxl.coinfuse.util.EspressoIdlingResource;
import com.hxl.coinfuse.util.UiUtils;
import com.hxl.domain.model.Trade;
import com.hxl.presentation.viewmodels.CoinExchangesViewModel;

import java.net.UnknownHostException;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;

@AndroidEntryPoint
public class CoinExchangesFragment extends BaseFragment<FragmentCoinExchangesBinding, CoinExchangesViewModel> {
    @Override
    protected FragmentCoinExchangesBinding setViewBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentCoinExchangesBinding.inflate(inflater, container, false);
    }

    @Override
    protected CoinExchangesViewModel setViewModel() {
        return new ViewModelProvider(this).get(CoinExchangesViewModel.class);
    }

    private static final String TAG = "CoinExchanges";
    private final TradesAdapter tradesAdapter = new TradesAdapter(true);
    private String coinId;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        assert getArguments() != null;
        coinId = getArguments().getString(coinArgKey);
    }

    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        super.onCreateView(savedInstanceState);

        final Observer<List<Trade>> tradesObserver = trades -> {
            tradesAdapter.setList(trades);
            EspressoIdlingResource.decrement();
        };

        vm.getCurrentTrades().observe(requireActivity(), tradesObserver);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.rvCoinExchanges.setAdapter(tradesAdapter);
        fetchTrades();
    }

    private void fetchTrades() {
        EspressoIdlingResource.increment();
        vm.fetchTrades(coinId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        trades -> {
                            vm.getCurrentTrades().setValue(trades);
                            Log.d(TAG, "fetchTrades: succeed");
                        },
                        e -> {
                            Log.e(TAG, "fetchTrades: failed", e);
                            if (e instanceof UnknownHostException) {
                                Snackbar.make(requireContext(), binding.getRoot(), UiUtils.getString(requireContext(), R.string.error_no_internet), Snackbar.LENGTH_LONG).show();
                            }
                            EspressoIdlingResource.decrement();
                        },
                        compositeDisposable
                );
    }


}
