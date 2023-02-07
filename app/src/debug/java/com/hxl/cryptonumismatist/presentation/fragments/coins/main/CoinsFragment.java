package com.hxl.cryptonumismatist.presentation.fragments.coins.main;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hxl.cryptonumismatist.R;
import com.hxl.cryptonumismatist.databinding.FragmentCoinsBinding;
import com.hxl.cryptonumismatist.presentation.fragments.welcome.WelcomeFragmentViewModel;

public class CoinsFragment extends Fragment {
    private WelcomeFragmentViewModel vmWelcome;
    private FragmentCoinsBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        vmWelcome = new ViewModelProvider(requireActivity()).get(WelcomeFragmentViewModel.class);
        binding = FragmentCoinsBinding.inflate(inflater, container, false);

        Log.d("CN_Coins", "onCreateView");
        return binding.getRoot();
    }

    // region lifecycle
    @Override
    public void onStart() {
        super.onStart();
        Log.d("CN_Coins", "onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("CN_Coins", "onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("CN_Coins", "onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("CN_Coins", "OnStop");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("CN_Coins", "OnDestroy");
    }
    // endregion

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.btnBack.setOnClickListener(v -> {
            vmWelcome.saveWelcome.invoke(true);
            NavHostFragment.findNavController(this).navigate(R.id.action_coinsFragment_to_welcomeFragment);
            Log.d("CN_Coins", "btnBack");
        });

        Log.d("CN_Coins", "onViewCreated");
    }
}