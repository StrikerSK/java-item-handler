package com.bohemian.app.repository;

import com.bohemian.app.entity.ItemDAO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<ItemDAO, Long> {

    @Modifying
    @Query("DELETE FROM ItemDAO i where i.modifiedAt <= current_timestamp()")
    void deleteExpiredItems();

}
