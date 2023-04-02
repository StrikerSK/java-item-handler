package com.bohemian.app.controller;

import com.bohemian.app.entity.ItemDAO;
import com.bohemian.app.service.IItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/item")
public class ItemController {

    @Autowired
    private IItemService service;

    @GetMapping("/{id}")
    public ItemDAO getItem(@PathVariable Long id) {
        return service.getItem(id);
    }

    @GetMapping("")
    public List<ItemDAO> findItems(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "5") Integer limit,
            @RequestParam(required = false) List<String> tags
    ) {
        Pageable pageable = PageRequest.of(page, limit);
        return service.findItems(pageable, tags);
    }

    @PostMapping
    public Long createItem(@RequestBody ItemDAO item) {
        return service.createItem(item);
    }

    @PutMapping("/{id}")
    public void updateItem(@PathVariable Long id, @RequestBody ItemDAO item) {
        service.updateItem(id, item);
    }

    @DeleteMapping("/{id}")
    public void updateItem(@PathVariable Long id) {
        service.deleteItem(id);
    }

}
