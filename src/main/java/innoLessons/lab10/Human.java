package innoLessons.lab10;

public final class Human extends Animal {
    @Override
    public void bear() {
        if (!this.isAlive()) {
            this.setAlive(true);
            this.setName("John");
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
