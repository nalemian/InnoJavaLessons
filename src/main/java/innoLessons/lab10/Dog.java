package innoLessons.lab10;

public final class Dog extends Animal {

    @Override
    public void bear() {
        if (!this.isAlive()) {
            this.setAlive(true);
            this.setName("Spike");
            System.out.println("The " + this.getClass().getSimpleName() +
                    " " + getName() + " was born");
        }
    }

    @Override
    public void die() {
        if (this.isAlive()) {
            this.setAlive(false);
            System.out.println("The " + this.getClass().getSimpleName() +
                    " " + getName() + " has barked and died");
            this.setName(null);
        }
    }

    public void bark() {
        System.out.println(this.getName() + " is barking");
    }
}