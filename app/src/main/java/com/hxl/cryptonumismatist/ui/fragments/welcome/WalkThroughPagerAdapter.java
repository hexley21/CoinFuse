package com.hxl.cryptonumismatist.ui.fragments.welcome;

import static com.hxl.cryptonumismatist.ui.fragments.welcome.WalkThrough.WALK_THROUGHS;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class WalkThroughPagerAdapter extends FragmentStateAdapter {

    private final WalkThroughEnums[] walkThroughEnums;

    public WalkThroughPagerAdapter(
            Fragment fragment,
            WalkThroughEnums[] walkThroughEnums
    ) {
        super(fragment);
        this.walkThroughEnums = walkThroughEnums;
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {
        WalkThroughItem item = new WalkThroughItem();
        WalkThrough info = walkThroughEnums[position].walkthrough;

        Bundle bundle = new Bundle();
        bundle.putInt("title", info.title);
        bundle.putInt("description", info.description);
        bundle.putInt("image", info.image);
        item.setArguments(bundle);
        
        return item;
    }
    

    @Override
    public int getItemCount() {
        return walkThroughEnums.length;
    }
}
