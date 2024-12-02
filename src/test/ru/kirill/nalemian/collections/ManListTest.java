package ru.kirill.nalemian.collections;

import org.junit.jupiter.api.Test;
import ru.kirill.nalemian.SuperMan;

class ManListTest {
    @Test
    void createObjectManList() {
        var list = new ManList<SuperMan>();
        list.add(new SuperMan("name", "superPower"));
        System.out.println(1);
    }
}