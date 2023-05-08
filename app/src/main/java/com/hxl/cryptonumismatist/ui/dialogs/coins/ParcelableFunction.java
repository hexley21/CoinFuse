package com.hxl.cryptonumismatist.ui.dialogs.coins;

import android.os.Parcel;
import android.os.Parcelable;

import com.hxl.presentation.SortBy;
import com.hxl.presentation.SortCoin;

import java.io.Serializable;

import kotlin.jvm.functions.Function2;

public class ParcelableFunction implements Parcelable {

    public transient Function2<SortCoin.SortType, SortBy, Void> function;

    public ParcelableFunction() {}

    public static final Creator<ParcelableFunction> CREATOR = new Creator<ParcelableFunction>() {
        @Override
        public ParcelableFunction createFromParcel(Parcel in) {
            return new ParcelableFunction(in);
        }

        @Override
        public ParcelableFunction[] newArray(int size) {
            return new ParcelableFunction[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        // Write the function to the parcel
        dest.writeSerializable((Serializable) function);
    }

    private ParcelableFunction(Parcel in) {
        // Read the function from the parcel
        function = (Function2<SortCoin.SortType, SortBy, Void>) in.readSerializable();
    }
}
