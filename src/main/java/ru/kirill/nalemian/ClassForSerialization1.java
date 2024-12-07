package ru.kirill.nalemian;

import java.io.Serializable;

public class ClassForSerialization1 implements Serializable {
    private int field1;
    private int field2;

    public ClassForSerialization1(int field1, int field2) {
        this.field1 = field1;
        this.field2 = field2;
    }

    @Override
    public String toString() {
        return "ClassForSerialization1{" +
                "field1=" + field1 +
                ", field2=" + field2 +
                '}';
    }

    public int getField1() {
        return field1;
    }

    public int getField2() {
        return field2;
    }
}
