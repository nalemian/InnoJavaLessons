package ru.kirill.nalemian;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NumericValueTest {

    @Test
    void fibonacciNumbers() {
        NumericValue numericValue = new NumericValue();
        assertEquals(13, numericValue.getFibonacciNumber(7));
        int number = 15;
        int fibNumber = 0;
        int prevNum = 1;
        int penultimateNum = 0;
        for (int i = 2; i < 16; i++) {
            int sumOfPrevNumbs = penultimateNum + prevNum;
            fibNumber = sumOfPrevNumbs;
            penultimateNum = prevNum;
            prevNum = sumOfPrevNumbs;
        }
        assertEquals(fibNumber, numericValue.getFibonacciNumber(number));
    }
}