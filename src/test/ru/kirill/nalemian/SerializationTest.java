package ru.kirill.nalemian;

import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SerializationTest {
    @Test
    void deserializeWithoutSomeField() {
        try {
            var out = new ObjectOutputStream(new FileOutputStream("storage"));
            var obj1 = new ClassForSerialization1(1, 2);
            out.writeObject(obj1);
            out.close();
            System.out.println(obj1);
            var receive = new ObjectInputStream(new FileInputStream("storage"));
            //var obj2=(DeserializationClass)receive.readObject();
            var obj2 = (ClassForSerialization1) receive.readObject();
            receive.close();
            var obj2Deser = new ClassForSerialization2(obj2.getField1(), obj2.getField2(), 3);
            System.out.println(obj2Deser);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void deserializeWithExtraField() {
        try {
            var out = new ObjectOutputStream(new FileOutputStream("storage"));
            var obj1 = new ClassForSerialization2(1, 2, 3);
            out.writeObject(obj1);
            out.close();
            System.out.println(obj1);
            var receive = new ObjectInputStream(new FileInputStream("storage"));
            var obj2 = (ClassForSerialization1) receive.readObject();
            receive.close();
            System.out.println(obj2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
