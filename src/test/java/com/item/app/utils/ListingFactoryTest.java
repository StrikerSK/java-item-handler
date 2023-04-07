package com.item.app.utils;

import com.item.app.AbstractSpringTesting;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class ListingFactoryTest extends AbstractSpringTesting {

    private static final Integer DEFAULT_LIMIT = 999;
    private static final Integer DEFAULT_OFFSET = 5;
    private static final List<String> DEFAULT_TAGS = List.of("one", "two");
    private static ListingFactory listingFactory;

    @Before
    public void init() {
        listingFactory = new ListingFactory(DEFAULT_LIMIT, DEFAULT_OFFSET);
    }

    @Test
    public void testImplicitValues() {
        SearchParameters searchParameters  = listingFactory.generateFilter(null, null, null, null, null);
        Assert.assertEquals(Integer.MIN_VALUE, searchParameters.getLowerBound().longValue());
        Assert.assertEquals(Integer.MAX_VALUE, searchParameters.getUpperBound().longValue());
        Assert.assertEquals(DEFAULT_LIMIT.longValue(), searchParameters.getLimit().longValue());
        Assert.assertEquals(DEFAULT_OFFSET.longValue(), searchParameters.getOffset().longValue());
        Assert.assertEquals(List.of(), searchParameters.getTags());
    }


    @Test
    public void testExplicitValues() {
        SearchParameters searchParameters  = listingFactory.generateFilter(33, 3333, 5, 50, DEFAULT_TAGS);
        Assert.assertEquals(33, searchParameters.getLowerBound().longValue());
        Assert.assertEquals(3333, searchParameters.getUpperBound().longValue());
        Assert.assertEquals(5, searchParameters.getOffset().longValue());
        Assert.assertEquals(50, searchParameters.getLimit().longValue());
        Assert.assertEquals(DEFAULT_TAGS, searchParameters.getTags());
    }

    @Test
    public void testExplicitBigValues() {
        SearchParameters searchParameters  = listingFactory.generateFilter(33, 3333, 9999, 9999, DEFAULT_TAGS);
        Assert.assertEquals(33, searchParameters.getLowerBound().longValue());
        Assert.assertEquals(3333, searchParameters.getUpperBound().longValue());
        Assert.assertEquals(DEFAULT_OFFSET.longValue(), searchParameters.getOffset().longValue());
        Assert.assertEquals(DEFAULT_LIMIT.longValue(), searchParameters.getLimit().longValue());
        Assert.assertEquals(DEFAULT_TAGS, searchParameters.getTags());
    }

    @Test
    public void testExplicitNegativeValues() {
        SearchParameters searchParameters  = listingFactory.generateFilter(-33, -1, -66, -6666, DEFAULT_TAGS);
        Assert.assertEquals(-33, searchParameters.getLowerBound().longValue());
        Assert.assertEquals(-1, searchParameters.getUpperBound().longValue());
        Assert.assertEquals(0, searchParameters.getOffset().longValue());
        Assert.assertEquals(DEFAULT_LIMIT.longValue(), searchParameters.getLimit().longValue());
        Assert.assertEquals(DEFAULT_TAGS, searchParameters.getTags());
    }

    @Test
    public void testBoundsException() {
        try {
            listingFactory.generateFilter(999, 1, DEFAULT_OFFSET, DEFAULT_LIMIT, DEFAULT_TAGS);
            Assert.fail();
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("Lower bound cannot be same or higher than upper bound!", e.getMessage());
        }
    }

    @Test
    public void testBoundsEqualityException() {
        try {
            listingFactory.generateFilter(999, 999, DEFAULT_OFFSET, DEFAULT_LIMIT, DEFAULT_TAGS);
            Assert.fail();
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("Lower bound cannot be same or higher than upper bound!", e.getMessage());
        }
    }

}
