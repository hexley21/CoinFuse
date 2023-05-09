package com.hxl.cryptonumismatist.ui.fragments.bookmarks;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
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
        binding.pbCoinBookmarks.setVisibility(pbVisibility);
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_main);
        bookmarkCoinsAdapter = new BookmarkAdapter(navController);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView rvCoinBookmarks = binding.rvCoinBookmarks;
        rvCoinBookmarks.setLayoutManager(new LinearLayoutManager(requireContext()));
        rvCoinBookmarks.setAdapter(bookmarkCoinsAdapter);

        fetchBookmarkedCoins();
        pbVisibility = View.GONE;
        binding.srlCoinBookmarks.setOnRefreshListener(() -> {
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
                            binding.pbCoinBookmarks.setVisibility(View.GONE);
                            binding.srlCoinBookmarks.setRefreshing(false);
                            bookmarkCoinsAdapter.setList(coins);
                            if (coins.isEmpty()) {
                                visibilityBookmarkError(getResources().getString(R.string.error_no_bookmarks));
                            }

                            Log.d(TAG, "fetchBookmarkedCoins: success");
                            EspressoIdlingResource.decrement();
                        },
                        e -> {
                            binding.pbCoinBookmarks.setVisibility(View.GONE);
                            binding.srlCoinBookmarks.setRefreshing(false);
                            visibilityBookmarkError(e.getMessage());

                            Log.e(TAG, "fetchBookmarkedCoins: failed", e);
                            EspressoIdlingResource.decrement();
                        },
                        compositeDisposable
                );
    }

    private void visibilityBookmarkError(String error) {
        binding.pbCoinBookmarks.setVisibility(View.GONE);
        binding.textErrorCoinBookmark.setVisibility(View.VISIBLE);
        binding.iconErrorCoinBookmarks.setVisibility(View.VISIBLE);
        binding.setErrorCoinBookmarks(error);
    }

    private void setPbVisibilityErrorRefresh() {
        binding.textErrorCoinBookmark.setVisibility(View.GONE);
        binding.iconErrorCoinBookmarks.setVisibility(View.GONE);
    }
}
