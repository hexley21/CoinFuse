package com.hxl.cryptonumismatist.presentation.fragments.welcome;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class WalkthroughPagerAdapter extends FragmentStateAdapter {

    private final WalkthroughEnums[] walkthroughs;

    public WalkthroughPagerAdapter(
            Fragment fragment,
            WalkthroughEnums[] walkthroughs
    ) {
        super(fragment);
        this.walkthroughs = walkthroughs;
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {
        WalkthroughItem item = new WalkthroughItem();
        Walkthrough info = walkthroughs[position].walkthrough;

        Bundle bundle = new Bundle();
        bundle.putInt("title", info.title);
        bundle.putInt("description", info.description);
        bundle.putInt("image", info.image);
        item.setArguments(bundle);
        
        return item;
    }
    

    @Override
    public int getItemCount() {
        return walkthroughs.length;
    }
}
