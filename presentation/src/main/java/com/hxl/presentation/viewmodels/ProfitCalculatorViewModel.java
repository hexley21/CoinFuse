package com.hxl.presentation.viewmodels;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
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
public class ProfitCalculatorViewModel extends ViewModel {

    private static final String TAG = "ProfitCalculatorVM";
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    @NonNull private final SearchCoins searchCoins;

    @Inject
    public ProfitCalculatorViewModel(@NonNull SearchCoins searchCoins){
        this.searchCoins = searchCoins;
    }

    private StateLiveData<List<Coin>> currentSearch;
    private MutableLiveData<Double> currentProfit;
    private MutableLiveData<Double> currentInvestment;
    private MutableLiveData<Double> currentExit;

    private Double buyPrice;
    private Double investment;
    private Double sellPrice;
    private Double investmentFee;
    private Double exitFee;

    public void setBuyPriceField(Double buyPrice) {
        this.buyPrice = buyPrice;
        calculateProfit();
    }

    public void setInvestmentField(Double investment) {
        this.investment = investment;
        calculateProfit();
    }

    public void setSellPriceField(Double sellPrice) {
        this.sellPrice = sellPrice;
        calculateProfit();
    }

    public void setInvestmentFeeField(Double investmentFee) {
        this.investmentFee = investmentFee;
        calculateProfit();
    }

    public void setExitFeeField(Double exitFee) {
        this.exitFee = exitFee;
        calculateProfit();
    }

    public StateLiveData<List<Coin>> getCurrentSearch() {
        if (currentSearch == null) {
            currentSearch = new StateLiveData<>();
        }

        return currentSearch;
    }

    public MutableLiveData<Double> getCurrentProfit() {
        if (currentProfit == null) {
            currentProfit = new MutableLiveData<>();
        }

        return currentProfit;
    }

    public MutableLiveData<Double> getCurrentInvestment() {
        if (currentInvestment == null) {
            currentInvestment = new MutableLiveData<>();
        }

        return currentInvestment;
    }

    public MutableLiveData<Double> getCurrentExit() {
        if (currentExit == null) {
            currentExit = new MutableLiveData<>();
        }

        return currentExit;
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

    public void setProfit(double val) {
        getCurrentProfit().setValue(val);
    }

    public void setInvestment(double val) {
        getCurrentInvestment().setValue(val);
    }

    public void setExit(double val) {
        getCurrentExit().setValue(val);
    }

    public void calculateProfit() {
        if (buyPrice == null || buyPrice == 0.0D) {
            setProfit(0.0D);
            return;
        }
        if (investment == null || investment == 0.0D) {
            setProfit(0.0D);
            return;
        }
        if (sellPrice == null || sellPrice == 0.0D) {
            setProfit(0.0D);
            return;
        }
        final double nnInvestmentFee = investmentFee == null ? 0.0 : investmentFee;
        final double nnExitFee = exitFee == null ? 0.0 : exitFee;

        final double profit = ((sellPrice - buyPrice) * (investment / buyPrice)) - (nnInvestmentFee + nnExitFee);

        setProfit(profit);
        setInvestment(investment + nnInvestmentFee);
        setExit(investment + nnInvestmentFee + profit);
    }

    public void clearEverything() {
        buyPrice = 0.0D;
        investment = 0.0D;
        sellPrice = 0.0D;
        investmentFee = 0.0D;
        exitFee = 0.0D;
        setProfit(0.0D);
        setInvestment(0.0D);
        setExit(0.0D);
    }


    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
        Log.d(TAG, "onCleared: CompositeDisposable was disposed");
    }
}
