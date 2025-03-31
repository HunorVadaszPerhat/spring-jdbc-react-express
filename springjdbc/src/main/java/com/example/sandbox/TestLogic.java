package com.example.sandbox;

public class TestLogic {
    public static void main(String[] args) {
        // Write any small logic or testing code here
        System.out.println("Testing small logic!");

        // For example, testing a simple math operation
        //int result = addNumbers(5, 3);
        //System.out.println("Result of addition: " + result);

        //String result = reverseWords("Hello World");
        //System.out.println("Result of addition: " + result);

        String[] words = {"flower", "flow", "flight"};
        String result = longestCommonPrefix(words);
        System.out.println("Longest Common Prefix: " + result);
    }

    public static int addNumbers(int a, int b) {
        return a + b;
    }

    public static String reverseWords(String sentence) {
        String[] words = sentence.split(" ");
        StringBuilder reversedSentence = new StringBuilder();

        for (String word : words) {
            StringBuilder reversedWord = new StringBuilder(word);
            reversedSentence.append(reversedWord.reverse().toString()).append(" ");
        }

        return reversedSentence.toString().trim();
    }

    public static String longestCommonPrefix(String[] strings) {
        if (strings == null || strings.length == 0) return "";
        String prefix = strings[0];

        for (int i = 1; i < strings.length; i++) {
            while (strings[i].indexOf(prefix) != 0) {
                prefix = prefix.substring(0, prefix.length() - 1);
                if (prefix.isEmpty()) return "";
            }
        }

        return prefix;
    }
}
