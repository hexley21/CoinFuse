package com.hxl.cryptonumismatist.ui.fragments.welcome;

import com.hxl.cryptonumismatist.R;

public class WalkThrough {
    public static final WalkThrough WELCOME = new WalkThrough(R.string.title_welcome, R.string.desc_welcome, R.drawable.img_welcome);
    public static final WalkThrough EXPLORE = new WalkThrough(R.string.title_explore, R.string.desc_explore, R.drawable.img_explore);
    public static final WalkThrough ANALYZE = new WalkThrough(R.string.title_analyze, R.string.desc_analyze, R.drawable.img_analyze);
    public static final WalkThrough[] WALK_THROUGHS = new WalkThrough[] {WELCOME, EXPLORE, ANALYZE};

    public final int title;
    public final int description;
    public final int image;

    public WalkThrough(int title, int description, int image) {
        this.title = title;
        this.description = description;
        this.image = image;
    }

}

