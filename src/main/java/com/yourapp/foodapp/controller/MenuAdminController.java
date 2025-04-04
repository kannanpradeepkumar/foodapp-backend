package com.yourapp.foodapp.controller;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.yourapp.foodapp.model.MenuItem;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/menu")
public class MenuAdminController {

    @PostMapping("/{category}")
    public String addMenuItem(@PathVariable String category, @RequestBody MenuItem item) {
        DatabaseReference ref = FirebaseDatabase.getInstance()
                .getReference("menu")
                .child(category)
                .push(); // auto-generates a key

        ref.setValueAsync(item);
        return "Menu item added with ID: " + ref.getKey();
    }

    @PutMapping("/{category}/{itemId}")
    public String updateMenuItem(@PathVariable String category,
                                 @PathVariable String itemId,
                                 @RequestBody MenuItem item) {
        DatabaseReference ref = FirebaseDatabase.getInstance()
                .getReference("menu")
                .child(category)
                .child(itemId);

        ref.setValueAsync(item);
        return "Menu item updated";
    }

    @DeleteMapping("/{category}/{itemId}")
    public String deleteMenuItem(@PathVariable String category,
                                 @PathVariable String itemId) {
        DatabaseReference ref = FirebaseDatabase.getInstance()
                .getReference("menu")
                .child(category)
                .child(itemId);

        ref.removeValueAsync();
        return "Menu item deleted";
    }

    @PatchMapping("/{category}/{itemId}/availability")
    public String toggleAvailability(@PathVariable String category,
                                     @PathVariable String itemId,
                                     @RequestBody Map<String, Boolean> payload) {
        Boolean available = payload.get("available");
        if (available == null) return "Missing 'available' value";

        FirebaseDatabase.getInstance()
                .getReference("menu")
                .child(category)
                .child(itemId)
                .child("available")
                .setValueAsync(available);

        return "Menu item availability updated to: " + available;
    }
}
