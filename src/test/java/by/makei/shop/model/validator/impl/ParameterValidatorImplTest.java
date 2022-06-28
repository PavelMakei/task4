package by.makei.shop.model.validator.impl;

import by.makei.shop.exception.ServiceException;
import by.makei.shop.model.connectionpool.DbConnectionPool;
import by.makei.shop.model.validator.ParameterValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.HashMap;
import java.util.Map;

import static by.makei.shop.command.AttributeName.*;
import static org.junit.jupiter.api.Assertions.*;

class ParameterValidatorImplTest {
    private static final Logger logger = LogManager.getLogger();
    private static Map<String, String> incomeData = new HashMap<>();
    private static ParameterValidator validator;

    @BeforeEach
    void init() {
        validator = ParameterValidatorImpl.getInstance();
        incomeData = new HashMap<>();
    }


    @ParameterizedTest()
    @ValueSource(strings = { "1", "123", "999" })
    void validateAndMarkIncomeDataIdCorrectTest(String word) throws ServiceException {
        incomeData.put(ID,word);
        boolean isCorrect = validator.validateAndMarkIncomeData(incomeData);
        assertTrue(isCorrect && !incomeData.containsKey(INVALID_ID));
    }
    @ParameterizedTest()
    @ValueSource(strings = { "-1", "123.2", "999123123","","a" })
    void validateAndMarkIncomeDataIdIncorrectTest(String word) throws ServiceException {
        incomeData.put(ID,word);
        boolean isCorrect = validator.validateAndMarkIncomeData(incomeData);
        assertTrue( !isCorrect && incomeData.containsKey(INVALID_ID));
    }

    @ParameterizedTest()
    @ValueSource(strings = { "Ivan", "Сергей", "андрей","niko","ARMEN" })
    void validateAndMarkIncomeDataFirstNameCorrectTest(String word) throws ServiceException {
        incomeData.put(FIRST_NAME,word);
        boolean isCorrect = validator.validateAndMarkIncomeData(incomeData);
        assertTrue(isCorrect && !incomeData.containsKey(INVALID_FIRST_NAME));
    }
    @ParameterizedTest()
    @ValueSource(strings = { "Ivan1", "С", "Vadim_Anna","123","" })
    void validateAndMarkIncomeDataFirstNameIncorrectTest(String word) throws ServiceException {
        incomeData.put(FIRST_NAME,word);
        boolean isCorrect = validator.validateAndMarkIncomeData(incomeData);
        assertTrue( !isCorrect && incomeData.containsKey(INVALID_FIRST_NAME));
    }

    @ParameterizedTest()
    @ValueSource(strings = { "Family", "Яковлев", "андрей","niko","ARMEN" })
    void validateAndMarkIncomeDataLastNameCorrectTest(String word) throws ServiceException {
        incomeData.put(LAST_NAME,word);
        boolean isCorrect = validator.validateAndMarkIncomeData(incomeData);
        assertTrue(isCorrect && !incomeData.containsKey(INVALID_LAST_NAME));
    }
    @ParameterizedTest()
    @ValueSource(strings = { "Family1", "С", "Vadim_Anna","123","" })
    void validateAndMarkIncomeDataLastNameIncorrectTest(String word) throws ServiceException {
        incomeData.put(LAST_NAME,word);
        boolean isCorrect = validator.validateAndMarkIncomeData(incomeData);
        assertTrue( !isCorrect && incomeData.containsKey(INVALID_LAST_NAME));
    }

    @ParameterizedTest()
    @ValueSource(strings = { "AdMiN", "Пользователь1", "123фы","Фёдор","ARMEN" })
    void validateAndMarkIncomeDataLoginCorrectTest(String word) throws ServiceException {
        incomeData.put(LOGIN,word);
        boolean isCorrect = validator.validateAndMarkIncomeData(incomeData);
        assertTrue(isCorrect && !incomeData.containsKey(INVALID_LOGIN));
    }
    @ParameterizedTest()
    @ValueSource(strings = { "1", "12", "Vadim/Anna","<Asd","Roman?" })
    void validateAndMarkIncomeDataLoginIncorrectTest(String word) throws ServiceException {
        incomeData.put(LOGIN,word);
        boolean isCorrect = validator.validateAndMarkIncomeData(incomeData);
        assertTrue( !isCorrect && incomeData.containsKey(INVALID_LOGIN));
    }

