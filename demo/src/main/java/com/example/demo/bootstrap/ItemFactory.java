package com.example.demo.bootstrap;

import com.example.demo.beans.Item;
import com.example.demo.beans.ItemType;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ItemFactory {
    private static int COUNT=1;

    public static Item generate(){
        return Item.builder()
                .name(String.format("item %d",COUNT++))
                .itemType(generateRandomItemType())
                .price(generateRandomPrice())
                .build();
    }

    private static ItemType generateRandomItemType() {
        int val = (int) (Math.random() * ItemType.values().length);
        return ItemType.values()[val];
    }

    private static BigDecimal generateRandomPrice() {
        double val = new Random().nextDouble() * 101;
        return BigDecimal.valueOf(val);
    }

    public static List<Item> generateItems(int numOfItems) {
        List<Item> items = new ArrayList<>();
        for (int i = 0 ; i < numOfItems ; i++){
            items.add(generate());
            System.out.println(items.get(i).getName() + "  was created");
        }
        return items;
    }
}
