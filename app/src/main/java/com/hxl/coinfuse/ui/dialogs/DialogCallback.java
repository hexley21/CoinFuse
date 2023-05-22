package com.hxl.coinfuse.ui.dialogs;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public interface DialogCallback extends Parcelable {

    void invoke();

    @Override
    default int describeContents() {
        return 0;
    }

    @Override
    default void writeToParcel(@NonNull Parcel dest, int flags) {}
}
