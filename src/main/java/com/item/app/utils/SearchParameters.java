package com.item.app.utils;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
public class SearchParameters {

    private Integer lowerBound;
    private Integer upperBound;
    private Integer offset;
    private Integer limit;
    private List<String> tags;

    public SearchParameters(Integer lowerBound, Integer upperBound, Integer limit, Integer offset, List<String> tags) {
        this.setLowerBound(lowerBound);
        this.setUpperBound(upperBound);
        this.setOffset(offset);
        this.setLimit(limit);
        this.setTags(tags);
    }

    public void setLowerBound(Integer lowerBound) {
        this.lowerBound = Objects.requireNonNullElse(lowerBound, Integer.MIN_VALUE);
    }

    public void setUpperBound(Integer upperBound) {
        this.upperBound = Objects.requireNonNullElse(upperBound, Integer.MAX_VALUE);
    }

    public void setTags(List<String> tags) {
        this.tags = Objects.requireNonNullElseGet(tags, ArrayList::new);
    }

    public Pageable getPageable() {
        return new OffsetBasedPageRequest(offset, limit);
    }

}
