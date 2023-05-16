package com.hxl.coinfuse.ui.dialogs.exchanges;

import static com.hxl.coinfuse.ui.fragments.navigation.NavigationFragment.exchangeUrlArgKey;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.hxl.coinfuse.base.BaseDialog;
import com.hxl.coinfuse.databinding.DialogExchangeBinding;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ExchangeDialog extends BaseDialog<DialogExchangeBinding> {
    @Override
    protected DialogExchangeBinding setViewBinding(LayoutInflater inflater, ViewGroup container) {
        return DialogExchangeBinding.inflate(inflater, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        assert getArguments() != null;
        String explorerUrl = getArguments().getString(exchangeUrlArgKey);
        initExplorer(explorerUrl);
    }

    private void initExplorer(String explorerUrl) {
        if (explorerUrl == null || explorerUrl.isEmpty()) {
            binding.dialogExchangeWebsite.setVisibility(View.GONE);
        }
        else {
            binding.dialogExchangeWebsite.setOnClickListener(v ->
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(explorerUrl)))
            );
        }
    }

}
