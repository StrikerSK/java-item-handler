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
        lowerBound = Objects.requireNonNullElse(lowerBound, Integer.MIN_VALUE);
        upperBound = Objects.requireNonNullElse(upperBound, Integer.MAX_VALUE);
        assertBounds(lowerBound, upperBound);

        offset = checkOffset(offset);
        limit = checkLimit(limit);
        return new SearchParameters(lowerBound, upperBound, limit, offset, tags);
    }

    private Integer checkOffset(Integer offset) {
        offset = Objects.requireNonNullElse(offset, defaultOffset);

        if (offset < 0) {
            offset = 0;
        } else if (offset > defaultOffset) {
            offset = defaultOffset;
        }

        return offset;
    }

    private Integer checkLimit(Integer limit) {
        limit = Objects.requireNonNullElse(limit, defaultLimit);

        if (limit < 0 || limit > defaultLimit) {
            limit = defaultLimit;
        }

        return limit;
    }

    private void assertBounds(Integer lowerBound, Integer upperBound) {
        if (lowerBound >= upperBound) {
            throw new IllegalArgumentException("Lower bound cannot be same or higher than upper bound!");
        }
    }

}
