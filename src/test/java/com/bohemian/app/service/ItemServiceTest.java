package com.bohemian.app.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import com.bohemian.app.entity.ItemDAO;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class ItemServiceTest {

    @Mock
    private IItemService itemService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateItem() {
        ItemDAO item = new ItemDAO();
        item.setUserValue("Hello world!");
        Long itemId = 12345L;
        when(itemService.createItem(item)).thenReturn(itemId);
        Long createdItemId = itemService.createItem(item);
        assertEquals(itemId, createdItemId);
    }

    @Test
    public void testGetItem() {
        ItemDAO item = new ItemDAO("Test item", 10.0);
        Long itemId = 12345L; // mock ID value
        when(itemService.getItem(itemId)).thenReturn(item);
        ItemDAO retrievedItem = itemService.getItem(itemId);
        assertEquals(item.getName(), retrievedItem.getName());
        assertEquals(item.getPrice(), retrievedItem.getPrice(), 0.001);
    }

    @Test
    public void testUpdateItem() {
        ItemDAO item = new ItemDAO("Test item", 10.0);
        Long itemId = 12345L; // mock ID value
        itemService.updateItem(itemId, item);
        verify(itemService).updateItem(eq(itemId), eq(item));
    }

    @Test
    public void testDeleteItem() {
        Long itemId = 12345L; // mock ID value
        itemService.deleteItem(itemId);
        verify(itemService).deleteItem(eq(itemId));
    }

    @Test
    public void testGetItems() {
        ItemDAO item1 = new ItemDAO("Test item 1", 10.0);
        ItemDAO item2 = new ItemDAO("Test item 2", 20.0);
        List<ItemDAO> items = new ArrayList<>();
        items.add(item1);
        items.add(item2);
        when(itemService.getItems()).thenReturn(items);
        List<ItemDAO> retrievedItems = itemService.getItems();
        assertEquals(2, retrievedItems.size());
        assertTrue(retrievedItems.contains(item1));
        assertTrue(retrievedItems.contains(item2));
    }
}
