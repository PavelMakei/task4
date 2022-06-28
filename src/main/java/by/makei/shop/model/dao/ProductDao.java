package by.makei.shop.model.dao;

import by.makei.shop.exception.DaoException;
import by.makei.shop.model.entity.Product;

import java.util.Map;

public interface ProductDao extends BaseDao<Product>{
    Map<Product,String> findAllMap() throws DaoException;

    /**
     * find {@link Product} by parameters as filter
     * @param brandId String id. If empty - will be found all
     * @param typeId Sting id. If empty - will be found all
     * @param minPrice String to filter Price from
     * @param maxPrice String to filter Price to
     * @param minPower String to filter Power from
     * @param maxPower String to filter Power to
     * @param searchFrom String as Limit to return products from position
     * @param offset String as Limit to return products not more than
     * @param searchWord String to filter: product name include
     * @param orderQuery String Ordering by
     * @param inStock String as parameter to Filter if quantity in stock > inStock
     * @return Map {@link Product}, String quantity in stock
     * @throws DaoException
     */
    Map<Product, String> findBySearchParam(int brandId, int typeId, int minPrice, int maxPrice, int minPower,
                                           int maxPower, int searchFrom, int offset,
                                           String searchWord, String orderQuery, int inStock) throws DaoException;

    /**
     * count quantity of filtered products
     * @param brandId String id. If empty - will be found all
     * @param typeId Sting id. If empty - will be found all
     * @param minPrice String to filter Price from
     * @param maxPrice String to filter Price to
     * @param minPower String to filter Power from
     * @param maxPower String to filter Power to
     * @param searchWord String to filter: product name include
     * @param inStock String as parameter to Filter if quantity in stock > inStock
     * @return int as result
     * @throws DaoException
     */
    int countBySearchParam(int brandId, int typeId, int minPrice, int maxPrice, int minPower, int maxPower, String searchWord, int inStock) throws DaoException;

    Map<Product, String> findMapProductQuantityById(String id, String id1, Map<Product, String> productQuantity) throws DaoException;

    boolean updatePhoto(int id, byte[] bytesPhoto) throws DaoException;

    boolean updateProductData(Map<String, String> productDataMap) throws DaoException;
}
