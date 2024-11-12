package innoLessons.lab11;

import java.util.Scanner;

public class AnimalListManager {
    private String[] animals;
    private int count;

    // Constructor to initialize an empty list with a fixed size
    public AnimalListManager(int size) {
        animals = new String[size];
        count = 0;
    }

    public static void main(String[] args) {
        AnimalListManager manager = new AnimalListManager(10); // Initial list size of 10
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\nAnimal List Manager");
            System.out.println("1. Add Animal");
            System.out.println("2. Remove Animal");
            System.out.println("3. Update Animal");
            System.out.println("4. Display Animals");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");
            choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1 -> manager.addAnimal(scanner);
                case 2 -> manager.removeAnimal(scanner);
                case 3 -> manager.updateAnimal(scanner);
                case 4 -> manager.displayAnimals();
                case 5 -> System.out.println("Exiting program...");
                default -> System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 5);
    }

    // Method to add an animal
    public void addAnimal(Scanner scanner) {
        if (count >= animals.length) {
            System.out.println("Animal list is full. Cannot add more animals.");
            return;
        }

        System.out.print("Enter animal name to add: ");
        String animal = scanner.nextLine();
        animals[count] = animal;
        count++;
        System.out.println(animal + " has been added.");
    }

    // Method to remove an animal
    public void removeAnimal(Scanner scanner) {
        if (count == 0) {
            System.out.println("Animal list is empty. Nothing to remove.");
            return;
        }

        System.out.print("Enter animal name to remove: ");
        String animal = scanner.nextLine();
        int index = findAnimal(animal);

        if (index == -1) {
            System.out.println(animal + " not found in the list.");
        } else {
            for (int i = index; i < count - 1; i++) {
                animals[i] = animals[i + 1];
            }
            animals[count - 1] = null;
            count--;
            System.out.println(animal + " has been removed.");
        }
    }

    // Method to update an animal
    public void updateAnimal(Scanner scanner) {
        if (count == 0) {
            System.out.println("Animal list is empty. Nothing to update.");
            return;
        }

        System.out.print("Enter animal name to update: ");
        String oldAnimal = scanner.nextLine();
        int index = findAnimal(oldAnimal);

        if (index == -1) {
            System.out.println(oldAnimal + " not found in the list.");
        } else {
            System.out.print("Enter new animal name: ");
            String newAnimal = scanner.nextLine();
            animals[index] = newAnimal;
            System.out.println(oldAnimal + " has been updated to " + newAnimal + ".");
        }
    }

    // Method to display all animals
    public void displayAnimals() {
        if (count == 0) {
            System.out.println("Animal list is empty.");
            return;
        }

        System.out.println("Animals in the list:");
        for (int i = 0; i < count; i++) {
            System.out.println((i + 1) + ". " + animals[i]);
        }
    }

    // Helper method to find the index of an animal in the list
    private int findAnimal(String animal) {
        for (int i = 0; i < count; i++) {
            if (animals[i].equalsIgnoreCase(animal)) {
                return i;
            }
        }
        return -1;
    }
}
