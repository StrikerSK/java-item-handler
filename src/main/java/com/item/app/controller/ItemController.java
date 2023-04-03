package com.item.app.controller;

import com.item.app.entity.ItemDAO;
import com.item.app.utils.SearchParameters;
import com.item.app.service.IItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
            @RequestParam(required = false) Integer lowerValue,
            @RequestParam(required = false) Integer upperValue,
            @RequestParam(required = false) List<String> tags
    ) {
        SearchParameters searchParameters = new SearchParameters(lowerValue, upperValue, limit, page, tags);
        return service.findItems(searchParameters);
    }

    @PostMapping
    public Map<String, Object> createItem(@RequestBody ItemDAO item) {
        return Map.of("id", service.createItem(item));
    }

    @PutMapping("/{id}")
    public void updateItem(@PathVariable Long id, @RequestBody ItemDAO item) {
        service.updateItem(id, item);
    }

    @DeleteMapping("/{id}")
    public void deleteItem(@PathVariable Long id) {
        service.deleteItem(id);
    }

}
