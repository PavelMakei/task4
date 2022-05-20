package by.makei.shop.model.dao;

import by.makei.shop.exception.DaoException;
import by.makei.shop.model.entity.Product;

import java.util.Map;

public interface ProductDao extends BaseDao<Product>{
    Map<Product,String> findAllMap() throws DaoException;
    Map<Product, String> findBySearchParam(int brandId, int typeId, int minPrice, int maxPrice, int minPower,
                                           int maxPower, int searchFrom, int searchTo,
                                           String searchWord, String orderQuery, int inStock) throws DaoException;

    int countBySearchParam(int brandId, int typeId, int minPrice, int maxPrice, int minPower, int maxPower, String searchWord, int inStock) throws DaoException;

    Map<Product, String> findMapProductQuantityById(String id, String id1) throws DaoException;
}
