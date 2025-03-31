package com.example.sandbox.functionalInterfaceExamples;

import java.util.ArrayList;
import java.util.List;
import java.util.function.IntPredicate;
import java.util.function.Predicate;

public class Example1 {

    // Method to filter employees based on a predicate
    public static List<Employee> filterEmployees(List<Employee> employees, Predicate<Employee> condition) {
        List<Employee> filteredList = new ArrayList<>();
        for (Employee employee : employees) {
            if (condition.test(employee)) {
                filteredList.add(employee);
            }
        }
        return filteredList;
    }

    // Method to filter products based on a complex predicate
    public static List<Product> filterProducts(List<Product> products, Predicate<Product> condition) {
        List<Product> filteredProducts = new ArrayList<>();
        for (Product product : products) {
            if (condition.test(product)) {
                filteredProducts.add(product);
            }
        }
        return filteredProducts;
    }

    public static void main(String[] args) {
        // predicate takes one argument and returns a boolean - always
        // must use Integer wrapper class - cannot use primitive
        // boxing and unboxing from primitive to wrapper class is done behind the scenes
        Predicate<Integer> predicate = i -> i < 10;
        System.out.println(predicate.test(5));

        // IntPredicate supports primitive int by default
        // meaning no boxing and unboxing is necessary at all
        // it can help performance
        IntPredicate intPredicate = i -> i < 10;
        System.out.println(intPredicate.test(5));


        // Create a list of employees
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee("Alice", 30, 50000));
        employees.add(new Employee("Bob", 45, 70000));
        employees.add(new Employee("Charlie", 25, 40000));
        employees.add(new Employee("Diana", 35, 65000));

        // Filter employees with salary greater than 50,000
        List<Employee> highEarners = filterEmployees(employees, emp -> emp.getSalary() > 50000);
        System.out.println("Employees with salary > 50,000:");
        highEarners.forEach(System.out::println);

        // Create a list of products
        List<Product> products = new ArrayList<>();
        products.add(new Product("Laptop", "Electronics", 1200.50, 50));
        products.add(new Product("Smartphone", "Electronics", 800.99, 200));
        products.add(new Product("TV", "Electronics", 600.00, 30));
        products.add(new Product("Bread", "Groceries", 2.50, 500));
        products.add(new Product("Milk", "Groceries", 1.99, 0));
        products.add(new Product("Shampoo", "Personal Care", 5.99, 150));

        // Predicate to filter products in the "Electronics" category
        Predicate<Product> isElectronics = product -> product.getCategory().equalsIgnoreCase("Electronics");

        // Predicate to filter products with price > 500
        Predicate<Product> isExpensive = product -> product.getPrice() > 500;

        // Predicate to filter products in stock
        Predicate<Product> isInStock = product -> product.getStock() > 0;

        // Combine predicates to find expensive electronics in stock
        Predicate<Product> expensiveElectronicsInStock = isElectronics.and(isExpensive).and(isInStock);

        List<Product> filteredList = filterProducts(products, expensiveElectronicsInStock);
        System.out.println("Expensive electronics in stock:");
        filteredList.forEach(System.out::println);

        // Predicate to find affordable groceries with stock greater than 100
        Predicate<Product> affordableGroceries = product -> product.getCategory().equalsIgnoreCase("Groceries")
                && product.getPrice() < 10 && product.getStock() > 100;

        List<Product> affordableGroceriesList = filterProducts(products, affordableGroceries);
        System.out.println("\nAffordable groceries in stock:");
        affordableGroceriesList.forEach(System.out::println);

        // Predicate to find out-of-stock items
        Predicate<Product> outOfStock = isInStock.negate();

        List<Product> outOfStockProducts = filterProducts(products, outOfStock);
        System.out.println("\nOut-of-stock products:");
        outOfStockProducts.forEach(System.out::println);
    }
}
