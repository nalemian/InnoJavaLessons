package ru.kirill.nalemian;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;

class ReflectionWithConstructorsTest {
    @Test
    void printConstructorsParams() {
        ReflectionWithConstructors object = new ReflectionWithConstructors(1);
        Class<?> cls = object.getClass();
        try {
            Constructor<?>[] constructors = cls.getDeclaredConstructors();
            for (Constructor<?> constructor : constructors) {
                System.out.println(constructor.getName());
                Parameter[] parameters = constructor.getParameters();
                System.out.println("Number of parameters: " + parameters.length);
                for (Parameter parameter : parameters) {
                    System.out.println("name: " + parameter.getName());
                    System.out.println("type: " + parameter.getType().getName());
                }
                System.out.println();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}