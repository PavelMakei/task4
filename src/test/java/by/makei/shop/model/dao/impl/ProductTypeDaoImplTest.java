package by.makei.shop.model.dao.impl;

import by.makei.shop.exception.DaoException;
import by.makei.shop.model.connectionpool.DbConnectionPool;
import by.makei.shop.model.entity.ProductType;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProductTypeDaoImplTest {

    @Test
    void findAllTypesTest() throws DaoException {
        ProductTypeDaoImpl productTypeDao = ProductTypeDaoImpl.getInstance();;
        List<ProductType> types = productTypeDao.findAll();
        assertTrue(types.size() > 0);
    }

    @AfterAll
    static void teardown() {
        DbConnectionPool.getInstance().shutdown();
    }
}