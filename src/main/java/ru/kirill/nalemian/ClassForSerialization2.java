package ru.kirill.nalemian;

public class ClassForSerialization2 extends ClassForSerialization1 {
    private int field3;

    public ClassForSerialization2(int field1, int field2, int field3) {
        super(field1, field2);
        this.field3 = field3;
    }

    @Override
    public String toString() {
        return "ClassForSerialization2{" +
                "field1=" + getField1() +
                ", field2=" + getField2() +
                ", field3=" + field3 +
                '}';
    }
}
