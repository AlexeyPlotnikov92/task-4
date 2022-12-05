package com.example.demo1.repository;

import com.example.demo1.Demo1ApplicationTests;
import com.example.demo1.entity.MyItem;
import com.example.demo1.service.MyItemService;
import com.example.demo1.writer.MyFileWriter;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Slf4j
public class MyItemRepositoryTest extends Demo1ApplicationTests {
    @Autowired
    MyItemRepository myItemRepository;
    @Autowired
    MyItemService myItemService;


    @Test
    void checkCrud() {
        int initialSize = myItemRepository.findAll().size();
        Random random = new Random();
        String expectedName = UUID.randomUUID().toString();
        Integer expectedYearsOfRelease = random.nextInt(2000);
        MyItem myItem = myItemRepository.save(new MyItem(null, expectedName, expectedYearsOfRelease));
        Assertions.assertNotNull(myItem.getId());
        Assertions.assertEquals(expectedName, myItem.getName());
        Assertions.assertEquals(expectedYearsOfRelease, myItem.getYearOfRelease());

        Assertions.assertEquals(initialSize + 1, myItemRepository.findAll().size());

        MyItem byId = myItemRepository.findById(myItem.getId()).orElseThrow();
        Assertions.assertEquals(myItem.getId(), byId.getId());
        Assertions.assertEquals(expectedName, byId.getName());
        Assertions.assertEquals(expectedYearsOfRelease, byId.getYearOfRelease());

        String updatedName = UUID.randomUUID().toString();
        Integer updatedYearOfRelease = random.nextInt(2000);
        myItemRepository.save(new MyItem(myItem.getId(), updatedName, updatedYearOfRelease));
        Assertions.assertEquals(initialSize + 1, myItemRepository.findAll().size());
        Assertions.assertNotEquals(byId, myItemRepository.findById(myItem.getId()));

        byId = myItemRepository.findById(myItem.getId()).orElseThrow();
        Assertions.assertEquals(myItem.getId(), byId.getId());
        Assertions.assertEquals(updatedName, byId.getName());
        Assertions.assertEquals(updatedYearOfRelease, byId.getYearOfRelease());

        myItemRepository.deleteById(myItem.getId());
        Assertions.assertEquals(initialSize, myItemRepository.findAll().size());
    }

    @Test
    void checkWriteToFile() {
        myItemService.writeToFile();
        MyItem item = new MyItem(null, UUID.randomUUID().toString(),111);
        MyItem saveItem = myItemService.saveItem(item);
        try {
            myItemService.writeToFile();
            Assertions.assertEquals(myItemService.findAllItems().size(), MyFileWriter.readToFile().size());
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            myItemService.deleteItemById(saveItem.getId());
            myItemService.writeToFile();
        }
    }
}
