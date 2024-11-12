package ru.inno.nalemian.lessons.lab10;

public class Animal extends Creature {
    @Override
    public void bear() {
        if (!this.isAlive()) {
            this.setAlive(true);
            this.setName("X");
            System.out.println("The " + this.getClass().getSimpleName() +
                    " " + getName() + " was born");
        }
    }

    @Override
    public void die() {
        if (this.isAlive()) {
            this.setAlive(false);
            System.out.println("The " + this.getClass().getSimpleName() +
                    " " + getName() + " has died. RIP");
            this.setName(null);
        }
    }
}