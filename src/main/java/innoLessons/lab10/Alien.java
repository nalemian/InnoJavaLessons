package innoLessons.lab10;

public class Alien extends Creature {
    @Override
    public void bear() {
        this.setAlive(true);
        this.setName("Xenomorph");
        System.out.println("The " + this.getClass().getSimpleName() +
                " " + getName() + " has hatched from an egg. Beware of it!!!");
    }

    @Override
    public void die() {
        if (this.isAlive()) {
            this.setAlive(false);
            System.out.println("The " + this.getClass().getSimpleName() +
                    " " + getName() + " was killed by Sergeant Ripley");
            this.setName(null);
        }
    }
}

