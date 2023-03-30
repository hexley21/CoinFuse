package com.hxl.cryptonumismatist.ui.fragments.welcome.walkthrough;

import static com.hxl.cryptonumismatist.ui.fragments.welcome.walkthrough.WalkThrough.WALK_THROUGHS;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class WalkThroughPagerAdapter extends FragmentStateAdapter {

    public WalkThroughPagerAdapter(
            Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        WalkThroughItem item = new WalkThroughItem();
        WalkThrough info = WALK_THROUGHS[position];

        Bundle bundle = new Bundle();
        bundle.putInt("title", info.title);
        bundle.putInt("description", info.description);
        bundle.putInt("image", info.image);
        item.setArguments(bundle);
        
        return item;
    }
    

    @Override
    public int getItemCount() {
        return WALK_THROUGHS.length;
    }
}
