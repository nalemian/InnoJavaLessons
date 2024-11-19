package ru.inno.nalemian.lessons.assignment3;

public class Test {
    public static void main(String[] args) {
        String input = "123a";

        try {
            int number = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println("Error: The input string is not a valid integer.");
        }

        System.out.println("Program continues...");
    }
}

