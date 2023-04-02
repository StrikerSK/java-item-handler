package com.bohemian.app.service;

import com.bohemian.app.entity.ItemDAO;
import com.bohemian.app.utils.SearchParameters;

import java.util.List;

public interface IItemService {

    Long createItem(ItemDAO item);
    void updateItem(Long itemID, ItemDAO item);
    ItemDAO getItem(Long id);
    List<ItemDAO> findItems(SearchParameters parameters);
    void deleteItem(Long id);

}
