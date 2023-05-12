package com.hxl.cryptonumismatist.ui.fragments.bookmarks;

import static com.hxl.cryptonumismatist.ui.fragments.navigation.NavigationFragment.coinSortCallbackArgKey;
import static com.hxl.cryptonumismatist.ui.fragments.navigation.NavigationFragment.isTimeSortableArgKey;
import static com.hxl.cryptonumismatist.ui.fragments.navigation.NavigationFragment.orderByArgKey;
import static com.hxl.cryptonumismatist.ui.fragments.navigation.NavigationFragment.sortByArgKey;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hxl.cryptonumismatist.R;
import com.hxl.cryptonumismatist.base.BaseFragment;
import com.hxl.cryptonumismatist.databinding.FragmentBookmarksBinding;
import com.hxl.cryptonumismatist.ui.dialogs.coins.CoinSortCallback;
import com.hxl.cryptonumismatist.util.EspressoIdlingResource;
import com.hxl.domain.model.Coin;
import com.hxl.presentation.OrderBy;
import com.hxl.presentation.coin.CoinSortBy;
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

    private CoinSortBy finalSortBy = CoinSortBy.TIMESTAMP;
    private OrderBy finalOrderBy = OrderBy.DESC;

    private int loadingVisibility = View.VISIBLE;
    private int chipVisibility = View.GONE;


    private final AsyncListDiffer.ListListener<Coin> onDataChange = (old, cur) -> {
        binding.shimmerCoins.setVisibility(View.GONE);
        binding.srlCoinBookmarks.setRefreshing(false);
        binding.rvCoinBookmarks.scrollToPosition(0);
    };


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bookmarkCoinsAdapter = new BookmarkAdapter();
        bookmarkCoinsAdapter.addOnDataChangeListener(onDataChange);
    }

    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        binding.shimmerCoins.setVisibility(loadingVisibility);
        binding.chipCoinBookmarkSortDelete.setVisibility(chipVisibility);
        if (bookmarkCoinsAdapter.getNavController() == null) {
            navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_main);
            bookmarkCoinsAdapter.setNavController(navController);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView rvCoinBookmarks = binding.rvCoinBookmarks;
        rvCoinBookmarks.setLayoutManager(new LinearLayoutManager(requireContext()));
        rvCoinBookmarks.setAdapter(bookmarkCoinsAdapter);

        if (bookmarkCoinsAdapter.getList().isEmpty()) {
            getCoins();
        }

        binding.chipCoinBookmarkSort.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putBoolean(isTimeSortableArgKey, true);

            bundle.putParcelable(coinSortCallbackArgKey, (CoinSortCallback) this::fetchCoinsCallback);

            bundle.putSerializable(sortByArgKey, finalSortBy);
            bundle.putSerializable(orderByArgKey, finalOrderBy);

            binding.searchBarCoinBookmarks.clearFocus();

            navController.navigate(R.id.navigation_to_coinSortDialog, bundle);
        });

        loadingVisibility = View.GONE;

        binding.srlCoinBookmarks.setOnRefreshListener(() -> {
            setPbVisibilityErrorRefresh();
            binding.searchBarCoinBookmarks.setQuery("", false);
            binding.searchBarCoinBookmarks.clearFocus();
            fetchCoins();
        });

        binding.chipCoinBookmarkSortDelete.setOnClickListener(v -> {
            finalOrderBy = OrderBy.DESC;
            finalSortBy = CoinSortBy.TIMESTAMP;
            fetchCoins();
            chipVisibility = View.GONE;
            binding.chipCoinBookmarkSortDelete.setVisibility(View.GONE);
        });

        binding.searchBarCoinBookmarks.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                fetchCoins();
                return false;
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        binding.searchBarCoinBookmarks.clearFocus();
    }

    private void getCoins() {
        EspressoIdlingResource.increment();
        vm.bookmarkedCoins(finalSortBy, finalOrderBy)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        coinsConsumer,
                        coinsErrorConsumer,
                        compositeDisposable
                );
    }

    private void getCoinsByQuery(String query) {
        EspressoIdlingResource.increment();
        vm.searchBookmarkedCoins(query, finalSortBy, finalOrderBy)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        coinsConsumer,
                        coinsErrorConsumer,
                        compositeDisposable
                );
    }

    private void fetchCoins() {
        if (binding.searchBarCoinBookmarks.getQuery().length() == 0) {
            getCoins();
            return;
        }
        getCoinsByQuery(binding.searchBarCoinBookmarks.getQuery().toString());
    }

    private void fetchCoinsCallback(CoinSortBy coinSortBy, OrderBy orderBy) {
        if ((finalSortBy != coinSortBy) || (finalOrderBy != orderBy)) {
            chipVisibility = View.VISIBLE;
            binding.chipCoinBookmarkSortDelete.setVisibility(View.VISIBLE);
            finalSortBy = coinSortBy;
            finalOrderBy = orderBy;
        }
        fetchCoins();
    }
    private final Consumer<List<Coin>> coinsConsumer = coins -> {
            bookmarkCoinsAdapter.setList(coins);
            setPbVisibilityErrorRefresh();
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
