package com.hxl.cryptonumismatist.presentation.fragments.welcome;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.viewpager2.widget.ViewPager2;

import com.hxl.cryptonumismatist.R;
import com.hxl.cryptonumismatist.databinding.FragmentWelcomeBinding;

public class WelcomeFragment extends Fragment {

    private FragmentWelcomeBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentWelcomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ViewPager2 walkthroughPager = binding.walkthroughPager;
        Walkthrough[] walkthroughs = {
                new Walkthrough("1", "1", R.drawable.ic_launcher_foreground),
                new Walkthrough("2", "2", R.drawable.ic_launcher_foreground),
                new Walkthrough("3", "3", R.drawable.ic_launcher_foreground),
        };
        walkthroughPager.setAdapter(new WalkthroughPagerAdapter(this, walkthroughs));
        walkthroughPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                loadLastPage(position == 2);
            }
        });

        binding.tabIndicator.setViewPager(walkthroughPager);

        binding.btnNext.setOnClickListener(
                view1 -> walkthroughPager.setCurrentItem(walkthroughPager.getCurrentItem() + 1)
        );

        binding.btnGetStarted.setOnClickListener(view1 -> openCoinFragment());
        binding.tvSkip.setOnClickListener(view1 -> openCoinFragment());
    }

    private void openCoinFragment() {
        NavHostFragment.findNavController(this).navigate(R.id.action_welcomeFragment_to_coinsFragment);
    }

    private void loadLastPage(Boolean visibility) {
        int visStart = View.VISIBLE;
        int visElse = View.INVISIBLE;
        int animStart = R.anim.welcome_start;
        int animElse = R.anim.to_invisible;

        if (!visibility) {
            visStart = View.INVISIBLE;
            visElse = View.VISIBLE;
            animStart = R.anim.welcome_end;
            animElse = R.anim.to_visible;
        }

        binding.btnGetStarted.setVisibility(visStart);
        binding.btnGetStarted.setAnimation(AnimationUtils.loadAnimation(requireContext(), animStart));

        binding.btnNext.setVisibility(visElse);
        binding.btnNext.setAnimation(AnimationUtils.loadAnimation(requireContext(), animElse));

        binding.tvSkip.setVisibility(visElse);
        binding.tvSkip.setAnimation(AnimationUtils.loadAnimation(requireContext(), animElse));

        binding.tabIndicator.setVisibility(visElse);
        binding.tabIndicator.setAnimation(AnimationUtils.loadAnimation(requireContext(), animElse));
    }
}