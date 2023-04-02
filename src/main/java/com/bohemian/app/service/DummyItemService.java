package com.bohemian.app.service;

import com.bohemian.app.entity.ItemDAO;
import com.bohemian.app.entity.SearchParameters;
import com.bohemian.app.exceptions.NotFoundException;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

//@Service
public class DummyItemService implements IItemService {

    private final List<ItemDAO> items = new CopyOnWriteArrayList<>();

    @Override
    public Long createItem(ItemDAO item) {
        Long id = items.size() + 1L;
        Date current = Date.from(Instant.now());
        item.setId(id);
        item.setCreateAt(current);
        item.setModifiedAt(current);
        items.add(item);
        return id;
    }

    @Override
    public void updateItem(Long itemID, ItemDAO updatedItem) {
        for (ItemDAO item : items) {
            if (Objects.equals(itemID, item.getId())) {
                item.setUserValue(updatedItem.getUserValue());
                item.setTags(updatedItem.getTags());
                item.setModifiedAt(Date.from(Instant.now()));
                return;
            }
        }

        throw new NotFoundException(String.format("Item [%s] not found!", itemID));
     }

    @Override
    public ItemDAO getItem(Long id) {
        for (ItemDAO item : items) {
            if (Objects.equals(id, item.getId())) {
                return item;
            }
        }

        throw new NotFoundException(String.format("Item [%s] not found!", id));
    }

    @Override
    public List<ItemDAO> findItems(SearchParameters parameters) {
        return null;
    }

    @Override
    public void deleteItem(Long id) {}

}
