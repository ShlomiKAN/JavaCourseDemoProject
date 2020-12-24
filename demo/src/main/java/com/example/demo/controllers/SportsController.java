package com.example.demo.controllers;

import com.example.demo.beans.Item;
import com.example.demo.exceptions.InvalidEntityException;
import com.example.demo.exceptions.InvalidOperationException;
import com.example.demo.services.SportsService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sport")
@RequiredArgsConstructor
public class SportsController {

    private final SportsService sportsService;

    @PostMapping
    @SneakyThrows
    public ResponseEntity<?> saveItem(@RequestBody Item item){
        sportsService.saveItem(item);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<?> updateItem(@RequestBody Item item) throws InvalidOperationException {
            sportsService.updateItem(item);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getItemById(@PathVariable Long id) throws InvalidOperationException, InvalidEntityException {
        return new ResponseEntity<>(sportsService.getItemById(id),HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<?> getAllItems(){
        return new ResponseEntity<>(sportsService.getAllItems(),HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getNumberAllItems(){
        return new ResponseEntity<>(sportsService.getNumberOfAllItems(),HttpStatus.OK);
    }
}
