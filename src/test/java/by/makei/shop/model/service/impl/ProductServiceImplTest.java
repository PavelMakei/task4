package by.makei.shop.model.service.impl;

import by.makei.shop.exception.ServiceException;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ProductServiceImplTest {

    @Test
    void getAllBrandsMapTest() throws ServiceException {
        ProductServiceImpl productService = new ProductServiceImpl();
        Map<String, String> brands = productService.findAllBrandsMap();
        assertFalse(brands.isEmpty());
    }

    @Test
    void getAllTypesMapTest() throws ServiceException {
        ProductServiceImpl productService = new ProductServiceImpl();
        Map<String, String> types = productService.findAllTypesMap();
        assertFalse(types.isEmpty());
    }


}