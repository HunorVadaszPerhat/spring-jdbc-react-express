package com.example.sandbox.functionalInterfaceExamples;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.Arrays.stream;

@Getter
class MyObject {
    // Getters
    private String name;
    private int value;

    // Constructor
    public MyObject(String name, int value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public String toString() {
        return "MyObject{name='" + name + "', value=" + value + "}";
    }
}

public class Example2 {

    public static void main(String[] args) {
        List<String> countries = Arrays.asList("Germany", "France", "Italy");
        Stream<String> countriesStream = countries.stream();
        countriesStream.forEach(System.out::println);

        int[] numbers = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
        IntStream numbersStream = stream(numbers);
        numbersStream.forEach(System.out::println);

        Random random = new Random();
        IntStream randomIntsStream = IntStream.generate(random::nextInt)
                .limit(10);
        randomIntsStream.forEach(System.out::println);


        IntStream.generate(random::nextInt)
                .limit(10)
                .forEach(System.out::println);

        // Create an ArrayList of MyObject
        ArrayList<MyObject> myObjects = new ArrayList<>();
        myObjects.add(new MyObject("Object1", 10));
        myObjects.add(new MyObject("Object2", 20));
        myObjects.add(new MyObject("Object3", 30));

        // Create a stream from the ArrayList
        Stream<MyObject> stream = myObjects.stream();

        Stream.of(
                    new MyObject("Object1", 10),
                    new MyObject("Object2", 20),
                    new MyObject("Object3", 30)
                ).forEach(System.out::println);

        List.of(
                    new MyObject("Object1", 10),
                    new MyObject("Object2", 20),
                    new MyObject("Object3", 30)
                )
                .stream()
                .forEach(System.out::println);

        List<Integer> numbers1 = Arrays.asList(4, 7, 3, 8, 1, 2);
        numbers1.stream()
                .filter(n -> n > 4)
                .forEach(System.out::println);

        List<String> words = Arrays.asList("Apple", "Banana", "Pear");
        words.stream()
                .map(String::toLowerCase)
                .forEach(System.out::println);

        List<List<String>> productByCategories = Arrays.asList(
                Arrays.asList("washing machine", "Television"),
                Arrays.asList("Laptop", "Camera", "Watch"),
                Arrays.asList("grocery", "essentials")
        );
        System.out.println("productByCategories: " + productByCategories);
    }
}
