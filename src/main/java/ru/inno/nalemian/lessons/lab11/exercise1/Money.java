package ru.inno.nalemian.lessons.lab11.exercise1;

public enum Money {
    FIVE(5.00),
    TWO(2.00),
    ONE(1.00),
    HALF(0.50);

    private final double denomination;

    Money(double denomination) {
        this.denomination = denomination;
    }

    public double getDenomination() {
        return denomination;
    }
}
