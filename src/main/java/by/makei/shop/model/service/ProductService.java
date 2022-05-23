package by.makei.shop.model.service;

import by.makei.shop.exception.ServiceException;
import by.makei.shop.model.entity.Brand;
import by.makei.shop.model.entity.Product;
import by.makei.shop.model.entity.ProductType;

import java.util.List;
import java.util.Map;

public interface ProductService {

    void addNewProduct(Map<String, String> productData, byte[] photo) throws ServiceException;

    Map<String, String> findAllBrandsMap() throws ServiceException;

    Map<String, String> findAllTypesMap() throws ServiceException;

    List<Product> findAllProductList() throws ServiceException;

    Map<Product,String> findAllProductMap() throws ServiceException;

    Map<Product, String> findProductsByParam(Map<String,String> searchParamMap, Map<String, String> orderByParamQuery) throws ServiceException;

    Product findProductById(String id) throws ServiceException;

    Map<Product, String> findMapProductQuantityById(String id) throws ServiceException;

    Brand findBrandById(String id) throws ServiceException;

    ProductType findProductTypeById(String id)throws ServiceException;

    boolean updatePhoto(String id, byte[] bytesPhoto) throws ServiceException;

    boolean updateProductData(Map<String, String> productDataMap) throws ServiceException;
}
