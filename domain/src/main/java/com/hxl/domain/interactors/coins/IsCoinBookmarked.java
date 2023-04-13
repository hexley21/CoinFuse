package com.hxl.domain.interactors.coins;

import com.hxl.domain.repository.CoinRepository;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public class IsCoinBookmarked {
    private final CoinRepository repository;

    @Inject
    public IsCoinBookmarked(CoinRepository repository) {
        this.repository = repository;
    }

    public Single<Boolean> invoke(String id) {
            return repository.isCoinBookmarked(id);
        }

}
