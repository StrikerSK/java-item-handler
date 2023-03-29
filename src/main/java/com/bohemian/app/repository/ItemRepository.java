package com.bohemian.app.repository;

import com.bohemian.app.entity.ItemDAO;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<ItemDAO, Long> {

//    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<ItemDAO> findById(Long id);

    @Modifying
    @Query("DELETE FROM ItemDAO i where i.modifiedAt <= current_timestamp()")
    void deleteExpiredItems();

}
