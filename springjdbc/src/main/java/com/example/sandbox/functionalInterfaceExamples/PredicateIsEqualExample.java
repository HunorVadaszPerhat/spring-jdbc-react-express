package com.example.sandbox.functionalInterfaceExamples;

import java.util.List;
import java.util.function.Predicate;

public class PredicateIsEqualExample {
    public static void main(String[] args) {
        // List of strings to test
        List<String> names = List.of("Alice", "Bob", "Charlie", "Diana");

        // Predicate to check if a name equals "Alice"
        Predicate<String> isAlice = Predicate.isEqual("Alice");

        // Filter the list using the predicate
        System.out.println("Names matching 'Alice':");
        names.stream()
                .filter(isAlice)
                .forEach(System.out::println); // Output: Alice

        // Predicate to check if a name equals null
        Predicate<String> isNull = Predicate.isEqual(null);

        // Test with null values
        List<String> namesWithNull = List.of("Alice", null, "Charlie");
        System.out.println("\nNames that are null:");
        namesWithNull.stream()
                .filter(isNull)
                .forEach(System.out::println); // Output: null
    }
}
