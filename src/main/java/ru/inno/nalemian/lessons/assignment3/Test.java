package ru.inno.nalemian.lessons.assignment3;

import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        try {
            final int size = 5;
            final int testSize = 7;
            List<Integer> list = new ArrayList<>(size);
            for (int i = 0; i < testSize; i++) {
                System.out.println(list.get(i));
            }
        } catch (CommandRuntimeException e) {
            System.out.println(e.getMessage());
        }

    }
}

