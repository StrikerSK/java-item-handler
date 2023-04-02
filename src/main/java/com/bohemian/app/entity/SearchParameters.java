package com.bohemian.app.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
public class SearchParameters {

    private Integer lowerBound;
    private Integer upperBound;
    private Integer page;
    private Integer limit;
    private List<String> tags;

    public SearchParameters(Integer lowerBound, Integer upperBound, Integer limit, Integer page, List<String> tags) {
        setLowerBound(lowerBound);
        setUpperBound(upperBound);
        setPage(page);
        setLimit(limit);
        setTags(tags);
    }

    public void setLowerBound(Integer lowerBound) {
        this.lowerBound = Objects.requireNonNullElse(lowerBound, Integer.MIN_VALUE);
    }

    public void setUpperBound(Integer upperBound) {
        this.upperBound = Objects.requireNonNullElse(upperBound, Integer.MIN_VALUE);
    }

    public void setTags(List<String> tags) {
        this.tags = Objects.requireNonNullElseGet(tags, ArrayList::new);
    }

    public Pageable getPageable() {
        return PageRequest.of(getPage(), getLimit());
    }

}
