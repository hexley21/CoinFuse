package com.hxl.cryptonumismatist.presentation.fragments.welcome;

import com.hxl.cryptonumismatist.R;

public enum WalkThroughEnums {
    WELCOME(new WalkThrough(R.string.title_welcome, R.string.desc_welcome, R.drawable.img_welcome)),
    EXPLORE(new WalkThrough(R.string.title_explore, R.string.desc_explore, R.drawable.img_explore)),
    ANALYZE(new WalkThrough(R.string.title_analyze, R.string.desc_analyze, R.drawable.img_analyze));

    final WalkThrough walkthrough;

    WalkThroughEnums(WalkThrough info) {
        this.walkthrough = info;
    }
}