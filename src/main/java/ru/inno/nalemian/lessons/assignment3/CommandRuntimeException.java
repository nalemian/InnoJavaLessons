package ru.inno.nalemian.lessons.assignment3;

/**
 * thrown when command execution is failed
 */
public class CommandRuntimeException extends RuntimeException {
    public CommandRuntimeException(String invalidCommand) {
        super(invalidCommand);
    }
}
