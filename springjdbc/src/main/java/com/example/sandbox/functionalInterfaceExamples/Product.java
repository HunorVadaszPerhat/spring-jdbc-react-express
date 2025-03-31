package com.example.sandbox.functionalInterfaceExamples;

import lombok.Getter;

@Getter
public class Product {
    private String name;
    private String category;
    private double price;
    private int stock;

    public Product(String name, String category, double price, int stock) {
        this.name = name;
        this.category = category;
        this.price = price;
        this.stock = stock;
    }

    @Override
    public String toString() {
        return "Product{name='" + name + "', category='" + category + "', price=" + price + ", stock=" + stock + "}";
    }
}
