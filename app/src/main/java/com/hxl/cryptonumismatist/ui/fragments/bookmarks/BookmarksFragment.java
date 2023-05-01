package com.hxl.cryptonumismatist.ui.fragments.bookmarks;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hxl.cryptonumismatist.R;
import com.hxl.cryptonumismatist.base.BaseFragment;
import com.hxl.cryptonumismatist.databinding.FragmentBookmarksBinding;
import com.hxl.cryptonumismatist.ui.fragments.coins.main.CoinsMenuAdapter;
import com.hxl.cryptonumismatist.util.EspressoIdlingResource;
import com.hxl.presentation.viewmodels.BookmarksViewModel;

import javax.inject.Inject;

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
    @Inject
    CoinsMenuAdapter bookmarkCoinsAdapter;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView rvBookmarkCoins = binding.rvBookmarkCoins;
        rvBookmarkCoins.setLayoutManager(new LinearLayoutManager(requireContext()));
        rvBookmarkCoins.setAdapter(bookmarkCoinsAdapter);
        bookmarkCoinsAdapter.setNavigateToDetails(bundle -> {
            Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_main)
                    .navigate(R.id.navigationFragment_to_coinDetailsFragment, bundle);
            return null;
        });

        fetchBookmarkedCoins();

    }

    private void fetchBookmarkedCoins() {
        EspressoIdlingResource.increment();
        vm.bookmarkedCoins()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        coins -> {
                            bookmarkCoinsAdapter.setList(coins);

                            Log.d(TAG, "fetchBookmarkedCoins: success");
                            EspressoIdlingResource.decrement();
                        },
                        e -> {
                            Log.e(TAG, "fetchBookmarkedCoins: failed", e);
                            EspressoIdlingResource.decrement();
                        },
                        compositeDisposable
                );
    }
}
