package ru.kirill.nalemian;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class StreamsExamples {
    @Test
    void testSysOut() {
        List<Integer> numbers = new ArrayList<>();
        for (int i = 1; i < 11; i++) {
            numbers.add(i);
        }
        for (Integer number : numbers) {
            System.out.println(number);
        }
        System.out.println("==");
        numbers.stream()
                .forEach(System.out::println);
        System.out.println("==");
        numbers.stream()
                .map(IntegerUtils::test)
                .forEach(System.out::println);
        System.out.println("==");
        var list = numbers.stream()
                .map(integer -> {
                    var result = IntegerUtils.test(integer);
                    result += " test";
                    return result;
                })
                .toList();
        list.forEach(System.out::println);
        //.forEach(System.out::println);
    }
}
