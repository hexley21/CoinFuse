package com.hxl.cryptonumismatist.ui.dialogs.coins;

import static com.hxl.cryptonumismatist.ui.fragments.navigation.NavigationFragment.coinArgKey;
import static com.hxl.cryptonumismatist.ui.fragments.navigation.NavigationFragment.explorerArgKey;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import com.hxl.cryptonumismatist.R;
import com.hxl.cryptonumismatist.base.BaseDialog;
import com.hxl.cryptonumismatist.databinding.DialogCoinBinding;
import com.hxl.cryptonumismatist.util.EspressoIdlingResource;
import com.hxl.presentation.viewmodels.CoinDialogViewModel;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;

@AndroidEntryPoint
public class CoinDialog extends BaseDialog<DialogCoinBinding, CoinDialogViewModel> {
    @Override
    protected DialogCoinBinding setViewBinding(LayoutInflater inflater, ViewGroup container) {
        return DialogCoinBinding.inflate(inflater, container, false);
    }

    @Override
    protected CoinDialogViewModel setViewModel() {
        return new ViewModelProvider(this).get(CoinDialogViewModel.class);
    }

    private static final String TAG = "CoinDialog";


    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        assert getArguments() != null;
        String coinId = getArguments().getString(coinArgKey);
        String explorerUrl = getArguments().getString(explorerArgKey);
        initBookmark(coinId);
        initExplorer(explorerUrl);
    }

    private void setDrawableCompat(TextView textView, int drawableId) {
        Drawable drawable = ContextCompat.getDrawable(requireContext(), drawableId);
        textView.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
    }

    private void initExplorer(String explorerUrl) {
        if (explorerUrl == null || explorerUrl.isEmpty()) {
            binding.dialogCoinExplorer.setVisibility(View.GONE);
        }
        else {
            binding.dialogCoinExplorer.setOnClickListener(v ->
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(explorerUrl)))
            );
        }
    }

    private void initBookmark(String coinId) {
        EspressoIdlingResource.increment();
        vm.isCoinBookmarked(coinId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        b -> {
                            if (b) {
                                setDrawableCompat(binding.dialogCoinBookmark, R.drawable.bookmark_filled);
                                binding.dialogCoinBookmark.setText(getResources().getString(R.string.remove_from_bookmarks));
                            }
                            else {
                                binding.dialogCoinBookmark.setText(getResources().getString(R.string.add_to_bookmarks));
                            }
                            binding.dialogCoinBookmark.setOnClickListener(v -> bookmarkAction(coinId));

                            EspressoIdlingResource.decrement();
                            Log.d(TAG, "initBookmark: successful");},
                        e -> {
                            EspressoIdlingResource.decrement();
                            Log.e(TAG, "initBookmark: failed", e);
                            },
                        compositeDisposable
                );
    }

    private void bookmarkAction(String coinId) {
        EspressoIdlingResource.increment();
        vm.isCoinBookmarked(coinId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        b -> {
                            if (b) {
                                setDrawableCompat(binding.dialogCoinBookmark, R.drawable.bookmark_outlined);
                                binding.dialogCoinBookmark.setText(getResources().getString(R.string.add_to_bookmarks));
                                vm.unBookmarkCoin(coinId).subscribe();
                            }
                            else {
                                setDrawableCompat(binding.dialogCoinBookmark, R.drawable.bookmark_filled);
                                binding.dialogCoinBookmark.setText(getResources().getString(R.string.remove_from_bookmarks));
                                vm.bookmarkCoin(coinId).subscribe();
                            }

                            EspressoIdlingResource.decrement();
                            Log.d(TAG, "bookmarkAction: successful");},
                        e -> {
                            EspressoIdlingResource.decrement();
                            Log.e(TAG, "bookmarkAction: failed", e);
                            },
                        compositeDisposable
                );
    }
}
