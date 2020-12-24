package com.example.demo.bootstrap;

import com.example.demo.repositories.ItemRepository;
import com.example.demo.utils.ArtFont;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
@RequiredArgsConstructor
public class InitData implements CommandLineRunner {

    private final ItemRepository itemRepository;

    @Override
    public void run(String... args) throws Exception {
        System.out.println(ArtFont.ITEMS_FACTORY);
        itemRepository.saveAll(ItemFactory.generateItems(10));
    }
}
