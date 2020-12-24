package com.example.demo.services.test;

import com.example.demo.beans.Item;
import com.example.demo.bootstrap.ItemFactory;
import com.example.demo.services.AdminService;
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
@Order(2)
class AdminServiceTest implements CommandLineRunner {

    private final AdminService adminService;
    @Override
    public void run(String... args) throws Exception {

        System.out.println(ArtFont.ADMIN_SERVICE_TEST);
        DeveloperTestPrinter.print("Get all items (10)");
        TablePrinter.print(adminService.getAllItems());
        Thread.sleep(500);

        DeveloperTestPrinter.print("Add item");
        Item item = ItemFactory.generate();
        adminService.saveItem(item);
        TablePrinter.print(adminService.getAllItems());
        Thread.sleep(500);

        DeveloperTestPrinter.print("Update item");
        System.out.println("Get item (id:11)");
        Item itemToChange = adminService.getItemById(11L);
        TablePrinter.print(itemToChange);
        System.out.println("Update item (id:11)");
        itemToChange.setName("New name");
        itemToChange.setPrice(BigDecimal.valueOf(111.33));
        System.out.println("Changed item (id:11)");
        adminService.updateItem(itemToChange);
        TablePrinter.print(adminService.getItemById(11L));
        Thread.sleep(500);

        DeveloperTestPrinter.print("Get single item (id:11)");
        TablePrinter.print(adminService.getItemById(11L));
        Thread.sleep(500);

        DeveloperTestPrinter.print("Get non existing item (id:12)");
        try {
            TablePrinter.print(adminService.getItemById(12L));
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        Thread.sleep(500);

        DeveloperTestPrinter.print("Delete item (id:11)");
        try{
            adminService.deleteItem(adminService.getItemById(11L));
            System.out.println("Get item (id:11)");
            TablePrinter.print(adminService.getItemById(11L));
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        Thread.sleep(500);
    }
}