package com.hxl.cryptonumismatist.presentation.fragments.welcome;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class WalkthroughPagerAdapter extends FragmentStateAdapter {

    private final Walkthrough[] walkthroughs;

    public WalkthroughPagerAdapter(
            Fragment fragment,
            Walkthrough[] walkthroughs
    ) {
        super(fragment);
        this.walkthroughs = walkthroughs;
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {
        WalkthroughItem item = new WalkthroughItem();
        
        Bundle bundle = new Bundle();
        bundle.putString("title", walkthroughs[position].title);
        bundle.putString("description", walkthroughs[position].description);
        bundle.putInt("image", walkthroughs[position].image);
        item.setArguments(bundle);
        
        return item;
    }

    @Override
    public int getItemCount() {
        return walkthroughs.length;
    }
}
