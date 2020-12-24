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
class SportsServiceTest {
    @Mock
    private ItemRepository itemRepository;

    @InjectMocks
    private SportsService sportsService;

    @Test
    public void saveItem(){
        Item sportItem = TestUtils.getTestItem();
        sportItem.setItemType(ItemType.SPORTS);
        when(itemRepository.save(any())).thenReturn(sportItem);
        Assertions.assertDoesNotThrow(() -> sportsService.saveItem(sportItem));
    }

    @ParameterizedTest
    @EnumSource(value = ItemType.class,names = {"ELECTRICITY","FOOD","OTHER"})
    public void saveItemError(ItemType itemType) throws InvalidOperationException {
        Item itemToSave =  TestUtils.getTestItem();
        itemToSave.setItemType(itemType);
        Assertions.assertThrows(InvalidOperationException.class,() -> sportsService.saveItem(itemToSave));
    }

    @Test
    public void updateItem(){
        Item sportItem = TestUtils.getTestItem();
        sportItem.setItemType(ItemType.SPORTS);
        when(itemRepository.findById(anyLong())).thenReturn(Optional.of(sportItem));
        when(itemRepository.saveAndFlush(any())).thenReturn(sportItem);
        Assertions.assertDoesNotThrow(() -> sportsService.updateItem(sportItem));
    }

    @ParameterizedTest
    @EnumSource(value = ItemType.class,names = {"ELECTRICITY","FOOD","OTHER"})
    public void updateItemError(ItemType itemType) throws InvalidOperationException {
        Item itemToSave =  TestUtils.getTestItem();
        itemToSave.setItemType(itemType);
        Assertions.assertThrows(InvalidOperationException.class,() -> sportsService.updateItem(itemToSave));
    }

    @Test
    public void getItemById() throws InvalidOperationException, InvalidEntityException {
        Item expected = TestUtils.getTestItem();
        expected.setItemType(ItemType.SPORTS);
        when(itemRepository.findById(anyLong())).thenReturn(Optional.of(expected));
        Item actual = sportsService.getItemById(1L);
        Assertions.assertEquals(expected,actual);
    }

    @Test
    public void getItemByIdErrorNotExist(){
        Assertions.assertThrows(InvalidEntityException.class,()->sportsService.getItemById(2L));
    }

    @Test
    public void getItemByIdErrorOutsideDomain(){
        Item item = TestUtils.getTestItem();
        item.setItemType(ItemType.FOOD);
        when(itemRepository.findById(anyLong())).thenReturn(Optional.of(item));
        Assertions.assertThrows(InvalidOperationException.class,()->sportsService.getItemById(item.getId()));
    }

    @Test
    public void getAllItems(){
        List<Item> expected = TestUtils.getTestItemList().stream()
                .filter(item -> item.getItemType().equals(ItemType.SPORTS)).collect(toList());
        when(itemRepository.findByItemType(ItemType.SPORTS)).thenReturn(expected);
        List<Item> actual = sportsService.getAllItems();
        Assertions.assertEquals(expected,actual);
    }

    @Test
    public void getNumberOfAllItems(){
        List<Item> items = TestUtils.getTestItemList().stream()
                .filter(item -> item.getItemType().equals(ItemType.SPORTS)).collect(toList());
        int expected = items.size();
        when(itemRepository.countItemByType(ItemType.SPORTS.name())).thenReturn(expected);
        int actual = sportsService.getNumberOfAllItems();
        Assertions.assertEquals(expected,actual);
    }
}