package by.makei.shop.model.command;

import by.makei.shop.exception.CommandException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommandTypeTempTest {

    @Test
    void getCommand() {
    }

    @Test
    void defineCommand() throws CommandException {
        String[] comm = {"add_some_command"};
        Command command = CommandTypeTemp.defineCommand(comm);
        System.out.println(command);
    }
}