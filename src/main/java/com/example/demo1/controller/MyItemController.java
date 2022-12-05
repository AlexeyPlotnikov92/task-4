package com.example.demo1.controller;

import com.example.demo1.entity.MyItem;
import com.example.demo1.service.MyItemService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Controller
@RequestMapping("/admin/items")
public class MyItemController {
    private final MyItemService myItemService;

    @Autowired
    public MyItemController(MyItemService myItemService) {
        this.myItemService = myItemService;
    }

    @GetMapping
    public ModelAndView getItems() {
        ModelAndView modelAndView = new ModelAndView("items");
        modelAndView.addObject("items", myItemService.findAllItems());
        return modelAndView;
    }

    @GetMapping("/{id}")
    public ModelAndView getItemById(@PathVariable long id) {
        ModelAndView modelAndView = new ModelAndView("item");
        modelAndView.addObject("item", myItemService.findItemById(id));
        return modelAndView;
    }

    @PostMapping
    public ModelAndView createItem(@RequestParam String name,
                                   @RequestParam Integer yearOfRelease) {
        if (StringUtils.isNotEmpty(name) && yearOfRelease != null) {
            MyItem item = new MyItem(null, name, yearOfRelease);
            myItemService.saveItem(item);
        }
        return new ModelAndView("redirect:/admin/items");
    }

    @PostMapping("/{id}")
    public ModelAndView updateItem(@PathVariable Long id,
                                   @RequestParam String name,
                                   @RequestParam Integer yearOfRelease) {
        if (StringUtils.isNotEmpty(name) && yearOfRelease != null) {
            log.info("name = " + name + "years = " + yearOfRelease + "id = " + id);
            MyItem item = new MyItem(id, name, yearOfRelease);
            myItemService.saveItem(item);
        }
        return new ModelAndView("redirect:/admin/items");
    }

    @PostMapping("/{id}/remove")
    public ModelAndView deleteItem(@PathVariable Long id) {
        myItemService.deleteItemById(id);
        return new ModelAndView("redirect:/admin/items");
    }

    @PostMapping("/write")
    public ModelAndView writeToFile() {
        myItemService.writeToFile();
        return new ModelAndView("redirect:/admin/items");
    }
}
