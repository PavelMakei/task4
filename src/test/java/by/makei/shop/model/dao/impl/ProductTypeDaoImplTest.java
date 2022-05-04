package by.makei.shop.model.dao.impl;

import by.makei.shop.exception.DaoException;
import by.makei.shop.model.entity.ProductType;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProductTypeDaoImplTest {

    @Test
    void findAllTypesTest() throws DaoException {
        ProductTypeDaoImpl productTypeDao = new ProductTypeDaoImpl();
        List<ProductType> types = productTypeDao.findAll();
        assertTrue(types.size() > 0);
    }
}