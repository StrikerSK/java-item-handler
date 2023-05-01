package com.item.app.service;

import com.item.app.AbstractSpringTesting;
import com.item.app.entity.ItemDAO;
import com.item.app.utils.SearchParameters;
import com.item.app.exceptions.NotFoundException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.annotation.DirtiesContext;

import java.util.ArrayList;
import java.util.List;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class DefaultItemServiceTest extends AbstractSpringTesting {

    List<Long> itemsIDs = new ArrayList<>();

    @Before
    public void setup() {
        for (int i = 1; i <= 10; i++) {
            ItemDAO item = new ItemDAO();
            item.setUserValue(i);
            item.setTags(List.of("test tag"));
            Long id = itemService.createItem(item);
            itemsIDs.add(id);
        }
    }

    @Test
    public void listItems() {
        SearchParameters parameters = new SearchParameters(Integer.MIN_VALUE, Integer.MAX_VALUE, 5, 0, List.of());
        List<ItemDAO> items = itemService.findItems(parameters);
        Assert.assertEquals(5, items.size());

        parameters.setLimit(999);
        items = itemService.findItems(parameters);
        Assert.assertEquals(10, items.size());

        parameters.setUpperBound(8);
        items = itemService.findItems(parameters);
        Assert.assertEquals(8, items.size());

        parameters.setLowerBound(4);
        items = itemService.findItems(parameters);
        Assert.assertEquals(5, items.size());
    }

    @Test
    public void listItemsWithOffset() {
        SearchParameters parameters = new SearchParameters(Integer.MIN_VALUE, Integer.MAX_VALUE, 999, 3, List.of());
        List<ItemDAO> items = itemService.findItems(parameters);
        Assert.assertEquals(7, items.size());
        Assert.assertEquals(itemsIDs.get(3).longValue(), items.get(0).getId().longValue());
    }

    @Test
    public void invalidLimitInListItems() {
        SearchParameters parameters = new SearchParameters(Integer.MIN_VALUE, Integer.MAX_VALUE, -5, 0, List.of());
        try {
            itemService.findItems(parameters);
        } catch (Exception e){
            Assert.assertTrue(e instanceof IllegalArgumentException);
            Assert.assertEquals("Limit must not be less than one!", e.getMessage());
        }
    }

    @Test
    public void invalidOffsetInListItems() {
        SearchParameters parameters = new SearchParameters(Integer.MIN_VALUE, Integer.MAX_VALUE, 5, -5, List.of());
        try {
            itemService.findItems(parameters);
        } catch (Exception e){
            Assert.assertTrue(e instanceof IllegalArgumentException);
            Assert.assertEquals("Offset index must not be less than zero!", e.getMessage());
        }
    }

    @Test
    public void getExistingItem() {
        Long id = itemsIDs.get(0);
        ItemDAO item = itemService.getItem(id);
        Assert.assertNotNull(item);
        Assert.assertEquals(id, item.getId());
        Assert.assertEquals(1, item.getUserValue().longValue());
    }

    @Test
    public void getNonExistingItem() {
        Long id = 9999L;
        try {
            itemService.getItem(id);
        } catch (Exception e) {
            Assert.assertTrue(e instanceof NotFoundException);
            Assert.assertEquals(String.format("Item [%s] not found!", id), e.getMessage());
        }
    }

    @Test
    public void updateExistingItem() {
        Long id = itemsIDs.get(0);
        Integer newValue = 9999;

        ItemDAO item = new ItemDAO();
        item.setUserValue(newValue);
        item.setTags(List.of("updated tage", "new tag"));

        itemService.updateItem(id, item);
        ItemDAO persistedItem = itemService.getItem(id);
        Assert.assertEquals(newValue, persistedItem.getUserValue());
        Assert.assertEquals(2, persistedItem.getTags().size());
        Assert.assertEquals(id, persistedItem.getId());
        Assert.assertNotEquals(persistedItem.getCreateAt(), persistedItem.getModifiedAt());
    }

    @Test
    public void updateNonExistingItem() {
        Long id = 9999L;
        ItemDAO item = new ItemDAO();
        item.setUserValue(9999);
        item.setTags(List.of("updated tage", "new tag"));
        try {
            itemService.updateItem(id, item);
        } catch (Exception e) {
            Assert.assertTrue(e instanceof NotFoundException);
            Assert.assertEquals(String.format("Item [%s] not found!", id), e.getMessage());
        }
    }

    @Test
    public void deleteExistingItem() {
        Long id = itemsIDs.get(5);
        itemService.deleteItem(id);
        try {
            itemService.getItem(id);
        } catch (Exception e) {
            Assert.assertTrue(e instanceof NotFoundException);
            Assert.assertEquals(String.format("Item [%s] not found!", id), e.getMessage());
        }
    }

    @Test
    public void deleteNonExistingItem() {
        Long id = 9999L;
        try {
            itemService.deleteItem(id);
        } catch (Exception e) {
            Assert.assertTrue(e instanceof NotFoundException);
            Assert.assertEquals(String.format("Item [%s] not found!", id), e.getMessage());
        }
    }

}
