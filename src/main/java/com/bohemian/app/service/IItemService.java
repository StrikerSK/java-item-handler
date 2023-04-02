package com.bohemian.app.service;

import com.bohemian.app.entity.ItemDAO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IItemService {

    Long createItem(ItemDAO item);
    void updateItem(Long itemID, ItemDAO item);
    ItemDAO getItem(Long id);
    List<ItemDAO> findItems(Pageable pageable, List<String> tags);
    void deleteItem(Long id);

}
