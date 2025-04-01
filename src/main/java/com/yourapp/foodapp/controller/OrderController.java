package com.yourapp.foodapp.controller;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.yourapp.foodapp.model.Order;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;

@RestController
public class OrderController {

    @PostMapping("/api/order")
    public String placeOrder(@RequestBody Order order) {
        String orderId = UUID.randomUUID().toString();
        order.setId(orderId);

        DatabaseReference ref = FirebaseDatabase.getInstance()
                .getReference("orders")
                .child(orderId);

        ref.setValueAsync(order);
        return orderId;
    }

    @GetMapping("/api/order/{id}")
    public Order getOrderById(@PathVariable String id) throws InterruptedException {
        DatabaseReference ref = FirebaseDatabase.getInstance()
                .getReference("orders")
                .child(id);

        final Order[] result = new Order[1];
        CountDownLatch latch = new CountDownLatch(1);

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                result[0] = snapshot.getValue(Order.class);
                latch.countDown();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                latch.countDown();
            }
        });

        latch.await();

        if (result[0] == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found");
        }

        return result[0];
    }

    @PatchMapping("/api/order/{id}/status")
    public ResponseEntity<?> updateOrderStatus(@PathVariable String id, @RequestBody Map<String, String> body) {
        String newStatus = body.get("status");
        Set<String> allowedStatuses = Set.of("RECEIVED", "PREPARING", "READY", "DELIVERED");

        if (newStatus == null || !allowedStatuses.contains(newStatus.toUpperCase())) {
            return ResponseEntity.badRequest().body("Invalid status: must be one of " + allowedStatuses);
        }

        FirebaseDatabase.getInstance()
                .getReference("orders")
                .child(id)
                .child("status")
                .setValueAsync(newStatus.toUpperCase());

        return ResponseEntity.ok("Status updated to " + newStatus.toUpperCase());
    }
}
