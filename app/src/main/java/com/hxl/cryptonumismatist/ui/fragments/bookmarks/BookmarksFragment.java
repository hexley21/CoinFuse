package com.hxl.cryptonumismatist.ui.fragments.bookmarks;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hxl.cryptonumismatist.R;
import com.hxl.cryptonumismatist.base.BaseFragment;
import com.hxl.cryptonumismatist.databinding.FragmentBookmarksBinding;
import com.hxl.cryptonumismatist.util.EspressoIdlingResource;
import com.hxl.presentation.viewmodels.BookmarksViewModel;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;

@AndroidEntryPoint
public class BookmarksFragment extends BaseFragment<FragmentBookmarksBinding, BookmarksViewModel> {

    // region binding & view-model
    @Override
    protected FragmentBookmarksBinding setViewBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentBookmarksBinding.inflate(inflater, container, false);
    }

    @Override
    protected BookmarksViewModel setViewModel() {
        return new ViewModelProvider(this).get(BookmarksViewModel.class);
    }
    // endregion

    private final String TAG = "BookmarksFragment";
    BookmarkAdapter bookmarkCoinsAdapter;

    private int pbVisibility = View.VISIBLE;

    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        binding.pbBookmarks.setVisibility(pbVisibility);
        bookmarkCoinsAdapter = new BookmarkAdapter(requireActivity(), R.id.nav_host_fragment_main);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView rvBookmarkCoins = binding.rvBookmarkCoins;
        rvBookmarkCoins.setLayoutManager(new LinearLayoutManager(requireContext()));
        rvBookmarkCoins.setAdapter(bookmarkCoinsAdapter);

        fetchBookmarkedCoins();
        pbVisibility = View.GONE;
        binding.srlBookmarkCoins.setOnRefreshListener(() -> {
            setPbVisibilityErrorRefresh();
            fetchBookmarkedCoins();
        });
    }

    private void fetchBookmarkedCoins() {
        EspressoIdlingResource.increment();
        vm.bookmarkedCoins()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        coins -> {
                            binding.pbBookmarks.setVisibility(View.GONE);
                            binding.srlBookmarkCoins.setRefreshing(false);
                            bookmarkCoinsAdapter.setList(coins);
                            if (coins.isEmpty()) {
                                visibilityBookmarkError(getResources().getString(R.string.error_no_bookmarks));
                            }

                            Log.d(TAG, "fetchBookmarkedCoins: success");
                            EspressoIdlingResource.decrement();
                        },
                        e -> {
                            binding.pbBookmarks.setVisibility(View.GONE);
                            binding.srlBookmarkCoins.setRefreshing(false);
                            visibilityBookmarkError(e.getMessage());

                            Log.e(TAG, "fetchBookmarkedCoins: failed", e);
                            EspressoIdlingResource.decrement();
                        },
                        compositeDisposable
                );
    }

    private void visibilityBookmarkError(String error) {
        binding.pbBookmarks.setVisibility(View.GONE);
        binding.textBookmarkError.setVisibility(View.VISIBLE);
        binding.iconBookmarkError.setVisibility(View.VISIBLE);
        binding.setBookmarkError(error);
    }

    private void setPbVisibilityErrorRefresh() {
        binding.textBookmarkError.setVisibility(View.GONE);
        binding.iconBookmarkError.setVisibility(View.GONE);
    }
}
