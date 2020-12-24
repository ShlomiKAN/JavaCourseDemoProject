package com.example.demo.services.base;

import com.example.demo.beans.Item;
import com.example.demo.exceptions.InvalidEntityException;

public interface Admin {
    void deleteItem(Item item) throws InvalidEntityException;
}