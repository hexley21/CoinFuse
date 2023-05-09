package com.hxl.cryptonumismatist.ui.dialogs.coins;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.hxl.presentation.SortBy;
import com.hxl.presentation.SortCoin;

public interface CoinSortCallback extends Parcelable {

    void apply(SortCoin.SortType sortType, SortBy sortBy);

    @Override
    default int describeContents() {
        return 0;
    }

    @Override
    default void writeToParcel(@NonNull Parcel dest, int flags) {}
}
