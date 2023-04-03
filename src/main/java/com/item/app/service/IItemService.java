package com.item.app.service;

import com.item.app.entity.ItemDAO;
import com.item.app.utils.SearchParameters;

import java.util.List;

public interface IItemService {

    Long createItem(ItemDAO item);
    void updateItem(Long itemID, ItemDAO item);
    ItemDAO getItem(Long id);
    List<ItemDAO> findItems(SearchParameters parameters);
    void deleteItem(Long id);

}
