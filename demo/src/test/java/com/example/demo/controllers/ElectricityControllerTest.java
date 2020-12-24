package com.example.demo.controllers;

import com.example.demo.beans.Item;
import com.example.demo.beans.ItemType;
import com.example.demo.controllers.advice.RESTControllerAdvice;
import com.example.demo.exceptions.InvalidEntityException;
import com.example.demo.exceptions.InvalidOperationException;
import com.example.demo.services.ElectricityService;
import com.example.demo.utils.TestUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Objects;

import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class ElectricityControllerTest {

    @Mock
    private ElectricityService electricityService;

    @InjectMocks
    private ElectricityController electricityController;

    private MockMvc mockMvc;
    private ObjectMapper maapper = new ObjectMapper();

    @BeforeEach
    public void setup()
    {
        mockMvc = MockMvcBuilders.standaloneSetup(electricityController)
                .setControllerAdvice(new RESTControllerAdvice()).build();
    }

    //save
    @Test
    public void saveItemSuccessTest() throws Exception {
        Item testItem = TestUtils.getTestItem();
        testItem.setItemType(ItemType.ELECTRICITY);
        doNothing().when(electricityService).saveItem(testItem);
        String uri = "/electricity";
        mockMvc.perform(post(uri)
                .content(maapper.writeValueAsString(testItem))
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isCreated())
                .andReturn();
    }

    @ParameterizedTest
    @EnumSource(value = ItemType.class,names = {"SPORTS","FOOD","OTHER"})
    public void saveItemOutOfDomainTest(ItemType itemType) throws Exception {
        Item itemToSave = TestUtils.getTestItem();
        itemToSave.setItemType(itemType);
        doThrow(new InvalidOperationException("cannot add an item outside your domain"))
                .when(electricityService).saveItem(itemToSave);
        String uri = "/electricity";
        mockMvc.perform(post(uri)
                .content(maapper.writeValueAsString(itemToSave))
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof InvalidOperationException))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertEquals("cannot add an item outside your domain"
                        , Objects.requireNonNull(result.getResolvedException()).getMessage()))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
    }

    //update
    @Test
    public void updateItemSuccessTest() throws Exception {
        Item testItem = TestUtils.getTestItem();
        doNothing().when(electricityService).updateItem(testItem);
        String uri = "/electricity";
        mockMvc.perform(put(uri)
                .content(maapper.writeValueAsString(testItem))
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNoContent())
                .andReturn();
    }

    @ParameterizedTest
    @EnumSource(value = ItemType.class,names = {"SPORTS","FOOD","OTHER"})
    public void updateItemOutOfDomainTest(ItemType itemType) throws Exception {
        Item itemToUpdate = TestUtils.getTestItem();
        itemToUpdate.setItemType(itemType);
        doThrow(new InvalidOperationException("cannot update an item outside your domain"))
                .when(electricityService).updateItem(itemToUpdate);
        String uri = "/electricity";
        mockMvc.perform(put(uri)
                .content(maapper.writeValueAsString(itemToUpdate))
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof InvalidOperationException))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertEquals("cannot update an item outside your domain"
                        , Objects.requireNonNull(result.getResolvedException()).getMessage()))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
    }

    //get
    @Test
    public void getItemByIdTest() throws Exception {
        Item expect = TestUtils.getTestItem();
        expect.setItemType(ItemType.ELECTRICITY);
        expect.setId(1L);
        when(electricityService.getItemById(expect.getId())).thenReturn(expect);
        String uri = "/electricity/1";
        MvcResult mvcResult = mockMvc.perform(get(uri)
        ).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        Item actual = maapper.readValue(mvcResult.getResponse().getContentAsString(), Item.class);
        Assertions.assertEquals(expect,actual);
    }

    @Test
    public void getItemByIdNotFountTest() throws Exception {
        when(electricityService.getItemById(15L)).thenThrow(new InvalidEntityException("Item not found"));
        String uri = "/electricity/15";
        mockMvc.perform(get(uri)
        )
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof InvalidEntityException))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertEquals("Item not found", Objects.requireNonNull(result.getResolvedException()).getMessage()))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
    }

    @Test
    public void getItemByIdOutOfDomainTest() throws Exception {
        doThrow(new InvalidOperationException("cannot add an item outside your domain"))
                .when(electricityService).getItemById(1L);
        String uri = "/electricity/1";
        mockMvc.perform(get(uri)
        )
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof InvalidOperationException))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertEquals("cannot add an item outside your domain", Objects.requireNonNull(result.getResolvedException()).getMessage()))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
    }

    @Test
    public void getAllItemsTest() throws Exception {
        List<Item> expect = TestUtils.getTestItemList().stream()
                .filter(item -> item.getItemType().equals(ItemType.ELECTRICITY)).collect(toList());
        when(electricityService.getAllItems()).thenReturn(expect);
        String uri = "/electricity";
        MvcResult mvcResult = mockMvc.perform(get(uri)
        ).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        List<Item> actual = maapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<List<Item>>(){});
        Assertions.assertEquals(expect,actual);
    }

    @Test
    public void getNumberAllItemsTest() throws Exception {
        int expect = TestUtils.getTestItemList().size();
        when(electricityService.getNumberOfAllItems()).thenReturn(expect);
        String uri = "/electricity/all";
        MvcResult mvcResult = mockMvc.perform(get(uri)
        ).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        int actual = maapper.readValue(mvcResult.getResponse().getContentAsString(), int.class);
        Assertions.assertEquals(expect,actual);
    }
}