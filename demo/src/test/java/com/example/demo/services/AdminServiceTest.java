package com.example.demo.services;


import com.example.demo.beans.Item;
import com.example.demo.exceptions.InvalidEntityException;
import com.example.demo.repositories.ItemRepository;
import com.example.demo.utils.TestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AdminServiceTest {

    @InjectMocks
    private AdminService adminService;

    @Mock
    private ItemRepository itemRepository;

    @Test
    public void getItemById() throws InvalidEntityException {
        Item expected = TestUtils.getTestItem();
        when(itemRepository.findById(anyLong())).thenReturn(Optional.of(expected));
        Item actual = adminService.getItemById(1L);
        Assertions.assertEquals(expected,actual);
    }

    @Test
    public void getItemByIdError(){
        when(itemRepository.findById(anyLong())).thenReturn(Optional.empty());
        Assertions.assertThrows(InvalidEntityException.class,()->adminService.getItemById(1L));
    }

    @Test
    public void saveItem(){
        when(itemRepository.save(any())).thenReturn(TestUtils.getTestItem());
        Assertions.assertDoesNotThrow(()->adminService.saveItem(TestUtils.getTestItem()));
    }

    @Test
    public void updateItem(){
        when(itemRepository.saveAndFlush(any())).thenReturn(TestUtils.getTestItem());
        when(itemRepository.findById(anyLong())).thenReturn(Optional.of(TestUtils.getTestItem()));
        Assertions.assertDoesNotThrow(()->adminService.updateItem(TestUtils.getTestItem()));
    }

    @Test
    public void deleteItem(){
        doNothing().when(itemRepository).delete(any());
        when(itemRepository.findById(anyLong())).thenReturn(Optional.of(TestUtils.getTestItem()));
        Assertions.assertDoesNotThrow(()->adminService.deleteItem(TestUtils.getTestItem()));
    }

    @Test
    public void getAllItems(){
        List<Item> expected = TestUtils.getTestItemList();
        when(itemRepository.findAll()).thenReturn(expected);
        List<Item> actual = adminService.getAllItems();
        Assertions.assertEquals(expected,actual);
    }

    @Test
    public void getNumberOfAllItems(){
        int expected = TestUtils.getTestItemList().size();
        when(itemRepository.findAll()).thenReturn(TestUtils.getTestItemList());
        int actual = adminService.getNumberOfAllItems();
        Assertions.assertEquals(expected,actual);
    }
}