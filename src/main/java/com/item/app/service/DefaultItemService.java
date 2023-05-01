package com.item.app.service;

import com.item.app.exceptions.ConflictException;
import com.item.app.utils.SearchParameters;
import com.item.app.exceptions.NotFoundException;
import com.item.app.entity.ItemDAO;
import com.item.app.repository.ItemRepository;
import jakarta.persistence.LockModeType;
import jakarta.persistence.OptimisticLockException;
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

        try {
            repository.save(item);
        } catch (OptimisticLockException ex) {
            throw new ConflictException(String.format("Cannot update item [%s], because it was updated by other user!", itemID));
        }
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

    @Scheduled(fixedDelayString = "${item.deletion.delay:60000}")
    @Transactional
    public void deleteExpiredItems() {
        repository.deleteExpiredItems();
    }

}
