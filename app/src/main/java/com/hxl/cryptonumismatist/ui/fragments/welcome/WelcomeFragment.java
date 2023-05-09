package com.hxl.cryptonumismatist.ui.fragments.welcome;

import static androidx.navigation.fragment.NavHostFragment.findNavController;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.hxl.cryptonumismatist.R;
import com.hxl.cryptonumismatist.databinding.FragmentWelcomeBinding;
import com.hxl.cryptonumismatist.base.BaseFragment;
import com.hxl.cryptonumismatist.ui.fragments.welcome.walkthrough.WalkThroughPagerAdapter;
import com.hxl.presentation.viewmodels.WelcomeViewModel;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class WelcomeFragment extends BaseFragment<FragmentWelcomeBinding, WelcomeViewModel> {

    @Override
    protected FragmentWelcomeBinding setViewBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentWelcomeBinding.inflate(inflater, container, false);
    }

    @Override
    protected WelcomeViewModel setViewModel() {
        return new ViewModelProvider(this).get(WelcomeViewModel.class);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {

        if (!vm.getWelcome()) {
            openCoinsFragment();
        }
        super.onViewCreated(view, savedInstanceState);

        ViewPager2 walkThroughPager = binding.walkThroughPager;
        walkThroughPager.setAdapter(new WalkThroughPagerAdapter(this));
        walkThroughPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                if (position == 2) {
                    loadLastPage(true);
                } else if (binding.btnGetStarted.getVisibility() == View.VISIBLE) {
                    loadLastPage(false);
                }
            }
        });

        binding.tabIndicator.setViewPager(walkThroughPager);

        binding.btnNext.setOnClickListener(
                view1 -> walkThroughPager.setCurrentItem(walkThroughPager.getCurrentItem() + 1)
        );

        View.OnClickListener exitButtonClick = v -> {
            vm.saveWelcome(false);
            openCoinsFragment();
        };

        binding.btnGetStarted.setOnClickListener(exitButtonClick);
        binding.tvSkip.setOnClickListener(exitButtonClick);
    }

    private void openCoinsFragment() {
        findNavController(this).navigate(R.id.welcome_to_navigation);
    }

    private void loadLastPage(@NonNull Boolean visibility) {
        int vStart;
        int vElse;
        int aStart;
        int aElse;

        if (visibility) {
            vStart = View.VISIBLE;
            vElse = View.INVISIBLE;
            aStart = R.anim.welcome_start;
            aElse = R.anim.to_invisible;
        }
        else {
            vStart = View.INVISIBLE;
            vElse = View.VISIBLE;
            aStart = R.anim.welcome_end;
            aElse = R.anim.to_visible;
        }

        binding.btnGetStarted.setVisibility(vStart);
        binding.btnGetStarted.setAnimation(AnimationUtils.loadAnimation(requireContext(), aStart));

        binding.btnNext.setVisibility(vElse);
        binding.btnNext.setAnimation(AnimationUtils.loadAnimation(requireContext(), aElse));

        binding.tvSkip.setVisibility(vElse);
        binding.tvSkip.setAnimation(AnimationUtils.loadAnimation(requireContext(), aElse));

        binding.tabIndicator.setVisibility(vElse);
        binding.tabIndicator.setAnimation(AnimationUtils.loadAnimation(requireContext(), aElse));
    }
}