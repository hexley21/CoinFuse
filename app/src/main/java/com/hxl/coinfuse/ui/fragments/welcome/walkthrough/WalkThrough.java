package com.hxl.coinfuse.ui.fragments.welcome.walkthrough;

import com.hxl.coinfuse.R;

public class WalkThrough {
    public static final WalkThrough WELCOME = new WalkThrough(R.string.title_welcome, R.string.desc_welcome, R.raw.market);
    public static final WalkThrough EXPLORE = new WalkThrough(R.string.title_explore, R.string.desc_explore, R.raw.coin);
    public static final WalkThrough ANALYZE = new WalkThrough(R.string.title_analyze, R.string.desc_analyze, R.raw.analyze);
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

