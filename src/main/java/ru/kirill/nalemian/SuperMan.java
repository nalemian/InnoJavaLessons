package ru.kirill.nalemian;

public class SuperMan extends Man {
    private String superPower;

    public SuperMan(String name, String superPower) {
        super(name);
        this.superPower = superPower;
    }

    public String getSuperPower() {
        return superPower;
    }

    public void setSuperPower(String superPower) {
        this.superPower = superPower;
    }
}
