package com.hxl.cryptonumismatist.presentation.fragments.welcome;

import com.hxl.cryptonumismatist.R;

public enum WalkthroughEnums {
    WELCOME(new Walkthrough(R.string.title_welcome, R.string.desc_welcome, R.drawable.img_welcome)),
    EXPLORE(new Walkthrough(R.string.title_explore, R.string.desc_explore, R.drawable.img_explore)),
    ANALYZE(new Walkthrough(R.string.title_analyze, R.string.desc_analye, R.drawable.img_analyze));

    final Walkthrough walkthrough;

    WalkthroughEnums(Walkthrough info) {
        this.walkthrough = info;
    }
}