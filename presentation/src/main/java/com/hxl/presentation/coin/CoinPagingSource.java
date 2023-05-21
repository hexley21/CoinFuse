package com.hxl.presentation.coin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.paging.PagingState;
import androidx.paging.rxjava3.RxPagingSource;

import com.hxl.domain.interactors.coins.GetCoins;
import com.hxl.domain.model.Coin;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;

public class CoinPagingSource extends RxPagingSource<Integer, Coin> {

    GetCoins getCoins;

    public static final int AMOUNT = 50;

    public CoinPagingSource(GetCoins getCoins) {
        this.getCoins = getCoins;
    }

    @Nullable
    @Override
    public Integer getRefreshKey(@NonNull PagingState<Integer, Coin> pagingState) {
        Integer anchorPosition = pagingState.getAnchorPosition();
        if (anchorPosition == null) {
            return null;
        }

        LoadResult.Page<Integer, Coin> anchorPage = pagingState.closestPageToPosition(anchorPosition);
        if (anchorPage == null) {
            return null;
        }

        Integer prevKey = anchorPage.getPrevKey();
        if (prevKey != null) {
            return prevKey + 1;
        }

        Integer nextKey = anchorPage.getNextKey();
        if (nextKey != null) {
            return nextKey - 1;
        }

        return null;
    }

    @NonNull
    @Override
    public Single<LoadResult<Integer, Coin>> loadSingle(@NonNull LoadParams<Integer> loadParams) {
        Integer nextPageNumber = loadParams.getKey();
        if (nextPageNumber == null) {
            nextPageNumber = 0;
        }

        return getCoins.invoke(AMOUNT, nextPageNumber)
                .observeOn(AndroidSchedulers.mainThread())
                .map(this::toLoadResult)
                .onErrorReturn(LoadResult.Error::new);
    }

    private LoadResult<Integer, Coin> toLoadResult(List<Coin> response) {

        Integer nextPage = null;
        if (!response.isEmpty()) {
            nextPage = response.get(response.size() - 1).rank;
        }
        return new LoadResult.Page<>(
                response,
                null,
                nextPage,
                LoadResult.Page.COUNT_UNDEFINED,
                LoadResult.Page.COUNT_UNDEFINED
        );
    }
}
