package by.makei.shop.util;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PasswordEncoderTest {
    public final static String password = "1111";

    @Test
    void getHashedPasswordTest() {
        String expected = "f77408b9b60b32af500acf58db3ecea7b9155123c7eb6";
        String actual = PasswordEncoder.getHashedPassword(password);
        System.out.println(actual);
        assertEquals(expected, actual);
    }

    @Test
    void festGetHashedPasswordTest() {
        assertThat(PasswordEncoder.getHashedPassword(password)).as("incorrect operation")
                .isEqualTo("f77408b9b60b32af500acf58db3ecea7b9155123c7eb6");
    }
}