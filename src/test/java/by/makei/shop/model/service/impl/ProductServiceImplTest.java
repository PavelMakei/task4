package by.makei.shop.model.service.impl;

import by.makei.shop.exception.ServiceException;
import by.makei.shop.model.service.ProductService;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;

class ProductServiceImplTest {

    @Test
    void getAllBrandsMapTest() throws ServiceException {
        ProductService productService = ProductServiceImpl.getInstance();
        Map<String, String> brands = productService.findAllBrandsMap();
        assertFalse(brands.isEmpty());
    }

    @Test
    void getAllTypesMapTest() throws ServiceException {
        ProductService productService = ProductServiceImpl.getInstance();
        Map<String, String> types = productService.findAllTypesMap();
        assertFalse(types.isEmpty());
    }


}