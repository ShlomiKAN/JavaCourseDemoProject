package com.example.demo.services.test;

import com.example.demo.beans.Item;
import com.example.demo.beans.ItemType;
import com.example.demo.bootstrap.ItemFactory;
import com.example.demo.repositories.ItemRepository;
import com.example.demo.services.ElectricityService;
import com.example.demo.utils.ArtFont;
import com.example.demo.utils.DeveloperTestPrinter;
import com.example.demo.utils.TablePrinter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
@Order(3)
class ElectricityServiceTest implements CommandLineRunner {
    private final ElectricityService electricityService;
    private final ItemRepository itemRepository;

    @Override
    public void run(String... args) throws Exception {
        System.out.println(ArtFont.ELECTRICITY_SERVICE_TEST);

        DeveloperTestPrinter.print("Get electricity items");
        TablePrinter.print(electricityService.getAllItems());
        Thread.sleep(500);

        DeveloperTestPrinter.print("Add item");
        Item item = ItemFactory.generate();
        item.setItemType(ItemType.ELECTRICITY);
        electricityService.saveItem(item);
        TablePrinter.print(electricityService.getAllItems());
        Thread.sleep(500);

        DeveloperTestPrinter.print("Update electricity item");
        System.out.println("Get electricity item");
        Item itemToChange = itemRepository.findFirstByOrderByIdDesc();
        TablePrinter.print(itemToChange);
        System.out.println("Update item (name:New electricity name, price:10.5");
        itemToChange.setName("New electricity name");
        itemToChange.setPrice(BigDecimal.valueOf(10.5));
        electricityService.updateItem(itemToChange);
        itemToChange.setName("Changed electricity item");
        TablePrinter.print(electricityService.getItemById(itemToChange.getId()));
        Thread.sleep(500);

        DeveloperTestPrinter.print("Add outside of electricity domain");
        Item notElectricityItem = ItemFactory.generate();
        notElectricityItem.setItemType(ItemType.SPORTS);
        try{
            electricityService.saveItem(notElectricityItem);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        Thread.sleep(500);

        DeveloperTestPrinter.print("Update outside of electricity domain");
        Item updateNonElectricityItem = ItemFactory.generate();
        try{
            updateNonElectricityItem.setItemType(ItemType.FOOD);
            itemRepository.save(updateNonElectricityItem);
            updateNonElectricityItem.setName("new name");
            electricityService.updateItem(updateNonElectricityItem);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        Thread.sleep(500);

        DeveloperTestPrinter.print("Get NOT electricity item");
        try{
            electricityService.getItemById(updateNonElectricityItem.getId());
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        Thread.sleep(500);

        DeveloperTestPrinter.print("Count electricity items: " + electricityService.getNumberOfAllItems() + " items");
        Thread.sleep(500);

        System.out.println("Get not exist item");
        try{
            electricityService.getItemById(100L);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        Thread.sleep(500);
    }
}