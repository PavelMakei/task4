package by.makei.shop.model.dao.impl;

import by.makei.shop.exception.DaoException;
import by.makei.shop.model.entity.Brand;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;

import java.util.List;


import static org.junit.jupiter.api.Assertions.assertTrue;

class BrandDaoImplTest {
Logger logger = LogManager.getLogger();
    @Test
    void findAllBrandsTest() throws DaoException {
        BrandDaoImpl brandDao = new BrandDaoImpl();
        List<Brand> brands = brandDao.findAllBrands();
        assertTrue(brands.size() > 0);
    }

}