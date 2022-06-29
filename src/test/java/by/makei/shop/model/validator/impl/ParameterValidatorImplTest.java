package by.makei.shop.model.validator.impl;

import by.makei.shop.exception.ServiceException;
import by.makei.shop.model.connectionpool.DbConnectionPool;
import by.makei.shop.model.validator.ParameterValidator;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.HashMap;
import java.util.Map;

import static by.makei.shop.command.AttributeName.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ParameterValidatorImplTest {
    private static Map<String, String> incomeData = new HashMap<>();
    private static ParameterValidator validator;

    @BeforeAll
    static void initForAll() {
        validator = ParameterValidatorImpl.getInstance();
    }

    @BeforeEach
    void init() {

        incomeData = new HashMap<>();
    }


    @ParameterizedTest()
    @ValueSource(strings = {"1", "123", "999"})
    void validateAndMarkIncomeDataIdCorrectTest(String word) throws ServiceException {
        incomeData.put(ID, word);
        boolean isCorrect = validator.validateAndMarkIncomeData(incomeData);
        assertTrue(isCorrect && !incomeData.containsKey(INVALID_ID));
    }

    @ParameterizedTest()
    @ValueSource(strings = {"-1", "123.2", "999123123", "", "a"})
    void validateAndMarkIncomeDataIdIncorrectTest(String word) throws ServiceException {
        incomeData.put(ID, word);
        boolean isCorrect = validator.validateAndMarkIncomeData(incomeData);
        assertTrue(!isCorrect && incomeData.containsKey(INVALID_ID));
    }

    @ParameterizedTest()
    @ValueSource(strings = {"Ivan", "Сергей", "андрей", "niko", "ARMEN"})
    void validateAndMarkIncomeDataFirstNameCorrectTest(String word) throws ServiceException {
        incomeData.put(FIRST_NAME, word);
        boolean isCorrect = validator.validateAndMarkIncomeData(incomeData);
        assertTrue(isCorrect && !incomeData.containsKey(INVALID_FIRST_NAME));
    }

    @ParameterizedTest()
    @ValueSource(strings = {"Ivan1", "С", "Vadim_Anna", "123", ""})
    void validateAndMarkIncomeDataFirstNameIncorrectTest(String word) throws ServiceException {
        incomeData.put(FIRST_NAME, word);
        boolean isCorrect = validator.validateAndMarkIncomeData(incomeData);
        assertTrue(!isCorrect && incomeData.containsKey(INVALID_FIRST_NAME));
    }

    @ParameterizedTest()
    @ValueSource(strings = {"Family", "Яковлев", "андрей", "niko", "ARMEN"})
    void validateAndMarkIncomeDataLastNameCorrectTest(String word) throws ServiceException {
        incomeData.put(LAST_NAME, word);
        boolean isCorrect = validator.validateAndMarkIncomeData(incomeData);
        assertTrue(isCorrect && !incomeData.containsKey(INVALID_LAST_NAME));
    }

    @ParameterizedTest()
    @ValueSource(strings = {"Family1", "С", "Vadim_Anna", "123", ""})
    void validateAndMarkIncomeDataLastNameIncorrectTest(String word) throws ServiceException {
        incomeData.put(LAST_NAME, word);
        boolean isCorrect = validator.validateAndMarkIncomeData(incomeData);
        assertTrue(!isCorrect && incomeData.containsKey(INVALID_LAST_NAME));
    }

    @ParameterizedTest()
    @ValueSource(strings = {"AdMiN", "Пользователь1", "123фы", "Фёдор", "ARMEN"})
    void validateAndMarkIncomeDataLoginCorrectTest(String word) throws ServiceException {
        incomeData.put(LOGIN, word);
        boolean isCorrect = validator.validateAndMarkIncomeData(incomeData);
        assertTrue(isCorrect && !incomeData.containsKey(INVALID_LOGIN));
    }

    @ParameterizedTest()
    @ValueSource(strings = {"1", "12", "Vadim/Anna", "<Asd", "Roman?"})
    void validateAndMarkIncomeDataLoginIncorrectTest(String word) throws ServiceException {
        incomeData.put(LOGIN, word);
        boolean isCorrect = validator.validateAndMarkIncomeData(incomeData);
        assertTrue(!isCorrect && incomeData.containsKey(INVALID_LOGIN));
    }

    @ParameterizedTest()
    @ValueSource(strings = {"anna-marya@gmail.com", "yohoho@yahoo.biz.by", "Woolf123@ya.ru", "Name.Lastname@example.com", "google@google.by"})
    void validateAndMarkIncomeDataEmailCorrectTest(String word) throws ServiceException {
        incomeData.put(EMAIL, word);
        boolean isCorrect = validator.validateAndMarkIncomeData(incomeData);
        assertTrue(isCorrect && !incomeData.containsKey(INVALID_EMAIL));
    }

    @ParameterizedTest()
    @ValueSource(strings = {"1..vpole@voin.ne", "google.com.com", "Vadim@neprohodim@.last.ru", "something.like@email", "Pustite,urody@bel.by"})
    void validateAndMarkIncomeDataEmailIncorrectTest(String word) throws ServiceException {
        incomeData.put(EMAIL, word);
        boolean isCorrect = validator.validateAndMarkIncomeData(incomeData);
        assertTrue(!isCorrect && incomeData.containsKey(INVALID_EMAIL));
    }

    @ParameterizedTest()
    @ValueSource(strings = {"(025)1234567", "(029)7654321", "(044)1111111"})
    void validateAndMarkIncomeDataPhoneCorrectTest(String word) throws ServiceException {
        incomeData.put(PHONE, word);
        boolean isCorrect = validator.validateAndMarkIncomeData(incomeData);
        assertTrue(isCorrect && !incomeData.containsKey(INVALID_PHONE));
    }

    @ParameterizedTest()
    @ValueSource(strings = {"+375251234567", "(25)1234567)", "(025)123456", "(029)12345678", "(028)1234567"})
    void validateAndMarkIncomeDataPhoneIncorrectTest(String word) throws ServiceException {
        incomeData.put(PHONE, word);
        boolean isCorrect = validator.validateAndMarkIncomeData(incomeData);
        assertTrue(!isCorrect && incomeData.containsKey(INVALID_PHONE));
    }

    @ParameterizedTest()
    @ValueSource(strings = {"АнтрОпоЛогия", "пароль", "даже1это", "WTF123", "0251234567", "ФалЬшыYka", "БаррЕЛЬ"})
    void validateAndMarkIncomeDataPasswordCorrectTest(String word) throws ServiceException {
        incomeData.put(PASSWORD, word);
        boolean isCorrect = validator.validateAndMarkIncomeData(incomeData);
        assertTrue(isCorrect && !incomeData.containsKey(INVALID_PASSWORD));
    }

    @ParameterizedTest()
    @ValueSource(strings = {"WTF", "(25)1234567", "Сейчас-то чего", "<RoBin", "12345678901234567"})
    void validateAndMarkIncomeDataPasswordIncorrectTest(String word) throws ServiceException {
        incomeData.put(PASSWORD, word);
        boolean isCorrect = validator.validateAndMarkIncomeData(incomeData);
        assertTrue(!isCorrect && incomeData.containsKey(INVALID_PASSWORD));
    }

    @ParameterizedTest()
    @ValueSource(strings = {"ADMIN", "USER", "BLOCKED", "GUEST"})
    void validateAndMarkIncomeDataAccessLevelCorrectTest(String word) throws ServiceException {
        incomeData.put(ACCESS_LEVEL, word);
        boolean isCorrect = validator.validateAndMarkIncomeData(incomeData);
        assertTrue(isCorrect);
    }

    @ParameterizedTest()
    @ValueSource(strings = {"admin", "NIKODIM", "111111", "<ADMIN", "ADMINUSER"})
    void validateAndMarkIncomeDataAccessLevelIncorrectTest(String word) throws ServiceException {
        incomeData.put(ACCESS_LEVEL, word);
        boolean isCorrect = validator.validateAndMarkIncomeData(incomeData);
        assertFalse(isCorrect);
    }

    @ParameterizedTest()
    @ValueSource(strings = {"1", "2", "3"})
    void validateAndMarkIncomeDataBrandIdCorrectTest(String word) throws ServiceException {
        incomeData.put(BRAND_ID, word);
        boolean isCorrect = validator.validateAndMarkIncomeData(incomeData);
        assertTrue(isCorrect && !incomeData.containsKey(INVALID_BRAND_ID));

    }

    @ParameterizedTest()
    @ValueSource(strings = {"123456", "1.1", "1,1", " 1", "1 "})
    void validateAndMarkIncomeDataBrandIdIncorrectTest(String word) throws ServiceException {
        incomeData.put(BRAND_ID, word);
        boolean isCorrect = validator.validateAndMarkIncomeData(incomeData);
        assertTrue(!isCorrect && incomeData.containsKey(INVALID_BRAND_ID));

    }

    @ParameterizedTest()
    @ValueSource(strings = {"1", "2", "3"})
    void validateAndMarkIncomeDataTypeIdCorrectTest(String word) throws ServiceException {
        incomeData.put(TYPE_ID, word);
        boolean isCorrect = validator.validateAndMarkIncomeData(incomeData);
        assertTrue(isCorrect && !incomeData.containsKey(INVALID_TYPE_ID));

    }

    @ParameterizedTest()
    @ValueSource(strings = {"123456", "1.1", "1,1", " 1", "1 "})
    void validateAndMarkIncomeDataTypeIdIncorrectTest(String word) throws ServiceException {
        incomeData.put(TYPE_ID, word);
        boolean isCorrect = validator.validateAndMarkIncomeData(incomeData);
        assertTrue(!isCorrect && incomeData.containsKey(INVALID_TYPE_ID));
    }

    @ParameterizedTest()
    @ValueSource(strings = {"Sunlight", "Arte-931", "Лампочка Ильича"})
    void validateAndMarkIncomeDataProductNameCorrectTest(String word) throws ServiceException {
        incomeData.put(PRODUCT_NAME, word);
        boolean isCorrect = validator.validateAndMarkIncomeData(incomeData);
        assertTrue(isCorrect && !incomeData.containsKey(INVALID_PRODUCT_NAME));

    }

    @ParameterizedTest()
    @ValueSource(strings = {"Arte<", "Question?", "Percent%", "AR", "<tr>"})
    void validateAndMarkIncomeDataProductNameIncorrectTest(String word) throws ServiceException {
        incomeData.put(PRODUCT_NAME, word);
        boolean isCorrect = validator.validateAndMarkIncomeData(incomeData);
        assertTrue(!isCorrect && incomeData.containsKey(INVALID_PRODUCT_NAME));
    }

    @ParameterizedTest()
    @ValueSource(strings = {"Sunlight morning. If it Possible", "Arte-931, this is the best."})
    void validateAndMarkIncomeDataDescriptionCorrectTest(String word) throws ServiceException {
        incomeData.put(DESCRIPTION, word);
        boolean isCorrect = validator.validateAndMarkIncomeData(incomeData);
        assertTrue(isCorrect && !incomeData.containsKey(INVALID_DESCRIPTION));

    }

    @ParameterizedTest()
    @ValueSource(strings = {"<br>", "Question?", "Percent%", "<>"})
    void validateAndMarkIncomeDataDescriptionIncorrectTest(String word) throws ServiceException {
        incomeData.put(DESCRIPTION, word);
        boolean isCorrect = validator.validateAndMarkIncomeData(incomeData);
        assertTrue(!isCorrect && incomeData.containsKey(INVALID_DESCRIPTION));
    }

    @ParameterizedTest()
    @ValueSource(strings = {"0", "23.34", "99999.99"})
    void validateAndMarkIncomeDataPriceCorrectTest(String word) throws ServiceException {
        incomeData.put(PRICE, word);
        boolean isCorrect = validator.validateAndMarkIncomeData(incomeData);
        assertTrue(isCorrect && !incomeData.containsKey(INVALID_PRICE));

    }

    @ParameterizedTest()
    @ValueSource(strings = {"24,34", "-0", "123456"})
    void validateAndMarkIncomeDataPriceIncorrectTest(String word) throws ServiceException {
        incomeData.put(PRICE, word);
        boolean isCorrect = validator.validateAndMarkIncomeData(incomeData);
        assertTrue(!isCorrect && incomeData.containsKey(INVALID_PRICE));
    }

    @ParameterizedTest()
    @ValueSource(strings = {"red", "red-brown", "yellow submarine", "СИНИЙ"})
    void validateAndMarkIncomeDataColourCorrectTest(String word) throws ServiceException {
        incomeData.put(COLOUR, word);
        boolean isCorrect = validator.validateAndMarkIncomeData(incomeData);
        assertTrue(isCorrect && !incomeData.containsKey(INVALID_COLOUR));

    }

    @ParameterizedTest()
    @ValueSource(strings = {"RGBA #123212", "123%", "<RED"})
    void validateAndMarkIncomeDataColourIncorrectTest(String word) throws ServiceException {
        incomeData.put(COLOUR, word);
        boolean isCorrect = validator.validateAndMarkIncomeData(incomeData);
        assertTrue(!isCorrect && incomeData.containsKey(INVALID_COLOUR));
    }

    @ParameterizedTest()
    @ValueSource(strings = {"0", "123", "999"})
    void validateAndMarkIncomeDataPowerCorrectTest(String word) throws ServiceException {
        incomeData.put(POWER, word);
        boolean isCorrect = validator.validateAndMarkIncomeData(incomeData);
        assertTrue(isCorrect && !incomeData.containsKey(INVALID_POWER));

    }

    @ParameterizedTest()
    @ValueSource(strings = {"-1", "123%", "23,34", "12W", "<23>"})
    void validateAndMarkIncomeDataPowerIncorrectTest(String word) throws ServiceException {
        incomeData.put(POWER, word);
        boolean isCorrect = validator.validateAndMarkIncomeData(incomeData);
        assertTrue(!isCorrect && incomeData.containsKey(INVALID_POWER));
    }

    @ParameterizedTest()
    @ValueSource(strings = {"100x100x100", "H200W300"})
    void validateAndMarkIncomeDataSizeCorrectTest(String word) throws ServiceException {
        incomeData.put(SIZE, word);
        boolean isCorrect = validator.validateAndMarkIncomeData(incomeData);
        assertTrue(isCorrect && !incomeData.containsKey(INVALID_SIZE));

    }

    @ParameterizedTest()
    @ValueSource(strings = {"", "<234", "23,34", "12/23"})
    void validateAndMarkIncomeDataSizeIncorrectTest(String word) throws ServiceException {
        incomeData.put(SIZE, word);
        boolean isCorrect = validator.validateAndMarkIncomeData(incomeData);
        assertTrue(!isCorrect && incomeData.containsKey(INVALID_SIZE));
    }

    @ParameterizedTest()
    @ValueSource(strings = {"0", "999"})
    void validateAndMarkIncomeDataQuantityCorrectTest(String word) throws ServiceException {
        incomeData.put(QUANTITY_IN_STOCK, word);
        boolean isCorrect = validator.validateAndMarkIncomeData(incomeData);
        assertTrue(isCorrect && !incomeData.containsKey(INVALID_QUANTITY_IN_STOCK));

    }

    @ParameterizedTest()
    @ValueSource(strings = {"", "-1", "1.1", "1234"})
    void validateAndMarkIncomeDataQuantityIncorrectTest(String word) throws ServiceException {
        incomeData.put(QUANTITY_IN_STOCK, word);
        boolean isCorrect = validator.validateAndMarkIncomeData(incomeData);
        assertTrue(!isCorrect && incomeData.containsKey(INVALID_QUANTITY_IN_STOCK));
    }

    @ParameterizedTest()
    @ValueSource(strings = {"1234567890123456"})
    void validateAndMarkIncomeDataCardNumberCorrectTest(String word) throws ServiceException {
        incomeData.put(CARD_NUMBER, word);
        boolean isCorrect = validator.validateAndMarkIncomeData(incomeData);
        assertTrue(isCorrect && !incomeData.containsKey(INVALID_CARD_NUMBER));

    }

    @ParameterizedTest()
    @ValueSource(strings = {"123456789012345", "12345678901234567"})
    void validateAndMarkIncomeDataCardNumberIncorrectTest(String word) throws ServiceException {
        incomeData.put(CARD_NUMBER, word);
        boolean isCorrect = validator.validateAndMarkIncomeData(incomeData);
        assertTrue(!isCorrect && incomeData.containsKey(INVALID_CARD_NUMBER));
    }

    @ParameterizedTest()
    @ValueSource(strings = {"000", "999"})
    void validateAndMarkIncomeDataCardCvcCorrectTest(String word) throws ServiceException {
        incomeData.put(CARD_CVC, word);
        boolean isCorrect = validator.validateAndMarkIncomeData(incomeData);
        assertTrue(isCorrect && !incomeData.containsKey(INVALID_CARD_CVC));

    }

    @ParameterizedTest()
    @ValueSource(strings = {"00", "0000"})
    void validateAndMarkIncomeDataCardCvcIncorrectTest(String word) throws ServiceException {
        incomeData.put(CARD_CVC, word);
        boolean isCorrect = validator.validateAndMarkIncomeData(incomeData);
        assertTrue(!isCorrect && incomeData.containsKey(INVALID_CARD_CVC));
    }

    @ParameterizedTest()
    @ValueSource(strings = {"07/2022", "12/2024"})
//should consist of future data
    void validateAndMarkIncomeDataCardExpDateCorrectTest(String word) throws ServiceException {
        incomeData.put(CARD_EXP_DATE, word);
        boolean isCorrect = validator.validateAndMarkIncomeData(incomeData);
        assertTrue(isCorrect && !incomeData.containsKey(INVALID_CARD_EXP_DATE));

    }

    @ParameterizedTest()
    @ValueSource(strings = {"05/2022", "01/01/2025", ""})
    void validateAndMarkIncomeDataCardExpDateIncorrectTest(String word) throws ServiceException {
        incomeData.put(CARD_EXP_DATE, word);
        boolean isCorrect = validator.validateAndMarkIncomeData(incomeData);
        assertTrue(!isCorrect && incomeData.containsKey(INVALID_CARD_EXP_DATE));
    }

    @ParameterizedTest()
    @ValueSource(strings = {"NAME SURNAME", "MASTERCARD SOMECARD"})
//should consist of future data
    void validateAndMarkIncomeDataCardHolderCorrectTest(String word) throws ServiceException {
        incomeData.put(CARD_HOLDER, word);
        boolean isCorrect = validator.validateAndMarkIncomeData(incomeData);
        assertTrue(isCorrect && !incomeData.containsKey(INVALID_CARD_HOLDER));

    }

    @ParameterizedTest()
    @ValueSource(strings = {"incorrect name", "CARD", "THIS IS WRONG", "123 ERROR"})
    void validateAndMarkIncomeDataCardHolderIncorrectTest(String word) throws ServiceException {
        incomeData.put(CARD_HOLDER, word);
        boolean isCorrect = validator.validateAndMarkIncomeData(incomeData);
        assertTrue(!isCorrect && incomeData.containsKey(INVALID_CARD_HOLDER));
    }

    @ParameterizedTest()
    @ValueSource(strings = {"0", "99999.99"})
//should consist of future data
    void validateAndMarkIncomeDataAmountToDepositCorrectTest(String word) throws ServiceException {
        incomeData.put(AMOUNT_TO_DEPOSIT, word);
        boolean isCorrect = validator.validateAndMarkIncomeData(incomeData);
        assertTrue(isCorrect && !incomeData.containsKey(INVALID_AMOUNT_TO_DEPOSIT));

    }

    @ParameterizedTest()
    @ValueSource(strings = {"-1", "99999.999", "123456"})
    void validateAndMarkIncomeDataAmountToDepositIncorrectTest(String word) throws ServiceException {
        incomeData.put(AMOUNT_TO_DEPOSIT, word);
        boolean isCorrect = validator.validateAndMarkIncomeData(incomeData);
        assertTrue(!isCorrect && incomeData.containsKey(INVALID_AMOUNT_TO_DEPOSIT));
    }

    @ParameterizedTest()
    @ValueSource(strings = {"Красная Площадь, дом 1", "г.Свободный, ул.Красивая, д.1, к.2, кв.3", "San-Francisco, Rich st., 22-11"})
//should consist of future data
    void validateAndMarkIncomeDataAddressCorrectTest(String word) throws ServiceException {
        incomeData.put(ADDRESS, word);
        boolean isCorrect = validator.validateAndMarkIncomeData(incomeData);
        assertTrue(isCorrect && !incomeData.containsKey(INVALID_ADDRESS));

    }

    @ParameterizedTest()
    @ValueSource(strings = {"<Мордер", "г.Перекопск, ул.Шестая, д№3 ", "San-Francisco 18%"})
    void validateAndMarkIncomeDataAddressIncorrectTest(String word) throws ServiceException {
        incomeData.put(ADDRESS, word);
        boolean isCorrect = validator.validateAndMarkIncomeData(incomeData);
        assertTrue(!isCorrect && incomeData.containsKey(INVALID_ADDRESS));
    }

    @ParameterizedTest()
    @ValueSource(strings = {"Здесь какой-то длинный текст.", "", "Что бы ещё такого написать", "Should it be on English"})
//should consist of future data
    void validateAndMarkIncomeDataDetailCorrectTest(String word) throws ServiceException {
        incomeData.put(DETAIL, word);
        boolean isCorrect = validator.validateAndMarkIncomeData(incomeData);
        assertTrue(isCorrect && !incomeData.containsKey(INVALID_DETAIL));

    }

    @ParameterizedTest()
    @ValueSource(strings = {"<Мордер непобедим", "А почему нельзя%", "Это не вопрос?"})
    void validateAndMarkIncomeDataDetailIncorrectTest(String word) throws ServiceException {
        incomeData.put(DETAIL, word);
        boolean isCorrect = validator.validateAndMarkIncomeData(incomeData);
        assertTrue(!isCorrect && incomeData.containsKey(INVALID_DETAIL));
    }

    @AfterAll
    static void shutdown() {
        DbConnectionPool.getInstance().shutdown();
    }
}