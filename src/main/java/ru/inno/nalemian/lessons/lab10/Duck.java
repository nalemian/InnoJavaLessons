package ru.inno.nalemian.lessons.lab10;

public class Duck implements Swimmable, Flyable, Living {
    boolean isSwimming = false;
    boolean isFlying = false;

    @Override
    public void fly() {
        if (!isFlying) {
            System.out.println("The " + this.getClass().getSimpleName() +
                    " is flying in the air");
            isFlying = true;
        }
    }

    @Override
    public void stopFlying() {
        if (isFlying) {
            System.out.println("The " + this.getClass().getSimpleName() +
                    " stopped flying in the air");
            isFlying = false;
        }
    }

    @Override
    public void swim() {
        if (!isSwimming) {
            System.out.println("The " + this.getClass().getSimpleName() +
                    " is swimming in the river");
            isSwimming = true;
        }
    }

    @Override
    public void stopSwimming() {
        if (isSwimming) {
            System.out.println("The " + this.getClass().getSimpleName() +
                    " stopped swimming in the river");
            isSwimming = false;
        }
    }
}