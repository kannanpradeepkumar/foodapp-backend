package com.yourapp.foodapp.controller;

import com.yourapp.foodapp.model.GroupedMenu;
import com.yourapp.foodapp.model.MenuItem;
import com.google.firebase.database.*;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.concurrent.CountDownLatch;

@RestController
public class MenuController {

    @GetMapping("/api/menu")
    public List<GroupedMenu> getGroupedMenu() throws InterruptedException {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("menu");

        List<GroupedMenu> result = new ArrayList<>();
        CountDownLatch latch = new CountDownLatch(1);

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot categorySnap : snapshot.getChildren()) {
                    List<MenuItem> items = new ArrayList<>();
                    for (DataSnapshot itemSnap : categorySnap.getChildren()) {
                        MenuItem item = itemSnap.getValue(MenuItem.class);
                        item.setId(itemSnap.getKey());
                        items.add(item);
                    }
                    result.add(new GroupedMenu(categorySnap.getKey(), items));
                }
                latch.countDown();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                System.err.println("Firebase error: " + error.getMessage());
                latch.countDown();
            }
        });

        latch.await(); // wait for Firebase response
        return result;
    }
}
