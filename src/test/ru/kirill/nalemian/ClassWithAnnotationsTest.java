package ru.kirill.nalemian;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

class ClassWithAnnotationsTest {
    @Test
    void printAnnotationsAndValues() {
        Class<ClassWithAnnotations> cls = ClassWithAnnotations.class;
        for (Field field : cls.getDeclaredFields()) {
            CustomAnnotation annotation = field.getAnnotation(CustomAnnotation.class);
            if (annotation != null) {
                System.out.println(field.getName() + ": " + annotation.value());
            }
        }
    }
}