package ru.kirill.nalemian;

public class AlgorithmUtils {
    //TODO write another implementation using dynamic approach
    //TODO find the way to prove that dynamic approach is more effective
    public static int getFibonacciNumber(int number) {
        if (number == 0) return 0;
        if (number == 1) return 1;
        return getFibonacciNumber(number - 1) + getFibonacciNumber(number - 2);
    }
}
