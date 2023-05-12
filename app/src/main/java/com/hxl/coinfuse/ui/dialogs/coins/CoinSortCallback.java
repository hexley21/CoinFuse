package com.hxl.coinfuse.ui.dialogs.coins;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.hxl.presentation.OrderBy;
import com.hxl.presentation.coin.CoinSortBy;

public interface CoinSortCallback extends Parcelable {

    void apply(CoinSortBy coinSortBy, OrderBy orderBy);

    @Override
    default int describeContents() {
        return 0;
    }

    @Override
    default void writeToParcel(@NonNull Parcel dest, int flags) {}
}