    @ParameterizedTest()
    @ValueSource(strings = { "anna-marya@gmail.com", "yohoho@yahoo.biz.by", "Woolf123@ya.ru","Name.Lastname@example.com","google@google.by" })
    void validateAndMarkIncomeDataEmailCorrectTest(String word) throws ServiceException {
        incomeData.put(EMAIL,word);
        boolean isCorrect = validator.validateAndMarkIncomeData(incomeData);
        assertTrue(isCorrect && !incomeData.containsKey(INVALID_EMAIL));
    }
    @ParameterizedTest()
    @ValueSource(strings = { "1..vpole@voin.ne", "google.com.com", "Vadim@neprohodim@.last.ru","something.like@email","Pustite,urody@bel.by" })
    void validateAndMarkIncomeDataEmailIncorrectTest(String word) throws ServiceException {
        incomeData.put(EMAIL,word);
        boolean isCorrect = validator.validateAndMarkIncomeData(incomeData);
        assertTrue( !isCorrect && incomeData.containsKey(INVALID_EMAIL));
    }
    @ParameterizedTest()
    @ValueSource(strings = { "(025)1234567", "(029)7654321", "(044)1111111"})
    void validateAndMarkIncomeDataPhoneCorrectTest(String word) throws ServiceException {
        incomeData.put(PHONE,word);
        boolean isCorrect = validator.validateAndMarkIncomeData(incomeData);
        assertTrue(isCorrect && !incomeData.containsKey(INVALID_PHONE));
    }
    @ParameterizedTest()
    @ValueSource(strings = { "+375251234567", "(25)1234567)", "(025)123456","(029)12345678","(028)1234567" })
    void validateAndMarkIncomeDataPhoneIncorrectTest(String word) throws ServiceException {
        incomeData.put(PHONE,word);
        boolean isCorrect = validator.validateAndMarkIncomeData(incomeData);
        assertTrue( !isCorrect && incomeData.containsKey(INVALID_PHONE));
    }

    @ParameterizedTest()
    @ValueSource(strings = {"АнтрОпоЛогия", "пароль", "даже1это", "WTF123", "0251234567", "ФалЬшыYka", "БаррЕЛЬ"})
    void validateAndMarkIncomeDataPasswordCorrectTest(String word) throws ServiceException {
        incomeData.put(PASSWORD,word);
        boolean isCorrect = validator.validateAndMarkIncomeData(incomeData);
        assertTrue(isCorrect && !incomeData.containsKey(INVALID_PASSWORD));
    }
    @ParameterizedTest()
    @ValueSource(strings = { "WTF", "(25)1234567", "Сейчас-то чего","<RoBin","12345678901234567" })
    void validateAndMarkIncomeDataPasswordIncorrectTest(String word) throws ServiceException {
        incomeData.put(PASSWORD,word);
        boolean isCorrect = validator.validateAndMarkIncomeData(incomeData);
        assertTrue( !isCorrect && incomeData.containsKey(INVALID_PASSWORD));
    }

    @ParameterizedTest()
    @ValueSource(strings = {"ADMIN", "USER","BLOCKED", "GUEST"})
    void validateAndMarkIncomeDataAccessLevelCorrectTest(String word) throws ServiceException {
        incomeData.put(ACCESS_LEVEL,word);
        boolean isCorrect = validator.validateAndMarkIncomeData(incomeData);
        assertTrue(isCorrect);
    }
    @ParameterizedTest()
    @ValueSource(strings = { "admin", "NIKODIM", "111111","<ADMIN","ADMINUSER" })
    void validateAndMarkIncomeDataAccessLevelIncorrectTest(String word) throws ServiceException {
        incomeData.put(ACCESS_LEVEL,word);
        boolean isCorrect = validator.validateAndMarkIncomeData(incomeData);
        assertTrue( !isCorrect );
    }

    @ParameterizedTest()
    @ValueSource(strings = {"1", "2", "3"})
    void validateAndMarkIncomeDataBrandIdCorrectTest(String word) throws ServiceException {
        incomeData.put(BRAND_ID,word);
        boolean isCorrect = validator.validateAndMarkIncomeData(incomeData);
        assertTrue(isCorrect && !incomeData.containsKey(INVALID_BRAND_ID));

    }
    @ParameterizedTest()
    @ValueSource(strings = { "123456", "1.1", "1,1"," 1","1 " })
    void validateAndMarkIncomeDataBrandIdIncorrectTest(String word) throws ServiceException {
        incomeData.put(BRAND_ID,word);
        boolean isCorrect = validator.validateAndMarkIncomeData(incomeData);
        assertTrue( !isCorrect && incomeData.containsKey(INVALID_BRAND_ID));

    }

    @AfterAll
    @Disabled
    static void shutdown(){
        DbConnectionPool.getInstance().shutdown();
    }
}