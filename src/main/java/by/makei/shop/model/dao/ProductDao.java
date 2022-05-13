package by.makei.shop.model.dao;

import by.makei.shop.exception.DaoException;
import by.makei.shop.model.entity.Product;

import java.util.Map;

public interface ProductDao extends BaseDao<Product>{
    Map<Product,String> findAllMap() throws DaoException;
    Map<Product, String> findBySearchParam(int brandId, int typeId, int minPrice, int maxPrice, int minPower, int maxPower) throws DaoException;
}
