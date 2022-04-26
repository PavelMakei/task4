package by.makei.shop.model.service;

import by.makei.shop.exception.ServiceException;

import java.util.Map;

public interface AdminService {

    boolean addNewProduct(Map<String, String> productData, byte[] photo) throws ServiceException;

    Map<String, Integer> getAllBrandsMap() throws ServiceException;

    Map<String, Integer> getAllTypesMap() throws ServiceException;
}
