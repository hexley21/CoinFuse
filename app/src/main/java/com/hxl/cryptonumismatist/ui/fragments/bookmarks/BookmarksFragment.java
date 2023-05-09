package com.hxl.cryptonumismatist.ui.fragments.bookmarks;

import static com.hxl.cryptonumismatist.ui.fragments.navigation.NavigationFragment.coinSortCallbackArgKey;
import static com.hxl.cryptonumismatist.ui.fragments.navigation.NavigationFragment.isTimeSortableArgKey;

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
import com.hxl.cryptonumismatist.ui.dialogs.coins.CoinSortCallback;
import com.hxl.cryptonumismatist.util.EspressoIdlingResource;
import com.hxl.domain.model.Coin;
import com.hxl.presentation.OrderBy;
import com.hxl.presentation.SortCoin;
import com.hxl.presentation.viewmodels.BookmarksViewModel;

import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.functions.Consumer;

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
    private BookmarkAdapter bookmarkCoinsAdapter;
    private NavController navController;

    private int pbVisibility = View.VISIBLE;

    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        binding.shimmerCoins.setVisibility(pbVisibility);
        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_main);
        bookmarkCoinsAdapter = new BookmarkAdapter(navController);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView rvCoinBookmarks = binding.rvCoinBookmarks;
        rvCoinBookmarks.setLayoutManager(new LinearLayoutManager(requireContext()));
        rvCoinBookmarks.setAdapter(bookmarkCoinsAdapter);

        fetchBookmarkedCoins();

        binding.chipCoinBookmarkSort.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putBoolean(isTimeSortableArgKey, true);
            bundle.putParcelable(coinSortCallbackArgKey, (CoinSortCallback) this::fetchBookmarkedCoins);
            navController.navigate(R.id.navigation_to_coinSortDialog, bundle);
        });
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
                        coinsConsumer,
                        coinsErrorConsumer,
                        compositeDisposable
                );
    }

    private void fetchBookmarkedCoins(SortCoin.SortType sortType, OrderBy orderBy) {
        EspressoIdlingResource.increment();
        vm.bookmarkedCoins(sortType, orderBy)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        coinsConsumer,
                        coinsErrorConsumer,
                        compositeDisposable
                );
    }

    private final Consumer<List<Coin>> coinsConsumer = coins -> {
            binding.shimmerCoins.setVisibility(View.GONE);
            binding.srlCoinBookmarks.setRefreshing(false);
            bookmarkCoinsAdapter.setList(coins);
            binding.rvCoinBookmarks.scrollTo(0, 0);
            if (coins.isEmpty()) {
                visibilityBookmarkError(getResources().getString(R.string.error_no_bookmarks));
            }

            Log.d(TAG, "fetchBookmarkedCoins: success");
            EspressoIdlingResource.decrement();
        };

    private final Consumer<Throwable> coinsErrorConsumer = e -> {
        binding.shimmerCoins.setVisibility(View.GONE);
        binding.srlCoinBookmarks.setRefreshing(false);
        visibilityBookmarkError(e.getMessage());
        Log.e(TAG, "fetchBookmarkedCoins: failed", e);
        EspressoIdlingResource.decrement();
    };

    private void visibilityBookmarkError(String error) {
        binding.shimmerCoins.setVisibility(View.GONE);
        binding.textErrorCoinBookmark.setVisibility(View.VISIBLE);
        binding.iconErrorCoinBookmarks.setVisibility(View.VISIBLE);
        binding.setErrorCoinBookmarks(error);
    }

    private void setPbVisibilityErrorRefresh() {
        binding.textErrorCoinBookmark.setVisibility(View.GONE);
        binding.iconErrorCoinBookmarks.setVisibility(View.GONE);
    }
}
