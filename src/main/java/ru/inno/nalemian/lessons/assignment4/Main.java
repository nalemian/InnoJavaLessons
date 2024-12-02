package ru.inno.nalemian.lessons.assignment4;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Main {
    private static int days;
    private static float grassAmount;

    public static void main(String[] args) {
        try {
            List<Animal> animals = readAnimals();
            removeDeadAnimals(animals);
            runSimulation(days, grassAmount, animals);
            //animals.forEach(System.out::println);
            printAnimals(animals);
            //animals.forEach(Animal::makeSound);
        } catch (NumberFormatException e) {
            System.out.println("Invalid inputs");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    private static List<Animal> readAnimals() throws Exception {
        try {
            List<Animal> animals = new ArrayList<>();
            BufferedReader reader = new BufferedReader(new FileReader("input.txt"));
            String testLine = reader.readLine();
            if (testLine == null || testLine.split("\\s+").length != 1) {
                throw new InvalidInputsException();
            }
            try {
                days = Integer.parseInt(testLine.trim());
            } catch (Exception e) {
                throw new InvalidInputsException();
            }
            testLine = reader.readLine();
            if (testLine == null || testLine.split("\\s+").length != 1) {
                throw new InvalidInputsException();
            }
            grassAmount = Float.parseFloat(testLine.trim());
            testLine = reader.readLine();
            if (testLine == null || testLine.split("\\s+").length != 1) {
                throw new InvalidInputsException();
            }
            int numAnimals = Integer.parseInt(testLine.trim());
            if (days < 1 || days > 30) {
                throw new InvalidInputsException();
            }
            if (grassAmount > 100 || grassAmount < 0) {
                throw new GrassOutOfBoundsException();
            }
            if (numAnimals < 1 || numAnimals > 20) {
                throw new InvalidInputsException();
            }
            for (int i = 0; i < numAnimals; i++) {
                testLine = reader.readLine();
                if (testLine == null) {
                    throw new InvalidInputsException();
                }
                String[] animalInfo = testLine.split("\\s+");
                if (animalInfo.length != 4) {
                    throw new InvalidNumberOfAnimalParametersException();
                }
                float weight = Float.parseFloat(animalInfo[1]);
                if (weight > 200 || weight < 5) {
                    throw new WeightOutOfBoundsException();
                }
                float speed = Float.parseFloat(animalInfo[2]);
                if (speed > 60 || speed < 5) {
                    throw new SpeedOutOfBoundsException();
                }
                float energy = Float.parseFloat(animalInfo[3]);
                if (energy > 100 || energy < 0) {
                    throw new EnergyOutOfBoundsException();
                }
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
            }
            if (reader.readLine() != null) {
                throw new InvalidInputsException();
            }
            return animals;
        } catch (FileNotFoundException e) {
            throw new InvalidInputsException();
        }
    }

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

    private static void printAnimals(List<Animal> animals) {
        animals.forEach(Animal::makeSound);
    }

    private static void removeDeadAnimals(List<Animal> animals) {
        animals.removeIf(animal -> animal.getEnergy() <= 0);
    }
}

enum AnimalSound {
    LION("Roar"),
    ZEBRA("Ihoho"),
    BOAR("Oink");
    private final String sound;

    AnimalSound(String sound) {
        this.sound = sound;
    }

    public String getSound() {
        return sound;
    }
}

class Field {
    private float grassAmount;

    public Field(float grassAmount) {
        if (grassAmount < 0 || grassAmount > 100) {
            throw new GrassOutOfBoundsException();
        }
        this.grassAmount = grassAmount;
    }

    public void makeGrassGrow() {
        float doubledGrassAmount = grassAmount * 2;
        if (doubledGrassAmount > 100) {
            grassAmount = 100;
        } else {
            grassAmount = doubledGrassAmount;
        }
    }

    float getGrassAmount() {
        return grassAmount;
    }

    void setGrassAmount(float grassAmount) {
        this.grassAmount = grassAmount;
    }
}

class Boar extends Animal implements Omnivore {

    public Boar(float weight, float speed, float energy) {
        super(weight, speed, energy);
    }

    @Override
    public void eat(List<Animal> animals, Field field) {
        grazeInTheField(this, field);
        try {
            //System.out.println(animals);
            Animal prey = findPrey(animals, this);
            //System.out.println(prey);
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

    public Animal findPrey(List<Animal> animals, Object hunter) {
        Animal hunterAnimal = (Animal) hunter;
        int index = animals.indexOf(hunterAnimal);
        //System.out.println(hunterAnimal);
        //System.out.println(index);
        if (animals.size() == 1) {
            throw new SelfHuntingException();
        }
        int preyIndex = 0;
        if (index + 1 != animals.size()) {
            preyIndex = index + 1;
        }
        Animal prey = animals.get(preyIndex);
        //System.out.println(prey);
        if (hunterAnimal.getClass().equals(prey.getClass())) {
            throw new CannibalismException();
        }
        //System.out.println(prey.getSpeed());
//        System.out.println(hunterAnimal.getSpeed());
//        System.out.println(prey.getEnergy());
//        System.out.println(hunterAnimal.getEnergy());
        if (prey.getSpeed() >= hunterAnimal.getSpeed() && prey.getEnergy() >= hunterAnimal.getEnergy()) {
            throw new TooStrongPreyException();
        }
        return prey;
    }

    @Override
    public Animal choosePrey(List list, Object hunter) {
        return null;
    }

    @Override
    public void huntPrey(Animal hunter, Animal prey) {
        float hunterEnergy = hunter.getEnergy();
        float updatedHunterEnergy = hunterEnergy + prey.getWeight();
        if (updatedHunterEnergy > 100) {
            hunter.setEnergy(100);
        } else {
            hunter.setEnergy(updatedHunterEnergy);
        }
        prey.setEnergy(0);
    }

    @Override
    public void grazeInTheField(Animal grazer, Field field) {
        float grassNeeded = grazer.getWeight() * 0.1f;
        float grassAmount = field.getGrassAmount();
        if (grassAmount >= grassNeeded) {
            field.setGrassAmount(grassAmount - grassNeeded);
            grazer.setEnergy(Math.min(grazer.getEnergy() + grassNeeded, 100));
        }
    }
}

class CannibalismException extends RuntimeException {
    public String getMessage() {
        return "Cannibalism is not allowed";
    }
}

interface Carnivore<T> {
    Animal choosePrey(List<Animal> animals, T hunter);

    void huntPrey(Animal hunter, Animal prey);
}

class EnergyOutOfBoundsException extends RuntimeException {
    public String getMessage() {
        return "The energy is out of bounds";
    }
}

class GrassOutOfBoundsException extends RuntimeException {
    public String getMessage() {
        return "The grass is out of bounds";
    }
}

interface Herbivore {
    void grazeInTheField(Animal grazer, Field field);
}

class InvalidInputsException extends IOException {
    public String getMessage() {
        return "Invalid inputs";
    }
}

class InvalidNumberOfAnimalParametersException extends IOException {
    public String getMessage() {
        return "Invalid number of animal parameters";
    }
}

class Lion extends Animal implements Carnivore {
    public Lion(float weight, float speed, float energy) {
        super(weight, speed, energy);
    }

    @Override
    public Animal choosePrey(List list, Object hunter) {
        return null;
    }

    public Animal findPrey(List<Animal> animals, Object hunter) {
        Animal hunterAnimal = (Animal) hunter;
        int index = animals.indexOf(hunterAnimal);
        //System.out.println(hunterAnimal);
        //System.out.println(index);
        if (animals.size() == 1) {
            throw new SelfHuntingException();
        }
        int preyIndex = 0;
        if (index + 1 != animals.size()) {
            preyIndex = index + 1;
        }
        Animal prey = animals.get(preyIndex);
        //System.out.println(prey);
        if (hunterAnimal.getClass().equals(prey.getClass())) {
            throw new CannibalismException();
        }
//        System.out.println(prey.getSpeed());
//        System.out.println(hunterAnimal.getSpeed());
//        System.out.println(prey.getEnergy());
//        System.out.println(hunterAnimal.getEnergy());
        if (prey.getSpeed() >= hunterAnimal.getSpeed() && prey.getEnergy() >= hunterAnimal.getEnergy()) {
            throw new TooStrongPreyException();
        }
        return prey;
    }

    @Override
    public void huntPrey(Animal hunter, Animal prey) {
        float hunterEnergy = hunter.getEnergy();
        float updatedHunterEnergy = hunterEnergy + prey.getWeight();
        if (updatedHunterEnergy > 100) {
            hunter.setEnergy(100);
        } else {
            hunter.setEnergy(updatedHunterEnergy);
        }
        prey.setEnergy(0);
        //System.out.println("preyEnergy: " + prey.getEnergy());
    }

    @Override
    public void eat(List<Animal> animals, Field field) {
        try {
            //System.out.println(animals);
            Animal prey = findPrey(animals, this);
            //System.out.println(prey);
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

interface Omnivore extends Carnivore, Herbivore {
}

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

    @Override
    public void grazeInTheField(Animal grazer, Field field) {
        float grassNeeded = grazer.getWeight() * 0.1f;
        float grassAmount = field.getGrassAmount();
        if (grassAmount >= grassNeeded) {
            field.setGrassAmount(grassAmount - grassNeeded);
            grazer.setEnergy(Math.min(grazer.getEnergy() + grassNeeded, 100));
        }
    }
}

class WeightOutOfBoundsException extends RuntimeException {
    public String getMessage() {
        return "The weight is out of bounds";
    }
}

class SpeedOutOfBoundsException extends RuntimeException {
    public String getMessage() {
        return "The speed is out of bounds";
    }
}

class TooStrongPreyException extends RuntimeException {
    public String getMessage() {
        return "The prey is too strong or too fast to attack";
    }
}

class SelfHuntingException extends RuntimeException {
    public String getMessage() {
        return "Self-hunting is not allowed";
    }
}

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

    protected Animal(float weight, float speed, float energy) {
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

    public void makeSound() {
    }

    public void decrementEnergy() {
        if (energy > 0) {
            energy -= 1;
        }
    }

    public abstract void eat(List<Animal> animals, Field field);

    float getEnergy() {
        return energy;
    }

    float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public void setEnergy(float energy) {
        this.energy = energy;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }
}
