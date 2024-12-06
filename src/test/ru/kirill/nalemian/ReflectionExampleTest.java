package ru.kirill.nalemian;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

class ReflectionExampleTest {
    @Test
    void makeTheFieldPrivate() {
        try {
            Class<?> byteBuddyClass = new ByteBuddy()
                    .subclass(ReflectionExample.class)
                    .defineField("publicField", int.class, Modifier.PRIVATE)
                    .make()
                    .load(ReflectionExample.class.getClassLoader(), ClassLoadingStrategy.Default.WRAPPER)
                    .getLoaded();
            Field field = byteBuddyClass.getDeclaredField("publicField");
            System.out.println(Modifier.isPrivate(field.getModifiers()));
            Object instance = byteBuddyClass.getDeclaredConstructor().newInstance();
            System.out.println(field.getInt(instance));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}