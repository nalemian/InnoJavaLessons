package innoLessons.lab10;

public interface Living {
    default void live() {
        System.out.println(this.getClass().getSimpleName() + " lives");
    }
}
