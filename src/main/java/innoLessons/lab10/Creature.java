package innoLessons.lab10;

public abstract class Creature {
    public String name = null;
    public boolean isAlive = false;

    public abstract void bear();

    public abstract void die();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public void shoutName() {
        if (name != null) {
            System.out.println(name);
        } else System.out.println("Error");
    }

}

