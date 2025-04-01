package com.yourapp.foodapp;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;

@SpringBootApplication
public class FoodappApplication {

    @PostConstruct
    public void initFirebase() {
        try {
            FileInputStream serviceAccount = new FileInputStream("src/main/resources/firebase-service-account.json");
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setDatabaseUrl("https://foodapp-6ce86-default-rtdb.asia-southeast1.firebasedatabase.app/")
                    .build();
            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
            }
        } catch (Exception e) {
            System.err.println("Firebase init error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(FoodappApplication.class, args);
    }
}
