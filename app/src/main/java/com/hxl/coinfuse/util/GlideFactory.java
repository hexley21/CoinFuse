package com.hxl.coinfuse.util;

import android.content.Context;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.hxl.coinfuse.R;

public final class GlideFactory {

    @NonNull
    public static RequestManager createGlide(Context context) {
        return Glide.with(context).setDefaultRequestOptions(
                new RequestOptions()
                        .placeholder(R.drawable.logo)
                        .error(R.drawable.logo)
                        .diskCacheStrategy(DiskCacheStrategy.DATA)
        );
    }
}
