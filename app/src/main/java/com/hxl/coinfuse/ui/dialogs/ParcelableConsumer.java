package com.hxl.coinfuse.ui.dialogs;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.function.Consumer;

public interface ParcelableConsumer<T> extends Parcelable, Consumer<T> {

    @Override
    default int describeContents() {
        return 0;
    }

    @Override
    default void writeToParcel(@NonNull Parcel dest, int flags) {}
}
