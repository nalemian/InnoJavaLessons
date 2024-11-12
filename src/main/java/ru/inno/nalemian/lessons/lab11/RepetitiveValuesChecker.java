package ru.inno.nalemian.lessons.lab11;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class RepetitiveValuesChecker {
    public static void main(String[] args) {
        Map<String, Integer> map = new HashMap<>();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter number of entries: ");
        int n = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < n; i++) {
            System.out.println("Enter key (String): ");
            String key = scanner.nextLine();
            System.out.println("Enter value (Integer): ");
            int value = scanner.nextInt();
            scanner.nextLine();
            map.put(key, value);
        }
        Map<Integer, Integer> valueCounts = new HashMap<>();
        /**
         * If the value is already in valueCounts, getOrDefault will return the existing count.
         * If the value is not in valueCounts, it will return 0 (the default value specified).
         */
        for (Integer value : map.values()) {
            valueCounts.put(value, valueCounts.getOrDefault(value, 0) + 1);
        }
        boolean hasRepetitiveValues = false;
        for (Map.Entry<Integer, Integer> entry : valueCounts.entrySet()) {
            if (entry.getValue() > 1) {
                hasRepetitiveValues = true;
                System.out.println("Value " + entry.getKey() + " appears " + entry.getValue() + " times.");
            }
        }

        if (!hasRepetitiveValues) {
            System.out.println("No repetitive values found.");
        }
        scanner.close();
    }
}

