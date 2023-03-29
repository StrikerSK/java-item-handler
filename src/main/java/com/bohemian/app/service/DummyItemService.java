package com.bohemian.app.service;

import com.bohemian.app.entity.ItemDAO;
import org.springframework.stereotype.Service;

import java.util.List;

//@Service
public class DummyItemService implements IItemService {

    @Override
    public Long createItem(ItemDAO item) {
        return 123L;
    }

    @Override
    public void updateItem(Long itemID, ItemDAO item) {}

    @Override
    public ItemDAO getItem(Long id) {
        ItemDAO item = new ItemDAO();
        item.setValue("Item name");
        return item;
    }

    @Override
    public List<ItemDAO> getItems() {
        return null;
    }

    @Override
    public void deleteItem(Long id) {}

}
