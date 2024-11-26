package ru.inno.nalemian.lessons.lab14.Task1;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.lang.Math.abs;

public class LambdaExpression {
    public static void main(String[] args) {
        final int size = 10;
        final int numToDivide = 3;
        List<Integer> randomNumbers = putRandomIntegers(new ArrayList<>(size), size);
        randomNumbers.stream()
                .filter(n -> n % numToDivide == 0)
                .forEach(n -> System.out.println(abs(n)));
    }

    public static List<Integer> putRandomIntegers(List<Integer> numbers, int size) {
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            numbers.add(random.nextInt());
        }
        return numbers;
    }

}
