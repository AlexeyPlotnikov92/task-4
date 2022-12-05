package com.example.demo1.service.impl;

import com.example.demo1.entity.MyItem;
import com.example.demo1.exception.EntityNotFoundException;
import com.example.demo1.repository.MyItemRepository;
import com.example.demo1.service.MyItemService;
import com.example.demo1.writer.MyFileWriter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.util.List;

@Service
@Slf4j
public class MyItemServiceImpl implements MyItemService {
    private final MyItemRepository myItemRepository;

    @Autowired
    public MyItemServiceImpl(MyItemRepository myItemRepository) {
        this.myItemRepository = myItemRepository;
    }

    @Override
    public List<MyItem> findAllItems() {
        return myItemRepository.findAll();
    }

    @Override
    public MyItem findItemById(long id) {
        log.info("findbyId with id - {}", id);
        return myItemRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(String.format("item with this Id = " +
                "%s was not found", id)));
    }

    @Override
    public MyItem saveItem(MyItem item) {
        log.info("save item with id - {} and name - {}", item.getId(), item.getName());
        return myItemRepository.save(item);
    }

    @Override
    public void deleteItemById(long id) {
        log.info("delete item with id - {}", id);
        myItemRepository.deleteById(id);
    }

    @Override
    public void writeToFile() {
        List<MyItem> myItems = myItemRepository.findAll();
        try {
            MyFileWriter.writeToFile(myItems);
            log.info("write to csvfile");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
