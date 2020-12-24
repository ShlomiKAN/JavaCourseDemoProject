package com.example.demo.repositories;

import com.example.demo.beans.Item;
import com.example.demo.beans.ItemType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item,Long> {

    @Query(value = "SELECT count(*) FROM items WHERE item_type = :item_type ",nativeQuery = true)
    int countItemByType(@Param("item_type") String item_type);

    List<Item> findByItemType(ItemType itemType);

    Item findFirstByOrderByIdDesc();
}
