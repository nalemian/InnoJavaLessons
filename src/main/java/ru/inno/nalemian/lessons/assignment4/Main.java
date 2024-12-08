package ru.inno.nalemian.lessons.assignment4;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Main class that simulates animal behavior for a certain number of days
 */
public class Main {
    private static int days;
    private static float grassAmount;
    private static final int MAX_DAYS = 30;
    private static final int MAX_GRASS_AMOUNT = 100;
    private static final int MAX_ANIMALS_NUMBER = 20;
    private static final int ANIMAL_INFO_LENGTH = 4;

    /**
     * Reads animal data and simulation parameters from the "input.txt" file,
     * validates inputs and constructs the list of animals
     *
     * @return a list of animals read from the input file
     * @throws InvalidInputsException                   if the inputs are invalid
     * @throws InvalidNumberOfAnimalParametersException if the number of animal parameters is incorrect
     * @throws Exception                                if an unexpected error occurs
     */
    private static List<Animal> readAnimals() throws InvalidInputsException,
            InvalidNumberOfAnimalParametersException, Exception {
        try {
            List<Animal> animals = new ArrayList<>();
            BufferedReader reader = new BufferedReader(new FileReader("input.txt"));
            String testLine;
            int numAnimals = 0;
            final int numOfInputFields = 3;
            // Read simulation parameters (days, grassAmount, number of animals)
            for (int i = 0; i < numOfInputFields; i++) {
                testLine = reader.readLine();
                if (testLine == null || testLine.split("\\s+").length != 1) {
                    throw new InvalidInputsException();
                }
                if (i == 0) {
                    days = Integer.parseInt(testLine.trim());
                }
                if (i == 1) {
                    grassAmount = Float.parseFloat(testLine.trim());
                }
                if (i == 2) {
                    numAnimals = Integer.parseInt(testLine.trim());
                }
            }
            // Validate simulation parameters
            if (days < 1 || days > MAX_DAYS) {
                throw new InvalidInputsException();
            }
            if (grassAmount > MAX_GRASS_AMOUNT || grassAmount < 0) {
                throw new GrassOutOfBoundsException();
            }
            if (numAnimals < 1 || numAnimals > MAX_ANIMALS_NUMBER) {
                throw new InvalidInputsException();
            }
            // Read and validate input lines with animal properties
            for (int i = 0; i < numAnimals; i++) {
                testLine = reader.readLine();
                if (testLine == null) {
                    throw new InvalidInputsException();
                }
                String[] animalInfo = testLine.split("\\s+");
                if (animalInfo.length != testLine.split(" ").length) {
                    throw new InvalidInputsException();
                }
                if (animalInfo.length != ANIMAL_INFO_LENGTH) {
                    throw new InvalidNumberOfAnimalParametersException();
                }
                // Extract animal properties
                final int energyIndex = 3;
                float weight = Float.parseFloat(animalInfo[1]);
                float speed = Float.parseFloat(animalInfo[2]);
                float energy = Float.parseFloat(animalInfo[energyIndex]);
                switch (animalInfo[0]) {
                    case "Lion":
                        animals.add(new Lion(weight, speed, energy));
                        break;
                    case "Zebra":
                        animals.add(new Zebra(weight, speed, energy));
                        break;
                    case "Boar":
                        animals.add(new Boar(weight, speed, energy));
                        break;
                    default:
                        throw new InvalidInputsException();
                }
                // Validate animal properties
                if (weight > Animal.MAX_WEIGHT || weight < Animal.MIN_WEIGHT) {
                    throw new WeightOutOfBoundsException();
                }
                if (speed > Animal.MAX_SPEED || speed < Animal.MIN_SPEED) {
                    throw new SpeedOutOfBoundsException();
                }
                if (energy > Animal.MAX_ENERGY || energy < Animal.MIN_ENERGY) {
                    throw new EnergyOutOfBoundsException();
                }
            }
            // Check that there are no extra lines in the input
            if (reader.readLine() != null) {
                throw new InvalidInputsException();
            }
            return animals;
        } catch (FileNotFoundException e) {
            throw new InvalidInputsException();
        }
    }

    /**
     * Runs the simulation of animals interacting in a field
     *
     * @param days        the number of simulation days
     * @param grassAmount the initial amount of grass in the field
     * @param animals     the list of animals to simulate
     */
    private static void runSimulation(int days, float grassAmount, List<Animal> animals) {
        Field field = new Field(grassAmount);
        for (int day = 0; day < days; day++) {
            for (Animal animal : new ArrayList<>(animals)) {
                if (animal.getEnergy() != 0.0) {
                    animal.eat(animals, field);
                }
            }
            animals.forEach(Animal::decrementEnergy);
            removeDeadAnimals(animals);
            field.makeGrassGrow();
        }
    }

