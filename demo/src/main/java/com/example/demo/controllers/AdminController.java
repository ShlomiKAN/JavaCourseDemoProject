package com.example.demo.controllers;

import com.example.demo.beans.Item;
import com.example.demo.services.AdminService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;

    @PostMapping(
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> saveItem(@RequestBody Item item){
        adminService.saveItem(item);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping
    @SneakyThrows
    public ResponseEntity<?> updateItem(@RequestBody Item item){
        adminService.updateItem(item);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @SneakyThrows
    @DeleteMapping
    public ResponseEntity<?> deleteItem(@RequestBody Item item) {
            adminService.deleteItem(item);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @SneakyThrows
    @GetMapping("/{id}")
    public ResponseEntity<?> getItemById(@PathVariable Long id){
            return new ResponseEntity<>(adminService.getItemById(id),HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<?> getAllItems(){
        return new ResponseEntity<>(adminService.getAllItems(),HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getNumberAllItems(){
        return new ResponseEntity<>(adminService.getNumberOfAllItems(),HttpStatus.OK);
    }
}
