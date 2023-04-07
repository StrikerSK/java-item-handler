package com.item.app.utils;

import java.util.List;
import java.util.Objects;

public class ListingFactory {

    private final Integer defaultLimit;
    private final Integer defaultOffset;

    public ListingFactory(Integer defaultLimit, Integer defaultOffset) {
        this.defaultLimit = defaultLimit;
        this.defaultOffset = defaultOffset;
    }

    public SearchParameters generateFilter(Integer lowerBound, Integer upperBound, Integer offset, Integer limit, List<String> tags) {
        offset = Objects.requireNonNullElse(offset, defaultOffset);
        limit = Objects.requireNonNullElse(limit, defaultLimit);
        return new SearchParameters(lowerBound, upperBound, limit, offset, tags);
    }

}
