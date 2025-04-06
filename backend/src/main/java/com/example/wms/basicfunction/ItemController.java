package com.example.wms.basicfunction;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RequestMapping("/")
public class ItemController {
    
    @Autowired
    private ItemService itemService;

    @GetMapping("/list")
    public ResponseEntity<List<Item>> getAllItems() {
        List<Item> items = itemService.getAllItems();
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    @GetMapping("/items/{id}")
    public ResponseEntity<Item> getItemById(@PathVariable Long id) {
        Optional<Item> optionalItem = itemService.getItemById(id);

        if (optionalItem.isPresent()) {
            return new ResponseEntity<>(optionalItem.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/add-item")
    public ResponseEntity<String> addItem(@RequestBody Item item) {
        try {
            itemService.createItem(item);
            return new ResponseEntity<>("Item created successfully!", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error creating item: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteItem(@PathVariable Long id) {
        try {
            itemService.deleteItem(id);
            return new ResponseEntity<>("Item deleted successfully!", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error deleting item: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/items/{id}")
    public ResponseEntity<String> updateItem(@PathVariable Long id, @RequestBody Item item) {
        try {
            item.setId(id);
            itemService.updateItem(item);
            return new ResponseEntity<>("Item updated successfully!", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error updating item: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
