package com.example.demo.services.base;

import com.example.demo.beans.Item;
import com.example.demo.exceptions.InvalidEntityException;
import com.example.demo.exceptions.InvalidOperationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ItemService {
    void saveItem(Item item) throws InvalidOperationException;
    void updateItem(Item item) throws InvalidEntityException,InvalidOperationException;
    Item getItemById(Long id) throws InvalidEntityException,InvalidOperationException;
    List<Item> getAllItems();
    int getNumberOfAllItems();
}
