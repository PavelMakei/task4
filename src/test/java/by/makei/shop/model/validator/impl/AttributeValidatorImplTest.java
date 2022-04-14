package by.makei.shop.model.validator.impl;

import by.makei.shop.model.validator.AttributeValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AttributeValidatorImplTest {
    private static final String VALID_NAME = "Алексей";
    private static final String INVALID_NAME = "Алексей5";
    private static final String VALID_LOGIN = "admin1";
    private static final String INVALID_LOGIN = "admin<>";
    private static final String VALID_PASSWORD = "123456QWERT";
    private static final String INVALID_PASSWORD = "admi";
    private static final String VALID_EMAIL = "wer@wer.ru";
    private static final String INVALID_EMAIL = "1wer@wer.ru";
    private static final String VALID_PHONE = "(025)1234567";
    private static final String INVALID_PHONE = "0251234567";
    private static final String VALID_DECIMAL = "12345.12";
    private static final String INVALID_DECIMAL = "123.12.00";

    private AttributeValidator validator;

    @BeforeEach
            void initialize(){
        validator = AttributeValidatorImpl.getInstance();
    }

    @Test
    void isNameValidPositiveTest() {
        assertTrue (validator.isNameValid(VALID_NAME));
    }

    @Test
    void isNameValidNegativeTest() {
        assertFalse (validator.isNameValid(INVALID_NAME));
    }

    @Test
    void isLoginValidPositiveTest() {
        assertTrue (validator.isLoginValid(VALID_LOGIN));
    }

    @Test
    void isLoginValidNegativeTest() {
        assertFalse (validator.isLoginValid(INVALID_LOGIN));
    }

    @Test
    void isPasswordValidPositiveTest() {
        assertTrue(validator.isPasswordValid(VALID_PASSWORD));
    }

    @Test
    void isPasswordValidNegativeTest() {
        assertFalse(validator.isPasswordValid(INVALID_PASSWORD));
    }

    @Test
    void isEmailValidPositiveTest() {
        assertTrue(validator.isEmailValid(VALID_EMAIL));
    }

    @Test
    void isEmailValidNegativeTest() {
        assertFalse(validator.isEmailValid(INVALID_EMAIL));
    }

    @Test
    void isPhoneValidPositiveTest() {
       assertTrue(validator.isPhoneValid(VALID_PHONE));
    }

    @Test
    void isPhoneValidNegativeTest() {
        assertFalse(validator.isPhoneValid(INVALID_PHONE));
    }

    @Test
    void isDecimalValidPositiveTest() {
       assertTrue(validator.isDecimalValid(VALID_DECIMAL));
    }

    @Test
    void isDecimalValidNegativeTest() {
        assertFalse(validator.isDecimalValid(INVALID_DECIMAL));
    }
}