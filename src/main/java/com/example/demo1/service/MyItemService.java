package com.example.demo1.service;

import com.example.demo1.entity.MyItem;

import java.util.List;

public interface MyItemService {

    List<MyItem> findAllItems();

    MyItem findItemById(long id);

    MyItem saveItem(MyItem item);

    void deleteItemById(long id);

    void writeToFile();
}
