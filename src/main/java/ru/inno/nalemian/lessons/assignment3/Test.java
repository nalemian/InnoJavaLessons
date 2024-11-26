package ru.inno.nalemian.lessons.assignment3;

public class Test {
    public static String main(String[] args) {
        String input = "123";

        try {
            int number = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return "Error: The input string is not a valid integer.";
        }

        return "Program continues...";
    }
}

