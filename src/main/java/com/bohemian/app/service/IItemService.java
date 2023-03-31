package com.bohemian.app.service;

import com.bohemian.app.entity.ItemDAO;

import java.util.List;

public interface IItemService {

    Long createItem(ItemDAO item);
    void updateItem(Long itemID, ItemDAO item);
    ItemDAO getItem(Long id);
    List<ItemDAO> getItems(Integer limi, List<String> tags);
    void deleteItem(Long id);

}
