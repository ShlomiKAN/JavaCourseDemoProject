package com.example.demo.services;

import com.example.demo.beans.Item;
import com.example.demo.beans.ItemType;
import com.example.demo.exceptions.InvalidEntityException;
import com.example.demo.exceptions.InvalidOperationException;
import com.example.demo.repositories.ItemRepository;
import com.example.demo.utils.TestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ElectricityServiceTest {
    @Mock
    private ItemRepository itemRepository;

    @InjectMocks
    private ElectricityService electricityService;

    @Test
    public void saveItem(){
        Item electricityItem = TestUtils.getTestItem();
        electricityItem.setItemType(ItemType.ELECTRICITY);
        when(itemRepository.save(any())).thenReturn(electricityItem);
        Assertions.assertDoesNotThrow(() -> electricityService.saveItem(electricityItem));
    }

    @ParameterizedTest
    @EnumSource(value = ItemType.class,names = {"SPORTS","FOOD","OTHER"})
    public void saveItemError(ItemType itemType) throws InvalidOperationException {
        Item itemToSave =  TestUtils.getTestItem();
        itemToSave.setItemType(itemType);
        Assertions.assertThrows(InvalidOperationException.class,() -> electricityService.saveItem(itemToSave));
    }

    @Test
    public void updateItem(){
        Item electricityItem = TestUtils.getTestItem();
        electricityItem.setItemType(ItemType.ELECTRICITY);
        when(itemRepository.findById(anyLong())).thenReturn(Optional.of(electricityItem));
        when(itemRepository.saveAndFlush(any())).thenReturn(electricityItem);
        Assertions.assertDoesNotThrow(() -> electricityService.updateItem(electricityItem));
    }

    @ParameterizedTest
    @EnumSource(value = ItemType.class,names = {"SPORTS","FOOD","OTHER"})
    public void updateItemError(ItemType itemType) throws InvalidOperationException {
        Item itemToSave =  TestUtils.getTestItem();
        itemToSave.setItemType(itemType);
        Assertions.assertThrows(InvalidOperationException.class,() -> electricityService.updateItem(itemToSave));
    }

    @Test
    public void getItemById() throws InvalidOperationException, InvalidEntityException {
        Item expected = TestUtils.getTestItem();
        expected.setItemType(ItemType.ELECTRICITY);
        when(itemRepository.findById(anyLong())).thenReturn(Optional.of(expected));
        Item actual = electricityService.getItemById(1L);
        Assertions.assertEquals(expected,actual);
    }

    @Test
    public void getItemByIdErrorNotExist(){
        Assertions.assertThrows(InvalidEntityException.class,()->electricityService.getItemById(2L));
    }

    @Test
    public void getItemByIdErrorOutsideDomain(){
        Item item = TestUtils.getTestItem();
        item.setItemType(ItemType.FOOD);
        when(itemRepository.findById(anyLong())).thenReturn(Optional.of(item));
        Assertions.assertThrows(InvalidOperationException.class,()->electricityService.getItemById(item.getId()));
    }

    @Test
    public void getAllItems(){
        List<Item> expected = TestUtils.getTestItemList().stream()
                .filter(item -> item.getItemType().equals(ItemType.ELECTRICITY)).collect(toList());
        when(itemRepository.findByItemType(ItemType.ELECTRICITY)).thenReturn(expected);
        List<Item> actual = electricityService.getAllItems();
        Assertions.assertEquals(expected,actual);
    }

    @Test
    public void getNumberOfAllItems(){
        List<Item> items = TestUtils.getTestItemList().stream()
                .filter(item -> item.getItemType().equals(ItemType.ELECTRICITY)).collect(toList());
        int expected = items.size();
        when(itemRepository.countItemByType(ItemType.ELECTRICITY.name())).thenReturn(expected);
        int actual = electricityService.getNumberOfAllItems();
        Assertions.assertEquals(expected,actual);
    }
}