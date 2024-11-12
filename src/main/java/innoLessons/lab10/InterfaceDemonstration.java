package innoLessons.lab10;

public class InterfaceDemonstration {
    public static void main(String[] args) {
        Living[] livings = addLivingObjects();
        letObjectsLive(livings);
    }

    private static Living[] addLivingObjects() {
        Living[] livings = new Living[2];

        Duck duck = new Duck();
        livings[0] = duck;

        Penguin penguin = new Penguin();
        livings[1] = penguin;

        return livings;
    }

    private static void letObjectsLive(Living[] livings) {
        for (Living living : livings) {
            living.live();
        }
    }
}