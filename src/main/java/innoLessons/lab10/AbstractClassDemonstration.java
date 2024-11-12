package innoLessons.lab10;

public class AbstractClassDemonstration {
    public static void main(String[] args) {
        Creature[] creatures = addCreatures();
        runCreatureMethods(creatures);
    }

    private static void runCreatureMethods(Creature[] creatures) {
        for (Creature creature : creatures) {
            creature.bear();
            creature.shoutName();
            creature.die();
            System.out.println();
        }
    }

    private static Creature[] addCreatures() {
        Creature[] creatures = new Creature[4];

        Creature human = new Human();
        creatures[0] = human;

        Creature animal = new Animal();
        creatures[1] = animal;

        Creature dog = new Dog();
        creatures[2] = dog;

        Creature alien = new Alien();
        creatures[3] = alien;

        return creatures;
    }
}