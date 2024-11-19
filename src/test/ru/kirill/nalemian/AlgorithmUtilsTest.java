package ru.kirill.nalemian;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AlgorithmUtilsTest {

    @Test
    void fibonacciNumbers() {
        assertEquals(13, AlgorithmUtils.getFibonacciNumber(7));
    }
}