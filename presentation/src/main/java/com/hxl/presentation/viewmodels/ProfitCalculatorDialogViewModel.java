package com.hxl.presentation.viewmodels;

import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.hxl.domain.interactors.coins.SearchCoins;
import com.hxl.domain.model.Coin;
import com.hxl.presentation.livedata.StateLiveData;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

@HiltViewModel
public class ProfitCalculatorDialogViewModel extends ViewModel {

    private static final String TAG = "ProfitCalculatorDialVM";
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    @NonNull private final SearchCoins searchCoins;

    @Inject
    public ProfitCalculatorDialogViewModel(SearchCoins searchCoins) {
        this.searchCoins = searchCoins;
    }

    private StateLiveData<List<Coin>> currentSearch;

    public StateLiveData<List<Coin>> getCurrentSearch() {
        if (currentSearch == null) {
            currentSearch = new StateLiveData<>();
        }

        return currentSearch;
    }

    public void fetchSearch(String query) {
        getCurrentSearch().setLoading();
        searchCoins.invoke(query)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        search -> {
                            getCurrentSearch().setSuccess(search);
                            Log.d(TAG, "fetchSearch: success");
                        },
                        e -> {
                            getCurrentSearch().setError(e);
                            Log.e(TAG, "fetchSearch: failed", e);
                        },
                        compositeDisposable
                );
    }
    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
        Log.d(TAG, "onCleared: CompositeDisposable was disposed");
    }
}
