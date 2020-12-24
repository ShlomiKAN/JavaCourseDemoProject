package com.example.demo.services;

import com.example.demo.beans.Item;
import com.example.demo.beans.ItemType;
import com.example.demo.exceptions.InvalidEntityException;
import com.example.demo.exceptions.InvalidOperationException;
import com.example.demo.repositories.ItemRepository;
import com.example.demo.services.base.ItemService;
import com.example.demo.services.base.ItemServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ElectricityService  extends ItemServiceImpl implements ItemService {

    public ElectricityService(ItemRepository itemRepository) {
        super(itemRepository);
    }

    @Override
    public void saveItem(Item item) throws InvalidOperationException {
        if(! item.getItemType().equals(ItemType.ELECTRICITY))
            throw new InvalidOperationException("cannot add an item outside your domain");

        itemRepository.save(item);
    }

    @Override
    public void updateItem(Item item) throws InvalidEntityException,InvalidOperationException {
        if(! item.getItemType().equals(ItemType.ELECTRICITY))
            throw new InvalidOperationException("cannot update an item outside your domain");
        itemRepository.findById(item.getId()).orElseThrow(() -> new InvalidEntityException("cannot update not existing id"));

        itemRepository.saveAndFlush(item);
    }

    @Override
    public Item getItemById(Long id) throws InvalidEntityException,InvalidOperationException {
        Item item = itemRepository.findById(id).orElseThrow(() -> new InvalidEntityException("Item not found"));
        if(! item.getItemType().equals(ItemType.ELECTRICITY))
            throw new InvalidOperationException("cannot get an item outside your domain");

        return item;
    }

    @Override
    public List<Item> getAllItems() {
        return itemRepository.findByItemType(ItemType.ELECTRICITY);
    }

    @Override
    public int getNumberOfAllItems() {
        return itemRepository.countItemByType(ItemType.ELECTRICITY.name());
    }
}

