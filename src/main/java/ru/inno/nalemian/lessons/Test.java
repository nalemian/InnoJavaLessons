package ru.inno.nalemian.lessons;

public class Test {
    public static void main(String[] args) {
        try {
            Float.parseFloat("bnj");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
