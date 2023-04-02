package com.bohemian.app.repository;

import com.bohemian.app.entity.ItemDAO;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<ItemDAO, Long> {

    @Modifying
    @Query("DELETE FROM ItemDAO i where i.modifiedAt <= current_timestamp()")
    void deleteExpiredItems();

    @Query(value = "SELECT * FROM items i JOIN item_tags it ON i.id=it.item_id WHERE i.item_value >= ?1 AND i.item_value <= ?2", nativeQuery = true)
    List<ItemDAO> findItemsWithWithoutTags(Integer lowerBound, Integer upperBound, Pageable pageable);

    @Query(value = "SELECT * FROM items i JOIN item_tags it ON i.id=it.item_id WHERE it.tag IN (?1) AND i.item_value >= ?2 AND i.item_value <= ?3", nativeQuery = true)
    List<ItemDAO> findItemsWithAllFilters(List<String> tags, Integer lowerBound, Integer upperBound, Pageable pageable);

}
