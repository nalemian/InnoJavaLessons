package ru.kirill.nalemian;

import java.util.List;

public class ReflectionWithConstructors {
    private int intField;
    private float floatField;
    private String stringField;
    private List<Integer> listField;

    public ReflectionWithConstructors(int intField) {
        this.intField = intField;
    }

    public ReflectionWithConstructors(int intField, float floatField) {
        this.intField = intField;
        this.floatField = floatField;
    }

    public ReflectionWithConstructors(int intField, float floatField, String stringField) {
        this.intField = intField;
        this.floatField = floatField;
        this.stringField = stringField;
    }

    public ReflectionWithConstructors(int intField, float floatField, String stringField, List<Integer> listField) {
        this.intField = intField;
        this.floatField = floatField;
        this.stringField = stringField;
        this.listField = listField;
    }

}
