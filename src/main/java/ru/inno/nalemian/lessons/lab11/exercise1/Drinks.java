package ru.inno.nalemian.lessons.lab11.exercise1;

public enum Drinks {
    COKE_COLA("Coke Cola", 2.50),
    SPRITE("Sprite", 2.00),
    FANTA("Fanta", 1.80);

    private final String name;
    private final double price;

    Drinks(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }
}