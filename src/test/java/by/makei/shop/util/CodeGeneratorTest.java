package by.makei.shop.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CodeGeneratorTest {

    @Test
    void generateCodeTestShouldReturnString() {
        String expected = CodeGenerator.generateCode();
        assertTrue(expected.length() == 10);
    }
}