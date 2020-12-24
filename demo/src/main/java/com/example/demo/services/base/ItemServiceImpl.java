package com.example.demo.services.base;

import com.example.demo.repositories.ItemRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ItemServiceImpl{
    protected final ItemRepository itemRepository;
}
