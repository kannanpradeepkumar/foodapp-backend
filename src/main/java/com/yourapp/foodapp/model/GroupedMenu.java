package com.yourapp.foodapp.model;

import java.util.List;

public class GroupedMenu {
    private String category;
    private List<MenuItem> items;

    public GroupedMenu() {
    }

    public GroupedMenu(String category, List<MenuItem> items) {
        this.category = category;
        this.items = items;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<MenuItem> getItems() {
        return items;
    }

    public void setItems(List<MenuItem> items) {
        this.items = items;
    }
}