package com.hxl.cryptonumismatist.presentation.fragments.coins.main;

import android.os.Bundle;
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

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@AndroidEntryPoint
public class CoinsFragment extends BaseFragment<FragmentCoinsBinding, CoinsFragmentViewModel> {

    private final CompositeDisposable disposable = new CompositeDisposable();
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

        disposable.add(
                vm.getCoins()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(coins -> {
                            coinsAdapter.setList(coins);
                            coinsRv.setAdapter(coinsAdapter);
                        }, error -> Log.e("CoinsFragment", error.getMessage(), error)
                        ));
    }
}