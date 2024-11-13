package ru.inno.nalemian.lessons.lab11.exercise2;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BillTest {
    @Test
    void testBuildConstructor() {
        Bill bill = new Bill(1, "test", 1.2, 1, 1);
        assertEquals(1, bill.getBillId());
    }
}