package by.makei.shop.model.service;

import by.makei.shop.exception.ServiceException;
import by.makei.shop.model.entity.Product;

import java.util.List;
import java.util.Map;

public interface ProductService {

    boolean addNewProduct(Map<String, String> productData, byte[] photo) throws ServiceException;

    Map<String, String> getAllBrandsMap() throws ServiceException;

    Map<String, String> getAllTypesMap() throws ServiceException;

    List<Product> getAllProductList() throws ServiceException;

    Map<Product,String> getAllProductMap() throws ServiceException;

    Map<Product, String> findProductsByParam(Map<String,String> searchParamMap) throws ServiceException;
}
