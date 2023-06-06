package com.hxl.coinfuse.ui.fragments.settings.pages;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.hxl.coinfuse.R;
import com.hxl.coinfuse.databinding.FragmentAboutUsBinding;
import com.hxl.coinfuse.util.UiUtils;

import java.util.Calendar;

public class AboutUsFragment extends Fragment {

    private FragmentAboutUsBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentAboutUsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.setYear(String.valueOf(Calendar.getInstance().get(Calendar.YEAR)));

        binding.tvEmail.setOnClickListener(v -> openEmail());
        binding.tvGithub.setOnClickListener(v ->openWeb("https://" + UiUtils.getString(requireContext(), R.string.github_repo)));
    }

    private void openEmail() {
        startActivity(
                new Intent(
                        Intent.ACTION_SENDTO,
                        Uri.parse("mailto:${requireContext().getString(R.string.developer)}")
                )
        );
    }

    private void openWeb(String url) {
        requireActivity().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
    }
}
