package com.hxl.local.coin.mapper;

import androidx.annotation.NonNull;

import com.hxl.domain.model.ValueAndTimestamp;
import com.hxl.local.coin.model.BookmarkEntity;

public class BookmarkEntityMapper {

    public static ValueAndTimestamp<String> mapFromEntity(@NonNull BookmarkEntity bookmarkEntity) {
        return new ValueAndTimestamp<>(bookmarkEntity.myValue, bookmarkEntity.timestamp);
    }

    public static BookmarkEntity mapToEntity(@NonNull ValueAndTimestamp<String> bookmark){
        return new BookmarkEntity(bookmark.value, bookmark.timestamp);
    }
}
