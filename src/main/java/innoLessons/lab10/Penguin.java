package innoLessons.lab10;

public class Penguin implements Swimmable, Living {
    boolean isSwimming = false;

    @Override
    public void swim() {
        if (!isSwimming) {
            System.out.println("The " + this.getClass().getSimpleName() +
                    " is swimming between icebergs");
            isSwimming = true;
        }
    }

    @Override
    public void stopSwimming() {
        if (isSwimming) {
            System.out.println("The " + this.getClass().getSimpleName() +
                    " stopped swimming between icebergs");
            isSwimming = false;
        }
    }
}
