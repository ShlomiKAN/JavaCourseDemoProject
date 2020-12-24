package com.example.demo.controllers;

import com.example.demo.beans.Item;
import com.example.demo.exceptions.InvalidEntityException;
import com.example.demo.exceptions.InvalidOperationException;
import com.example.demo.services.ElectricityService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/electricity")
@RequiredArgsConstructor
public class ElectricityController {

    private final ElectricityService electricityService;

    @PostMapping
    @SneakyThrows
    public ResponseEntity<?> saveItem(@RequestBody Item item){
        electricityService.saveItem(item);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping
    @SneakyThrows
    public ResponseEntity<?> updateItem(@RequestBody Item item){
        electricityService.updateItem(item);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getItemById(@PathVariable Long id) throws InvalidOperationException, InvalidEntityException {
            return new ResponseEntity<>(electricityService.getItemById(id),HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<?> getAllItems(){
        return new ResponseEntity<>(electricityService.getAllItems(),HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getNumberAllItems(){
        return new ResponseEntity<>(electricityService.getNumberOfAllItems(),HttpStatus.OK);
    }
}
