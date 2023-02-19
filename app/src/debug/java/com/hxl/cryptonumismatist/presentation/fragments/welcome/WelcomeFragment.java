package com.hxl.cryptonumismatist.presentation.fragments.welcome;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.viewpager2.widget.ViewPager2;

import com.hxl.cryptonumismatist.R;
import com.hxl.cryptonumismatist.databinding.FragmentWelcomeBinding;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class WelcomeFragment extends Fragment {

    private static final String TAG = "Welcome";
    private FragmentWelcomeBinding binding;
    private WelcomeFragmentViewModel vm;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");
        binding = FragmentWelcomeBinding.inflate(inflater, container, false);
        vm = new ViewModelProvider(this).get(WelcomeFragmentViewModel.class);
        if (!vm.getWelcome.invoke()) {
            openCoinsFragment();
        }
        Log.d(TAG, String.format("getWelcome : %b", vm.getWelcome.invoke()));

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG, "onViewCreated");
        ViewPager2 walkThroughPager = binding.walkThroughPager;
        walkThroughPager.setAdapter(new WalkThroughPagerAdapter(this, WalkThroughEnums.values()));
        walkThroughPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                if (position == 2) {
                    loadLastPage(true);
                } else if (binding.btnGetStarted.getVisibility() == View.VISIBLE) {
                    loadLastPage(false);
                }
                Log.d(TAG, String.format("pagerItem: %d", walkThroughPager.getCurrentItem()));
            }
        });

        binding.tabIndicator.setViewPager(walkThroughPager);

        binding.btnNext.setOnClickListener(
                view1 -> walkThroughPager.setCurrentItem(walkThroughPager.getCurrentItem() + 1)
        );


        View.OnClickListener exitButtonClick = v -> {
            vm.saveWelcome.invoke(false);
            openCoinsFragment();
        };

        binding.btnGetStarted.setOnClickListener(exitButtonClick);
        binding.tvSkip.setOnClickListener(exitButtonClick);

    }

    private void openCoinsFragment() {
        NavHostFragment.findNavController(this).navigate(R.id.action_welcomeFragment_to_coinsFragment);
        Log.d(TAG, "openCoinsFragment");
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
        Log.d(TAG, String.format("loadLastPage(%b)", visibility));
    }

    // region lifecycle
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Log.d(TAG, "onAttach");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "onDetach");
    }
    // endregion

}