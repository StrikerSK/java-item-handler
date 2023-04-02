package com.bohemian.app.service;

import com.bohemian.app.BohemianAppApplicationTests;
import com.bohemian.app.entity.ItemDAO;
import com.bohemian.app.exceptions.NotFoundException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;

public class DefaultItemServiceTest extends BohemianAppApplicationTests {

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
        List<ItemDAO> items = itemService.findItems(PageRequest.of(0, 5), List.of());
        Assert.assertEquals(5, items.size());
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
