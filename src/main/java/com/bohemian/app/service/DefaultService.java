package com.bohemian.app.service;

import com.bohemian.app.exceptions.NotFoundException;
import com.bohemian.app.entity.ItemDAO;
import com.bohemian.app.repository.ItemRepository;
import jakarta.persistence.LockModeType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DefaultService implements IItemService {

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
        item.setUserValue(updatedItem.getUserValue());
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
    public List<ItemDAO> getItems() {
        return repository.findAllItems("code");
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

}