    /**
     * Prints the sounds made by all remaining animals
     *
     * @param animals the list of animals to print
     */
    private static void printAnimals(List<Animal> animals) {
        animals.forEach(Animal::makeSound);
    }

    /**
     * Removes animals with zero or negative energy from the simulation
     *
     * @param animals the list of animals to filter
     */
    private static void removeDeadAnimals(List<Animal> animals) {
        animals.removeIf(animal -> animal.getEnergy() <= 0);
    }

    /**
     * Main method that organizes the simulation
     */
    public static void main(String[] args) {
        try {
            List<Animal> animals = readAnimals();
            removeDeadAnimals(animals);
            runSimulation(days, grassAmount, animals);
            printAnimals(animals);
        } catch (NumberFormatException e) {
            System.out.println("Invalid inputs");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}

/**
 * Exception thrown when an animal attempts cannibalism
 */
class CannibalismException extends RuntimeException {
    public String getMessage() {
        return "Cannibalism is not allowed";
    }
}

/**
 * Exception thrown when an animal's energy is out of bounds
 */
class EnergyOutOfBoundsException extends RuntimeException {
    public String getMessage() {
        return "The energy is out of bounds";
    }
}

/**
 * Exception thrown when the grass amount is out of bounds
 */
class GrassOutOfBoundsException extends RuntimeException {
    public String getMessage() {
        return "The grass is out of bounds";
    }
}

/**
 * Exception thrown if the inputs are invalid
 */
class InvalidInputsException extends IOException {
    public String getMessage() {
        return "Invalid inputs";
    }
}

/**
 * Exception thrown if the number of animal parameters is incorrect
 */
class InvalidNumberOfAnimalParametersException extends IOException {
    public String getMessage() {
        return "Invalid number of animal parameters";
    }
}

/**
 * Exception thrown when an animal's weight is out of bounds
 */
class WeightOutOfBoundsException extends RuntimeException {
    public String getMessage() {
        return "The weight is out of bounds";
    }
}

/**
 * Exception thrown when an animal's speed is out of bounds
 */
class SpeedOutOfBoundsException extends RuntimeException {
    public String getMessage() {
        return "The speed is out of bounds";
    }
}

/**
 * Exception thrown when a prey is too strong or fast for the hunter
 */
class TooStrongPreyException extends RuntimeException {
    public String getMessage() {
        return "The prey is too strong or too fast to attack";
    }
}

/**
 * Exception thrown when an animal attempts to hunt itself
 */
class SelfHuntingException extends RuntimeException {
    public String getMessage() {
        return "Self-hunting is not allowed";
    }
}

/**
 * Abstract class representing an animal in the simulation
 */
abstract class Animal {
    public static final float MIN_SPEED = 5;
    public static final float MAX_SPEED = 60;
    public static final float MIN_ENERGY = 0;
    public static final float MAX_ENERGY = 100;
    public static final float MIN_WEIGHT = 5;
    public static final float MAX_WEIGHT = 200;
    private float weight;
    private float speed;
    private float energy;

    /**
     * Constructs an Animal with the specified attributes
     *
     * @param weight the animal's weight
     * @param speed  the animal's speed
     * @param energy the animal's energy
     * @throws SpeedOutOfBoundsException  if speed is out of valid range
     * @throws EnergyOutOfBoundsException if energy is out of valid range
     * @throws WeightOutOfBoundsException if weight is out of valid range
     */
    protected Animal(float weight, float speed, float energy) throws SpeedOutOfBoundsException,
            EnergyOutOfBoundsException, WeightOutOfBoundsException {
        if (speed < MIN_SPEED || speed > MAX_SPEED) {
            throw new SpeedOutOfBoundsException();
        }
        if (energy < MIN_ENERGY || energy > MAX_ENERGY) {
            throw new EnergyOutOfBoundsException();
        }
        if (weight < MIN_WEIGHT || weight > MAX_WEIGHT) {
            throw new WeightOutOfBoundsException();
        }
        this.weight = weight;
        this.speed = speed;
        this.energy = energy;
    }

    /**
     * Makes the animal make its characteristic sound
     */
    public void makeSound() {
    }

    /**
     * Reduces the animal's energy by 1 unit
     */
    public void decrementEnergy() {
        if (energy > 0) {
            energy -= 1;
        }
    }

    /**
     * Defines the eating behavior of the animal
     *
     * @param animals the list of all animals
     * @param field   the field where the animal grazes or hunts
     */
    public abstract void eat(List<Animal> animals, Field field);

    /**
     * Gets the current energy of the animal
     *
     * @return the energy
     */
    float getEnergy() {
        return energy;
    }

    /**
     * Gets the weight of the animal
     *
     * @return the weight
     */
    float getWeight() {
        return weight;
    }

    /**
     * Sets the energy of the animal
     *
     * @param energy the new energy
     */
    public void setEnergy(float energy) {
        this.energy = energy;
    }

    /**
     * Gets the speed of the animal
     *
     * @return the speed
     */
    public float getSpeed() {
        return speed;
    }
}

/**
 * Interface for animals that hunt other animals
 *
 * @param <T> the type of hunter
 */
interface Carnivore<T> {
    /**
     * Chooses a prey for the hunter
     *
     * @param animals the list of animals
     * @param hunter  the hunter
     * @return the chosen prey
     * @throws SelfHuntingException   if the hunter tries to hunt itself
     * @throws TooStrongPreyException if the prey is too strong or fast
     * @throws CannibalismException   if the prey belongs to the same species as the hunter
     */
    Animal choosePrey(List<Animal> animals, T hunter) throws SelfHuntingException,
            TooStrongPreyException, CannibalismException;

    /**
     * Executes the hunting of the prey
     *
     * @param hunter the hunter
     * @param prey   the prey
     */
    void huntPrey(Animal hunter, Animal prey);
}

/**
 * Interface for animals that graze on grass
 */
interface Herbivore {
    public final float GRASS_MULTIPLIER = 0.1f;

    /**
     * Allows the animal to graze in the field
     *
     * @param grazer the grazing animal
     * @param field  the field where grazing occurs
     */
    void grazeInTheField(Animal grazer, Field field);
}

/**
 * Interface for animals that can both hunt and graze
 *
 * @param <T> the type of omnivore
 */
interface Omnivore<T> extends Carnivore<T>, Herbivore {
}

/**
 * Enum representing the sounds made by different animals
 */
enum AnimalSound {
    LION("Roar"),
    ZEBRA("Ihoho"),
    BOAR("Oink");
    private final String sound;

    AnimalSound(String sound) {
        this.sound = sound;
    }

    /**
     * Gets the sound of the animal
     *
     * @return the sound
     */
    public String getSound() {
        return sound;
    }
}

/**
 * Represents the field where animals graze and interact
 */
class Field {
    private float grassAmount;
    private static final int MAX_GRASS_AMOUNT = 100;

    /**
     * Constructs a field with a specified amount of grass
     *
     * @param grassAmount the initial grass amount
     * @throws GrassOutOfBoundsException if grassAmount is not within valid range
     */
    public Field(float grassAmount) throws GrassOutOfBoundsException {
        if (grassAmount < 0 || grassAmount > MAX_GRASS_AMOUNT) {
            throw new GrassOutOfBoundsException();
        }
        this.grassAmount = grassAmount;
    }

    /**
     * Doubles the grass amount in the field to the maximum allowed level
     */
    public void makeGrassGrow() {
        float doubledGrassAmount = grassAmount * 2;
        if (doubledGrassAmount > MAX_GRASS_AMOUNT) {
            grassAmount = MAX_GRASS_AMOUNT;
        } else {
            grassAmount = doubledGrassAmount;
        }
    }

    /**
     * Gets the current amount of grass
     *
     * @return the current grass amount
     */
    float getGrassAmount() {
        return grassAmount;
    }

    /**
     * Sets the grass amount in the field
     *
     * @param grassAmount the new grass amount
     */
    void setGrassAmount(float grassAmount) {
        this.grassAmount = grassAmount;
    }
}

/**
 * Represents a Boar
 */
class Boar extends Animal implements Omnivore<Boar> {
    private static final int MAX_HUNTER_ENERGY = 100;

    public Boar(float weight, float speed, float energy) {
        super(weight, speed, energy);
    }

    /**
     * Allows the boar to graze and hunt for food
     */
    @Override
    public void eat(List<Animal> animals, Field field) {
        grazeInTheField(this, field);
        try {
            Animal prey = choosePrey(animals, this);
            if (prey != null) {
                huntPrey(this, prey);
            }
        } catch (SelfHuntingException | CannibalismException | TooStrongPreyException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void makeSound() {
        System.out.println(AnimalSound.BOAR.getSound());
    }

    /**
     * Allows the boar to choose its prey
     */
    @Override
    public Animal choosePrey(List<Animal> animals, Boar hunter) {
        int index = animals.indexOf(hunter);
        if (animals.size() == 1) {
            throw new SelfHuntingException();
        }
        int preyIndex = 0;
        if (index + 1 != animals.size()) {
            preyIndex = index + 1;
        }
        Animal prey = animals.get(preyIndex);
        if (hunter.getClass().equals(prey.getClass())) {
            throw new CannibalismException();
        }
        if (prey.getSpeed() >= hunter.getSpeed() && prey.getEnergy() >= hunter.getEnergy()) {
            throw new TooStrongPreyException();
        }
        return prey;
    }

    /**
     * Allows the boar to hunt its prey
     */
    @Override
    public void huntPrey(Animal hunter, Animal prey) {
        float hunterEnergy = hunter.getEnergy();
        float updatedHunterEnergy = hunterEnergy + prey.getWeight();
        if (updatedHunterEnergy > MAX_HUNTER_ENERGY) {
            hunter.setEnergy(MAX_HUNTER_ENERGY);
        } else {
            hunter.setEnergy(updatedHunterEnergy);
        }
        prey.setEnergy(0);
    }

    /**
     * Allows the boar to graze in the field
     */
    @Override
    public void grazeInTheField(Animal grazer, Field field) {
        float grassNeeded = grazer.getWeight() * Herbivore.GRASS_MULTIPLIER;
        float grassAmount = field.getGrassAmount();
        if (grassAmount >= grassNeeded) {
            field.setGrassAmount(grassAmount - grassNeeded);
            grazer.setEnergy(Math.min(grazer.getEnergy() + grassNeeded, MAX_HUNTER_ENERGY));
        }
    }
}

/**
 * Represents a Lion
 */
class Lion extends Animal implements Carnivore<Lion> {
    public Lion(float weight, float speed, float energy) {
        super(weight, speed, energy);
    }

    /**
     * Allows the lion to choose its prey
     */
    @Override
    public Animal choosePrey(List<Animal> animals, Lion hunter) {
        int index = animals.indexOf(hunter);
        if (animals.size() == 1) {
            throw new SelfHuntingException();
        }
        int preyIndex = 0;
        if (index + 1 != animals.size()) {
            preyIndex = index + 1;
        }
        Animal prey = animals.get(preyIndex);
        if (hunter.getClass().equals(prey.getClass())) {
            throw new CannibalismException();
        }
        if (prey.getSpeed() >= hunter.getSpeed() && prey.getEnergy() >= hunter.getEnergy()) {
            throw new TooStrongPreyException();
        }
        return prey;
    }

    /**
     * Executes the lion's hunting behavior
     */
    @Override
    public void huntPrey(Animal hunter, Animal prey) throws SelfHuntingException,
            TooStrongPreyException, CannibalismException {
        float hunterEnergy = hunter.getEnergy();
        float updatedHunterEnergy = hunterEnergy + prey.getWeight();
        hunter.setEnergy(Math.min(updatedHunterEnergy, Animal.MAX_ENERGY));
        prey.setEnergy(0);
    }

    @Override
    public void eat(List<Animal> animals, Field field) {
        try {
            Animal prey = choosePrey(animals, this);
            if (prey != null) {
                huntPrey(this, prey);
            }
        } catch (SelfHuntingException | CannibalismException | TooStrongPreyException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void makeSound() {
        System.out.println("Roar");
    }
}

/**
 * Represents a Zebra
 */
class Zebra extends Animal implements Herbivore {
    public Zebra(float weight, float speed, float energy) {
        super(weight, speed, energy);
    }

    @Override
    public void makeSound() {
        System.out.println("Ihoho");
    }

    @Override
    public void eat(List<Animal> animals, Field field) {
        grazeInTheField(this, field);
    }

    /**
     * Allows the zebra to graze in the field
     */
    @Override
    public void grazeInTheField(Animal grazer, Field field) {
        float grassNeeded = grazer.getWeight() * Herbivore.GRASS_MULTIPLIER;
        float grassAmount = field.getGrassAmount();
        if (grassAmount >= grassNeeded) {
            field.setGrassAmount(grassAmount - grassNeeded);
            grazer.setEnergy(Math.min(grazer.getEnergy() + grassNeeded, Animal.MAX_ENERGY));
        }
    }
}
