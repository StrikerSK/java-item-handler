package com.bohemian.app.service;

import com.bohemian.app.exceptions.NotFoundException;
import com.bohemian.app.entity.ItemDAO;
import com.bohemian.app.repository.ItemRepository;
import jakarta.persistence.LockModeType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
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
    public List<ItemDAO> findItems(Pageable pageable, List<String> tags) {
        if (tags == null || tags.isEmpty()) {
            return getItems(pageable);
        } else {
            return getItems(pageable, tags);
        }
    }

    @Override
    @Transactional
    public void deleteItem(Long id) {
        repository.deleteById(id);
    }

    @Scheduled(fixedDelay = 60000)
    @Transactional
    public void deleteExpiredItems() {
        repository.deleteExpiredItems();
    }

    private List<ItemDAO> getItems(Pageable pageable) {
        return repository.findAll(pageable).toList();
    }

    private List<ItemDAO> getItems(Pageable pageable, List<String> tags) {
        return repository.findAllItems(tags, pageable);
    }

}
