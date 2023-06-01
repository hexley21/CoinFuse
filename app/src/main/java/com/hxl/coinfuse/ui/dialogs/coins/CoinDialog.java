package com.hxl.coinfuse.ui.dialogs.coins;

import static com.hxl.coinfuse.ui.fragments.navigation.NavigationFragment.coinArgKey;
import static com.hxl.coinfuse.ui.fragments.navigation.NavigationFragment.explorerArgKey;
import static com.hxl.coinfuse.ui.fragments.navigation.NavigationFragment.dialogCallbackArgKey;
import static com.hxl.coinfuse.ui.fragments.navigation.NavigationFragment.searchQuery;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.hxl.coinfuse.R;
import com.hxl.coinfuse.base.BaseDialog;
import com.hxl.coinfuse.databinding.DialogCoinBinding;
import com.hxl.coinfuse.ui.dialogs.DialogCallback;
import com.hxl.coinfuse.util.UiUtils;
import com.hxl.presentation.livedata.DataState;
import com.hxl.presentation.viewmodels.CoinDialogViewModel;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;

@AndroidEntryPoint
public class CoinDialog extends BaseDialog<DialogCoinBinding> {
    @Override
    protected DialogCoinBinding setViewBinding(LayoutInflater inflater, ViewGroup container) {
        return DialogCoinBinding.inflate(inflater, container, false);
    }

    private CoinDialogViewModel vm;
    private static final String TAG = "CoinDialog";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vm = new ViewModelProvider(this).get(CoinDialogViewModel.class);
    }

    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        assert getArguments() != null;
        String coinId = getArguments().getString(coinArgKey);
        String explorerUrl = getArguments().getString(explorerArgKey);
        String query = getArguments().getString(searchQuery);
        DialogCallback callback = getArguments().getParcelable(dialogCallbackArgKey);
        initBookmark(coinId);
        initExplorer(explorerUrl);
        initSearchRemove(query, callback);

    }

    private void setDrawableCompat(TextView textView, int drawableId) {
        textView.setCompoundDrawablesWithIntrinsicBounds(
                UiUtils.getDrawable(requireContext(), drawableId),
                null,
                null,
                null
        );
    }

    private void initSearchRemove(String query, DialogCallback callback) {
        if (query != null) {
            vm.getCurrentDeleteState().observe(requireActivity(), delete -> {
                if (delete.getState() == DataState.SUCCESS) {
                    assert callback != null;
                    callback.invoke();
                }
                else if (delete.getState() == DataState.ERROR) {
                    Toast.makeText(requireContext(), delete.getError().getMessage(), Toast.LENGTH_LONG).show();
                }
            });

            binding.dialogCoinDeleteSearch.setOnClickListener(v -> {
                vm.deleteSearchQuery(query);
                dismiss();
            });
            return;
        }
        binding.dialogCoinDeleteSearch.setVisibility(View.GONE);
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

                            Log.d(TAG, "initBookmark: successful");},
                        e -> Log.e(TAG, "initBookmark: failed", e),
                        compositeDisposable
                );
    }

    private void bookmarkAction(String coinId) {
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

                            Log.d(TAG, "bookmarkAction: successful");},
                        e -> Log.e(TAG, "bookmarkAction: failed", e),
                        compositeDisposable
                );
    }
}
