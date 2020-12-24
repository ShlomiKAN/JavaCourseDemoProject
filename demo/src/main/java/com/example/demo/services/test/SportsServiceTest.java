package com.example.demo.services.test;

import com.example.demo.beans.Item;
import com.example.demo.beans.ItemType;
import com.example.demo.bootstrap.ItemFactory;
import com.example.demo.repositories.ItemRepository;
import com.example.demo.services.SportsService;
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
@Order(4)
public class SportsServiceTest implements CommandLineRunner {
    private final SportsService sportsService;
    private final ItemRepository itemRepository;

    @Override
    public void run(String... args) throws Exception {
        System.out.println(ArtFont.SPORT_SERVICE_TEST);

        DeveloperTestPrinter.print("Get sport items");
        TablePrinter.print(sportsService.getAllItems());
        Thread.sleep(500);

        DeveloperTestPrinter.print("Add item");
        Item item = ItemFactory.generate();
        item.setItemType(ItemType.SPORTS);
        sportsService.saveItem(item);
        TablePrinter.print(sportsService.getAllItems());
        Thread.sleep(500);

        DeveloperTestPrinter.print("Update sport item");
        System.out.println("Get sport item");
        Item itemToChange = itemRepository.findFirstByOrderByIdDesc();
        TablePrinter.print(itemToChange);
        System.out.println("Update item (name:New sport name, price:55.55");
        itemToChange.setName("New sport name");
        itemToChange.setPrice(BigDecimal.valueOf(55.55));
        sportsService.updateItem(itemToChange);
        itemToChange.setName("Changed sport item");
        TablePrinter.print(sportsService.getItemById(itemToChange.getId()));
        Thread.sleep(500);

        DeveloperTestPrinter.print("Add outside of sport domain");
        Item notSportItem = ItemFactory.generate();
        notSportItem.setItemType(ItemType.FOOD);
        try{
            sportsService.saveItem(notSportItem);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        Thread.sleep(500);

        DeveloperTestPrinter.print("Update outside of sport domain");
        Item updateNonSportItem = ItemFactory.generate();
        try{
            updateNonSportItem.setItemType(ItemType.FOOD);
            itemRepository.save(updateNonSportItem);
            updateNonSportItem.setName("new name");
            sportsService.updateItem(updateNonSportItem);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        Thread.sleep(500);

        DeveloperTestPrinter.print("Get NOT sport item");
        try{
            sportsService.getItemById(updateNonSportItem.getId());
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        Thread.sleep(500);

        DeveloperTestPrinter.print("Count sport items: " + sportsService.getNumberOfAllItems() + " items");
        Thread.sleep(500);

        System.out.println("Get not exist item");
        try{
            sportsService.getItemById(100L);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        Thread.sleep(500);
    }
}

