package com.hxl.coinfuse.ui.dialogs;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.hxl.presentation.OrderBy;
import com.hxl.presentation.coin.CoinSortBy;

public interface SortCallback<T> extends Parcelable{

    void apply(T coinSortBy, OrderBy orderBy);

    @Override
    default int describeContents() {
        return 0;
    }

    @Override
    default void writeToParcel(@NonNull Parcel dest, int flags) {}
}
