package ru.kirill.nalemian;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class ExceptionExamplesTest {
    @Disabled
    @Test
    void stacktraceExample() {
        throw new RuntimeException("Test");
    }

    @Test
    void stacktraceTryCatch() {
        try {
            stacktraceExample();
        } catch (Exception ex) {
            System.out.println(1);
        }
    }

    @Test
    void checkedException() throws Exception {
        throw new Exception("");
    }
}
