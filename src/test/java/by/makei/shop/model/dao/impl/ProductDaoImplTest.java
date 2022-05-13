package by.makei.shop.model.dao.impl;

import by.makei.shop.exception.DaoException;
import by.makei.shop.model.connectionpool.DbConnectionPool;
import by.makei.shop.model.dao.ProductDao;
import by.makei.shop.model.dao.UserDao;
import by.makei.shop.model.entity.Product;
import by.makei.shop.model.entity.User;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Optional;

import static by.makei.shop.model.command.AttributeName.LOGIN;
import static org.junit.jupiter.api.Assertions.*;

class ProductDaoImplTest {
    static final int CORRECT_BRAND_ID = 2;
    static final int INCORRECT_BRAND_ID = 0;
    static final int INCORRECT_TYPE_ID = 0;
    static final int CORRECT_TYPE_ID = 1;
    static final int MIN_PRICE = 50;
    static final int MAX_PRICE = 300;
    static final int MIN_POWER = 0;
    static final int MAX_POWER = 90;


    public static ProductDao productDao;

    @BeforeAll
    static void init() {
        productDao = new ProductDaoImpl();
    }

    @Test
    void findBySearchCorrectParamShouldReturnNotEmptyMap() throws DaoException {
        Map<Product, String> productsMap = productDao.findBySearchParam(
                CORRECT_BRAND_ID, CORRECT_TYPE_ID, MIN_PRICE, MAX_PRICE, MIN_POWER, MAX_POWER);
        assert (productsMap.size() > 1);
    }

    @Test
    void findBySearchIncorrectParamShouldReturnNotEmptyMap() throws DaoException {
        Map<Product, String> productsMap = productDao.findBySearchParam(
                INCORRECT_BRAND_ID, INCORRECT_TYPE_ID, MIN_PRICE, MAX_PRICE, MIN_POWER, MAX_POWER);
        assert (productsMap.size() > 1);
    }

    @Test
    void countBySearchCorrectParamShouldReturnNotZero() throws DaoException {
        int expected = productDao.countBySearchParam(
                CORRECT_BRAND_ID, CORRECT_TYPE_ID, MIN_PRICE, MAX_PRICE, MIN_POWER, MAX_POWER);
        assert (expected > 1);
    }


    @AfterAll
    static void teardown() {
        DbConnectionPool.getInstance().shutdown();
    }
}

