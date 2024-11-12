package innoLessons.lab11.exercise1;

import java.util.Scanner;

public class VendingMachine {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the Vending Machine!");
        System.out.println("Please select a drink from the menu:");
        for (Drinks drink : Drinks.values()) {
            System.out.println(drink.getName() + " - $" + drink.getPrice());
        }
        System.out.print("\nEnter the number of the drink you want to buy (1 for Coke Cola, 2 for Sprite, 3 for Fanta): ");
        int choice = scanner.nextInt();
        if (choice < 1 || choice > Drinks.values().length) {
            System.out.println("Invalid choice. Exiting.");
            return;
        }
        Drinks selectedDrink = Drinks.values()[choice - 1];
        System.out.println("You selected " + selectedDrink.getName() + " which costs $" + selectedDrink.getPrice());
        System.out.print("\nInsert money (you can insert $5, $2, $1, or $0.50 bills): ");
        double insertedMoney = 0;
        while (insertedMoney < selectedDrink.getPrice()) {
            insertedMoney += scanner.nextDouble();

            if (insertedMoney < selectedDrink.getPrice()) {
                System.out.println("You need more money. Please insert more.");
            }
        }
        System.out.println("\nPurchase successful! Dispensing " + selectedDrink.getName());
        double change = insertedMoney - selectedDrink.getPrice();
        System.out.println("Your change is: $" + String.format("%.2f", change));
        returnChange(change);
    }

    private static void returnChange(double change) {
        System.out.println("Returning change...");

        for (Money money : Money.values()) {
            while (change >= money.getDenomination()) {
                change -= money.getDenomination();
                System.out.println("Returned: $" + money.getDenomination());
            }
        }
        if (change > 0) {
            System.out.println("Remaining change less than the smallest denomination. Cannot return exact change.");
        }
    }
}