package com.hxl.cryptonumismatist.presentation.fragments.coins.main;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hxl.cryptonumismatist.databinding.FragmentCoinsBinding;
import com.hxl.cryptonumismatist.presentation.fragments.BaseFragment;
import com.hxl.domain.model.Coin;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;

@AndroidEntryPoint
public class CoinsFragment extends BaseFragment<FragmentCoinsBinding, CoinsFragmentViewModel> {

//    private final CompositeDisposable disposable = new CompositeDisposable();
    @Override
    protected FragmentCoinsBinding setViewBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentCoinsBinding.inflate(inflater, container, false);
    }

    @Override
    protected CoinsFragmentViewModel setViewModel() {
        return new ViewModelProvider(this).get(CoinsFragmentViewModel.class);
    }

    @Inject
    CoinsAdapter coinsAdapter;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        RecyclerView coinsRv = binding.rvCoins;
        coinsRv.setLayoutManager(new LinearLayoutManager(requireContext()));
        coinsRv.setAdapter(coinsAdapter);
        if (binding.tfSearch.getEditText() != null) {
            binding.tfSearch.getEditText().addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                    vm.searchCoins(s.toString())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new SingleObserver<List<Coin>>() {

                                @Override
                                public void onSubscribe(Disposable d) {
                                }

                                @Override
                                public void onSuccess(List<Coin> coins) {
                                    coinsAdapter.setList(coins);
                                }

                                @Override
                                public void onError(Throwable e) {
                                }
                            });
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            });
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        vm.getCoins()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<Coin>>() {
                    @Override
                    public void onSubscribe(Disposable d) { }
                    @Override
                    public void onSuccess(List<Coin> coins) {
                        coinsAdapter.setList(coins);
                    }
                    @Override
                    public void onError(Throwable e) {
                        Log.e("CoinsFragment", e.getMessage(), e);
                    }
                });
    }
}