package com.example.demo.controllers;

import com.example.demo.beans.Item;
import com.example.demo.controllers.advice.RESTControllerAdvice;
import com.example.demo.exceptions.InvalidEntityException;
import com.example.demo.services.AdminService;
import com.example.demo.utils.TestUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.post;

@ExtendWith(MockitoExtension.class)
class AdminControllerTest {
    @Mock
    private AdminService adminService;

    @InjectMocks
    private AdminController adminController;

    private MockMvc mockMvc;
    private ObjectMapper maapper = new ObjectMapper();

    @BeforeEach
    public void setup(){
        mockMvc = MockMvcBuilders.standaloneSetup(adminController)
                .setControllerAdvice(new RESTControllerAdvice()).build();
    }

    //Save
    @Test
    public void saveItemSuccessTest() throws Exception {
        Item testItem = TestUtils.getTestItem();
        doNothing().when(adminService).saveItem(testItem);
        String uri = "/admin";
        MvcResult mvcResult = mockMvc.perform(post(uri)
                .content(maapper.writeValueAsString(testItem))
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isCreated())
                .andReturn();
    }

    //update
    @Test
    public void updateItemSuccessTest() throws Exception {
        Item testItem = TestUtils.getTestItem();
        doNothing().when(adminService).updateItem(testItem);
        String uri = "/admin";
        mockMvc.perform(put(uri)
                .content(maapper.writeValueAsString(testItem))
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNoContent())
                .andReturn();
    }

    @Test
    public void updateItemNotFountTest() throws Exception {
        Item itemToUpdate = TestUtils.getTestItem();
        itemToUpdate.setId(15L);
        doThrow(new InvalidEntityException("Item not found")).when(adminService).updateItem(itemToUpdate);
        String uri = "/admin";
        mockMvc.perform(put(uri)
                .content(maapper.writeValueAsString(itemToUpdate))
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof InvalidEntityException))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertEquals("Item not found", Objects.requireNonNull(result.getResolvedException()).getMessage()))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
    }

    //delete
    @Test
    public void deleteItemSuccessTest() throws Exception {
        Item testItem = TestUtils.getTestItem();
        doNothing().when(adminService).deleteItem(testItem);
        String uri = "/admin";
        mockMvc.perform(delete(uri)
                .content(maapper.writeValueAsString(testItem))
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNoContent())
                .andReturn();
    }

    @Test
    public void deleteItemNotFountTest() throws Exception {
        Item itemToDelete = TestUtils.getTestItem();
        itemToDelete.setId(15L);
        doThrow(new InvalidEntityException("Item not found")).when(adminService).deleteItem(itemToDelete);
        String uri = "/admin";
        mockMvc.perform(delete(uri)
                .content(maapper.writeValueAsString(itemToDelete))
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof InvalidEntityException))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertEquals("Item not found", Objects.requireNonNull(result.getResolvedException()).getMessage()))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
    }

    //get
    @Test
    public void getItemByIdTest() throws Exception {
        Item expect = TestUtils.getTestItem();
        when(adminService.getItemById(1L)).thenReturn(expect);
        String uri = "/admin/1";
        MvcResult mvcResult = mockMvc.perform(get(uri)
        ).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        Item actual = maapper.readValue(mvcResult.getResponse().getContentAsString(), Item.class);
        Assertions.assertEquals(expect,actual);
    }

    @Test
    public void getItemByIdNotFountTest() throws Exception {
        when(adminService.getItemById(15L)).thenThrow(new InvalidEntityException("Item not found"));
        String uri = "/admin/15";
        mockMvc.perform(get(uri)
        )
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof InvalidEntityException))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertEquals("Item not found", Objects.requireNonNull(result.getResolvedException()).getMessage()))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
    }

    @Test
    public void getAllItemsTest() throws Exception {
        List<Item> expect = TestUtils.getTestItemList();
        when(adminService.getAllItems()).thenReturn(expect);
        String uri = "/admin";
        MvcResult mvcResult = mockMvc.perform(get(uri)
        ).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        List<Item> actual = maapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<>() {
        });
        Assertions.assertEquals(expect,actual);
    }

    @Test
    public void getNumberAllItemsTest() throws Exception {
        int expect = TestUtils.getTestItemList().size();
        when(adminService.getNumberOfAllItems()).thenReturn(expect);
        String uri = "/admin/all";
        MvcResult mvcResult = mockMvc.perform(get(uri)
        ).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        int actual = maapper.readValue(mvcResult.getResponse().getContentAsString(), Integer.class);
        Assertions.assertEquals(expect,actual);
    }
}