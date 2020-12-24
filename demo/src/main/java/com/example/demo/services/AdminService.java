package com.example.demo.services;

import com.example.demo.beans.Item;
import com.example.demo.exceptions.InvalidEntityException;
import com.example.demo.repositories.ItemRepository;
import com.example.demo.services.base.Admin;
import com.example.demo.services.base.ItemService;
import com.example.demo.services.base.ItemServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService extends ItemServiceImpl implements ItemService, Admin {

    public AdminService(ItemRepository itemRepository) {
        super(itemRepository);
    }

    @Override
    public void deleteItem(Item item) throws InvalidEntityException {
        itemRepository.findById(item.getId()).orElseThrow(() -> new InvalidEntityException("cannot delete - id not exist"));
        itemRepository.delete(item);
    }

    @Override
    public void saveItem(Item item) {
        itemRepository.save(item);
    }

    @Override
    public void updateItem(Item item) throws InvalidEntityException {
        itemRepository.findById(item.getId()).orElseThrow(() -> new InvalidEntityException("Cannot update not existing id"));
        itemRepository.saveAndFlush(item);
    }

    @Override
    public Item getItemById(Long id) throws InvalidEntityException {
        return itemRepository.findById(id).orElseThrow(() -> new InvalidEntityException("Item not found"));
    }

    @Override
    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    @Override
    public int getNumberOfAllItems() {
        return itemRepository.findAll().size();
    }
}
