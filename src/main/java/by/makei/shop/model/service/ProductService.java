package by.makei.shop.model.service;

import by.makei.shop.exception.ServiceException;
import by.makei.shop.model.entity.Brand;
import by.makei.shop.model.entity.Product;
import by.makei.shop.model.entity.ProductType;

import java.util.Map;

public interface ProductService {

    /**
     * check income parameters? if incorrect - add incorrect markers and return false
     *
     * @param productData should contain range data to fill into {@link Product}
     * @param photo       as byte array
     * @return boolean as result
     * @throws ServiceException
     */
    boolean addNewProduct(Map<String, String> productData, byte[] photo) throws ServiceException;

    /**
     * find all {@link Brand}
     *
     * @return Map String - Brand name, String - Brand id
     * @throws ServiceException
     */
    Map<String, String> findAllBrandsMap() throws ServiceException;

    /**
     * find all{@link ProductType}
     *
     * @return Map String - ProductType name, String ProductType id
     * @throws ServiceException
     */
    Map<String, String> findAllTypesMap() throws ServiceException;

    /**
     * check and correct (if incorrect) searchParamMap
     * find Product by variety params.
     *
     * @param searchParamMap    variety of params
     * @param orderByParamQuery Map order name - parameter
     * @return new Map <Product, String - quantity in stock>
     * @throws ServiceException
     */
    Map<Product, String> findProductsByParam(Map<String, String> searchParamMap, Map<String, String> orderByParamQuery) throws ServiceException;

    /**
     * check if id is valid, else throw ServiceException
     * find {@link Product} by id param
     *
     * @param id as String
     * @return {@link Product}
     * @throws ServiceException
     */
    Product findProductById(String id) throws ServiceException;

    /**
     * check if inputProductIdQuantity is valid, else mark incorrect data and return false
     * fill productQuantityMap
     *
     * @param inputProductIdQuantity Map String productId, String quantity
     * @param productQuantityMap     Map Product, String - quantity. Fill map because of unification used DAO
     * @return boolean as result
     * @throws ServiceException
     */
    boolean findMapProductQuantityById(Map<String, String> inputProductIdQuantity, Map<Product, String> productQuantityMap) throws ServiceException;

    /**
     * find {@link Brand} by id
     *
     * @param id
     * @return {@link Brand}
     * @throws ServiceException
     */
    Brand findBrandById(String id) throws ServiceException;

    /**
     * find {@link ProductType} by id
     *
     * @param id as String
     * @return {@link ProductType}
     * @throws ServiceException
     */
    ProductType findProductTypeById(String id) throws ServiceException;

    /**
     * check if updated photo is valid, else add mark incorrect photo to productDataMap, return false
     *
     * @param productDataMap
     * @param bytesPhoto     as byte array
     * @return boolean as result
     * @throws ServiceException
     */
    boolean updatePhoto(Map<String, String> productDataMap, byte[] bytesPhoto) throws ServiceException;

    /**
     * check if productDataMap contains valid data, else mark incorrect and return false
     *
     * @param productDataMap
     * @return boolean as result
     * @throws ServiceException
     */
    boolean updateProductData(Map<String, String> productDataMap) throws ServiceException;
}
