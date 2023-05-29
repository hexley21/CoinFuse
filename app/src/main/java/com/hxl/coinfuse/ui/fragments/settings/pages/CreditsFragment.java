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
import androidx.recyclerview.widget.RecyclerView;

import com.hxl.coinfuse.R;
import com.hxl.coinfuse.databinding.FragmentCreditsBinding;
import com.hxl.coinfuse.databinding.ItemCreditBinding;
import com.hxl.coinfuse.util.UiUtils;

import kotlin.jvm.functions.Function2;

public class CreditsFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        com.hxl.coinfuse.databinding.FragmentCreditsBinding binding = FragmentCreditsBinding.inflate(inflater, container, false);

        binding.creditsToolbar.setNavigationOnClickListener(v -> requireActivity().onBackPressed());
        binding.rvCredits.setAdapter(new CreditsListAdapter());
        return binding.getRoot();
    }


    private static class CreditsListAdapter extends RecyclerView.Adapter<CreditsListAdapter.CreditsViewHolder> {

        private String[] creditTitles;
        private String[] creditDescriptions;
        private String[] creditLinks;

        @NonNull
        @Override
        public CreditsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            final ItemCreditBinding binding = ItemCreditBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);

            creditTitles = UiUtils.getStringArray(parent.getContext(), R.array.credit_titles);
            creditDescriptions = UiUtils.getStringArray(parent.getContext(), R.array.credit_descriptions);
            creditLinks = UiUtils.getStringArray(parent.getContext(), R.array.credit_links);

            return new CreditsViewHolder(binding);
        }

        @Override
        public void onBindViewHolder(@NonNull CreditsViewHolder holder, int position) {
            ((Function2<String, String, Void>) holder).invoke(creditTitles[position], creditDescriptions[position]);

            holder.itemView.setOnClickListener(v ->
                    holder.itemView.getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(creditLinks[position])))
            );
        }

        @Override
        public int getItemCount() {
            return 12;
        }

        public static class CreditsViewHolder extends RecyclerView.ViewHolder implements Function2<String, String, Void> {
            private final ItemCreditBinding binding;
            public CreditsViewHolder(ItemCreditBinding binding) {
                super(binding.getRoot());
                this.binding = binding;
            }

            @Override
            public Void invoke(String title, String desc) {
                binding.tvCreditTitle.setText(title);
                binding.tvCreditDesc.setText(desc);
                return null;
            }
        }
    }
}
