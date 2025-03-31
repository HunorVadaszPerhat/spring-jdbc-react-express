package com.example.sandbox.functionalInterfaceExamples;

import java.util.Arrays;
import java.util.function.BiFunction;
import java.util.function.Function;

public class MethodReferenceExample {
    public static void main(String[] args) {
        String[] names = { "Alice", "Bob", "Charlie" };

        // Using an instance method reference of an arbitrary object
        //Arrays.sort(names, String::compareToIgnoreCase);
        Arrays.sort(names, (a, b) -> Character.compare(a.charAt(0), b.charAt(0)));

        for (String name : names) {
            System.out.println(name);
            System.out.println(Arrays.toString(name.split(" ")));
            System.out.println(Arrays.toString(names));
        }


        Function<String, Integer> lengthExtractor = String::length; // Method reference to length()
        System.out.println(lengthExtractor.apply("Hello")); // Output: 5

        BiFunction<Integer, Integer, Integer> maxFunction = Math::max; // Method reference to max()
        System.out.println(maxFunction.apply(10, 20)); // Output: 20

        // Output:
        // Alice
        // Bob
        // Charlie
    }
}
