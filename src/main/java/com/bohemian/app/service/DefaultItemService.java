package com.bohemian.app.service;

import com.bohemian.app.entity.SearchParameters;
import com.bohemian.app.exceptions.NotFoundException;
import com.bohemian.app.entity.ItemDAO;
import com.bohemian.app.repository.ItemRepository;
import jakarta.persistence.LockModeType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class DefaultItemService implements IItemService {

    @Autowired
    private ItemRepository repository;

    @Override
    @Transactional
    public Long createItem(ItemDAO item) {
        return repository.save(item).getId();
    }

    @Override
    @Transactional
    public void updateItem(Long itemID, ItemDAO updatedItem) {
        ItemDAO item = repository.findById(itemID).orElseThrow(() -> new NotFoundException(String.format("Item [%s] not found!", itemID)));
        List<String> newChapters = new ArrayList<>(updatedItem.getTags());
        item.setUserValue(updatedItem.getUserValue());
        item.setTags(newChapters);
        repository.save(item);
    }

    @Override
    @Lock(LockModeType.OPTIMISTIC_FORCE_INCREMENT)
    @Transactional(readOnly = true)
    public ItemDAO getItem(Long itemID) {
        return repository.findById(itemID).orElseThrow(() -> new NotFoundException(String.format("Item [%s] not found!", itemID)));
    }

    @Override
    @Lock(LockModeType.OPTIMISTIC_FORCE_INCREMENT)
    @Transactional(readOnly = true)
    public List<ItemDAO> findItems(SearchParameters parameters) {
        List<String> tags = parameters.getTags();
        if (tags == null || tags.isEmpty()) {
            return repository.findItemsWithWithoutTags(parameters.getLowerBound(), parameters.getUpperBound(), parameters.getPageable());
        } else {
            return repository.findItemsWithAllFilters(parameters.getTags(), parameters.getLowerBound(), parameters.getUpperBound(), parameters.getPageable());
        }
    }

    @Override
    @Transactional
    public void deleteItem(Long id) {
        repository.deleteById(id);
    }

    @Scheduled(fixedDelayString = "${bohemian.deletion.delay:60000}")
    @Transactional
    public void deleteExpiredItems() {
        repository.deleteExpiredItems();
    }

}
