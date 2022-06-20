package by.makei.shop.model.dao.impl;

import by.makei.shop.exception.DaoException;
import by.makei.shop.model.connectionpool.DbConnectionPool;
import by.makei.shop.model.entity.Brand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;


import static org.junit.jupiter.api.Assertions.assertTrue;

class BrandDaoImplTest {
Logger logger = LogManager.getLogger();

    @Test
    void findAllBrandsTest() throws DaoException {
        BrandDaoImpl brandDao = BrandDaoImpl.getInstance();
        List<Brand> brands = brandDao.findAll();
        assertTrue(brands.size() > 0);
    }

    @AfterAll
    static void teardown() {
        DbConnectionPool.getInstance().shutdown();
    }

}