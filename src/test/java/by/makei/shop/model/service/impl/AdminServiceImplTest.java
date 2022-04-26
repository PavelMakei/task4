package by.makei.shop.model.service.impl;

import by.makei.shop.exception.ServiceException;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class AdminServiceImplTest {

    @Test
    void getAllBrandsMapTest() throws ServiceException {
        AdminServiceImpl adminService = new AdminServiceImpl();
        Map<String, Integer> brands = adminService.getAllBrandsMap();
        assertFalse(brands.isEmpty());
    }

    @Test
    void getAllTypesMapTest() throws ServiceException {
        AdminServiceImpl adminService = new AdminServiceImpl();
        Map<String, Integer> types = adminService.getAllTypesMap();
        assertFalse(types.isEmpty());
    }
}