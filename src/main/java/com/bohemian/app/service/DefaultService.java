package com.bohemian.app.service;

import com.bohemian.app.NotFoundException;
import com.bohemian.app.entity.ItemDAO;
import com.bohemian.app.repository.ItemRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultService implements IItemService {

    @Autowired
    private ItemRepository repository;

    @Override
    public Long createItem(ItemDAO item) {
        return repository.save(item).getId();
    }

    @Override
    public void updateItem(Long itemID, ItemDAO item) {
        if (repository.findById(itemID).isPresent()) {
            item.setId(itemID);
            repository.save(item);
        } else {
            throw new NotFoundException(String.format("Item [%s] not found!", itemID));
        }
    }

    @Override
    public ItemDAO getItem(Long itemID) {
        return repository.findById(itemID).orElseThrow(() -> new NotFoundException(String.format("Item [%s] not found!", itemID)));
    }

    @Override
    public List<ItemDAO> getItems() {
        return repository.findAll();
    }

    @Override
    public void deleteItem(Long id) {
        repository.deleteById(id);
    }

    @Scheduled(fixedDelay = 60000)
    @Transactional
    public void deleteExpiredItems() {
        repository.deleteExpiredItems();
    }

}
