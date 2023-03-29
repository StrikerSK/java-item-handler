package com.bohemian.app.controller;

import com.bohemian.app.entity.ItemDAO;
import com.bohemian.app.service.IItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/item")
public class ItemController {

    @Autowired
    private IItemService service;

    @GetMapping("/{id}")
    public ItemDAO getItem(@PathVariable Long id) {
        return service.getItem(id);
    }

    @PostMapping
    public Long createItem(@RequestBody ItemDAO item) {
        return service.createItem(item);
    }

}
