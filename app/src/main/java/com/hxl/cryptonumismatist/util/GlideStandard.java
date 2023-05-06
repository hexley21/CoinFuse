package com.hxl.cryptonumismatist.util;

import android.content.Context;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.hxl.cryptonumismatist.R;

public final class GlideStandard {

    @NonNull
    public static RequestManager getGlide(Context context) {
        return Glide.with(context).setDefaultRequestOptions(
                new RequestOptions()
                        .placeholder(R.drawable.logo)
                        .error(R.drawable.logo)
                        .diskCacheStrategy(DiskCacheStrategy.DATA)
        );
    }
}
