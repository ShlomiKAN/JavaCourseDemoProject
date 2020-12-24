package com.example.demo.utils;

import com.example.demo.beans.Item;
import com.example.demo.beans.ItemType;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TestUtils {
    public static Item getTestItem(){
        return Item.builder()
                .id(1L)
                .price(BigDecimal.valueOf(10))
                .itemType(ItemType.ELECTRICITY)
                .name("Test Item")
                .build();
    }

    public static List<Item> getTestItemList(){
        List<Item> items = new ArrayList<>();
        for(int i = 0 ; i < 10 ; i++) {
            int typeVal = new Random().nextInt(ItemType.values().length);
            items.add(Item.builder()
                    .id(Long.valueOf(i))
                    .price(BigDecimal.valueOf(i*10))
                    .itemType(ItemType.values()[typeVal])
                    .name(String.format("Test Item %s",i))
                    .build());
        }
        return items;
    }
}
