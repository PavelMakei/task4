package by.makei.shop.model.dao.impl;

import by.makei.shop.exception.DaoException;
import by.makei.shop.model.connectionpool.DbConnectionPool;
import by.makei.shop.model.connectionpool.ProxyConnection;
import by.makei.shop.model.dao.ProductDao;
import by.makei.shop.model.dao.mapper.impl.ProductMapper;
import by.makei.shop.model.entity.Product;
import org.apache.logging.log4j.Level;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import static by.makei.shop.model.command.AttributeName.ID;
import static by.makei.shop.model.command.AttributeName.QUANTITY_IN_STOCK;

public class ProductDaoImpl implements ProductDao {
    private static final String FIND_ANY = "%";
    private static final String TOTAL = "total";
    private static final String SQL_ADD_NEW_PRODUCT = """
            INSERT INTO lightingshop.products (brand_id, type_id, product_name, description, price, colour, power, size, photo, quantity_in_stock)
            values (?,?,?,?,?,?,?,?,?,?)""";

    private static final String SQL_UPDATE_PHOTO = """
            UPDATE lightingshop.products
            SET photo =?
            WHERE id =?
            """;

    private static final String SQL_SELECT_PRODUCT_BY_VAR_PARAM = """
            SELECT id, brand_id, type_id, product_name, description, price, colour, power, size, photo, quantity_in_stock
            FROM lightingshop.products WHERE %s = ?""";

    private static final String SQL_SELECT_ALL_PRODUCTS = """
            SELECT id, brand_id, type_id, product_name, description, price, colour, power, size, photo, quantity_in_stock FROM lightingshop.products""";

    private static final String SQL_SELECT_PRODUCTS_BY_PARAMS = """
            SELECT id, brand_id, type_id, product_name, description, price, colour, power, size, photo, quantity_in_stock FROM lightingshop.products
            WHERE brand_id LIKE ?
            AND type_id LIKE ?
            AND price BETWEEN ? AND ?
            AND power BETWEEN ? AND ?
            AND product_name LIKE '%s'
            AND quantity_in_stock >=?
            ORDER BY %s
            LIMIT ?,?
            """;

    private static final String SQL_COUNT_PRODUCTS_BY_PARAMS = """
            SELECT COUNT(id) AS total FROM lightingshop.products
            WHERE brand_id LIKE ?
            AND type_id LIKE ?
            AND price BETWEEN ? AND ?
            AND power BETWEEN ? AND ?
            AND product_name LIKE '%s'
            AND quantity_in_stock >=?
            """;


    @Override
    public Optional<Product> findEntityByOneParam(String paramName, String paramValue) throws DaoException {
        if (paramName != null && !paramName.matches(PARAMETER_VALIDATOR_PATTERN)) {
            logger.log(Level.ERROR, "findProductByOneParam incorrect paramName");
            throw new DaoException("findProductByOneParam incorrect paramName");
        }
        ProxyConnection proxyConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Optional<Product> optionalProduct = Optional.empty();
        try {
            proxyConnection = DbConnectionPool.getInstance().takeConnection();
            preparedStatement =
                    proxyConnection.prepareStatement(String.format(SQL_SELECT_PRODUCT_BY_VAR_PARAM, paramName));
            preparedStatement.setString(1, paramValue);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                optionalProduct = new ProductMapper().mapEntity(resultSet);
                logger.log(Level.DEBUG, "product was found by param {} and param value {}", paramName, paramValue);
            }

        } catch (SQLException e) {
            proxyConnection.setForChecking(true);
            logger.log(Level.ERROR, "ProductDao error while findProductByOneParam");
            throw new DaoException(e);
        } finally {
            finallyWhileClosing(proxyConnection, preparedStatement, resultSet);
        }
        return optionalProduct;
    }


    public boolean create(Product product, byte[] photo, int quantityInSock) throws DaoException {
        ProxyConnection proxyConnection = null;
        PreparedStatement preparedStatement = null;
        InputStream photoStream = null;

        try {
            photoStream = new ByteArrayInputStream(photo);

            proxyConnection = DbConnectionPool.getInstance().takeConnection();
            preparedStatement = proxyConnection.prepareStatement(SQL_ADD_NEW_PRODUCT);
            preparedStatement.setInt(1, product.getBrandId());
            preparedStatement.setInt(2, product.getTypeId());
            preparedStatement.setString(3, product.getProductName());
            preparedStatement.setString(4, product.getDescription());
            preparedStatement.setDouble(5, product.getPrice());
            preparedStatement.setString(6, product.getColour());
            preparedStatement.setInt(7, product.getPower());
            preparedStatement.setString(8, product.getSize());
            preparedStatement.setBlob(9, photoStream);
            preparedStatement.setInt(10, quantityInSock);
            preparedStatement.execute();
        } catch (SQLException e) {
            logger.log(Level.ERROR, "ProductDao error while create new product. {}", e.getMessage());
            proxyConnection.setForChecking(true);
            throw new DaoException("ProductDao error while create new product", e);
        } finally {
            finallyWhileClosing(proxyConnection, preparedStatement);
            try {
                if (photoStream != null) {
                    photoStream.close();
                }
            } catch (IOException e) {
                logger.log(Level.ERROR, "ProductDao error while create new product closing resources. {}", e.getMessage());
            }
        }
        return true;
    }

    @Override
    public List<Product> findAll() throws DaoException {
        ProxyConnection proxyConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Optional<Product> optionalProduct = Optional.empty();
        List<Product> resultList = new ArrayList<>();

        try {
            proxyConnection = DbConnectionPool.getInstance().takeConnection();
            preparedStatement = proxyConnection.prepareStatement(SQL_SELECT_ALL_PRODUCTS);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                optionalProduct = new ProductMapper().mapEntity(resultSet);
                optionalProduct.ifPresent(resultList::add);
                logger.log(Level.DEBUG, "ProductDao findAllProducts was found");
            }
        } catch (SQLException e) {
            proxyConnection.setForChecking(true);
            logger.log(Level.ERROR, "error while findAllProducts");
            throw new DaoException(e);
        } finally {
            finallyWhileClosing(proxyConnection, preparedStatement, resultSet);
        }
        return resultList;
    }

    public Map<Product, String> findAllMap() throws DaoException {
        ProxyConnection proxyConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Optional<Product> optionalProduct = Optional.empty();
        Map<Product, String> resultMap = new HashMap<>();

        try {
            proxyConnection = DbConnectionPool.getInstance().takeConnection();
            preparedStatement = proxyConnection.prepareStatement(SQL_SELECT_ALL_PRODUCTS);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                optionalProduct = new ProductMapper().mapEntity(resultSet);
                if (optionalProduct.isPresent()) {
                    resultMap.put(optionalProduct.get(), resultSet.getString(QUANTITY_IN_STOCK));
                }
                logger.log(Level.DEBUG, "ProductDao findAllProducts was found");
            }
        } catch (SQLException e) {
            proxyConnection.setForChecking(true);
            logger.log(Level.ERROR, "error while findAllProducts");
            throw new DaoException(e);
        } finally {
            finallyWhileClosing(proxyConnection, preparedStatement, resultSet);
        }
        return resultMap;
    }

    @Override
    public Map<Product, String> findBySearchParam(int brandId, int typeId, int minPrice, int maxPrice,
                                                  int minPower, int maxPower, int searchFrom, int searchTo,
                                                  String searchWord, String orderQuery, int inStock) throws DaoException {
        ProxyConnection proxyConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Optional<Product> optionalProduct = Optional.empty();
        Map<Product, String> resultMap = new LinkedHashMap<>();

        try {
            proxyConnection = DbConnectionPool.getInstance().takeConnection();
            preparedStatement =
                    proxyConnection.prepareStatement(String.format(SQL_SELECT_PRODUCTS_BY_PARAMS, FIND_ANY + searchWord + FIND_ANY, orderQuery));
            if (brandId == 0) {
                preparedStatement.setString(1, FIND_ANY);
            } else {
                preparedStatement.setInt(1, brandId);
            }
            if (typeId == 0) {
                preparedStatement.setString(2, FIND_ANY);
            } else {
                preparedStatement.setInt(2, typeId);
            }
            preparedStatement.setInt(3, minPrice);
            preparedStatement.setInt(4, maxPrice);
            preparedStatement.setInt(5, minPower);
            preparedStatement.setInt(6, maxPower);
            preparedStatement.setInt(7, inStock);
            preparedStatement.setInt(8, searchFrom);
            preparedStatement.setInt(9, searchTo);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                optionalProduct = new ProductMapper().mapEntity(resultSet);
                if (optionalProduct.isPresent()) {
                    resultMap.put(optionalProduct.get(), resultSet.getString(QUANTITY_IN_STOCK));
                }
                logger.log(Level.DEBUG, "ProductDao findBySearchParam product was found");
            }
        } catch (SQLException e) {
            proxyConnection.setForChecking(true);
            logger.log(Level.ERROR, "error while findBySearchParam");
            throw new DaoException(e);
        } finally {
            finallyWhileClosing(proxyConnection, preparedStatement, resultSet);
        }
        return resultMap;
    }

    @Override
    public int countBySearchParam(int brandId, int typeId, int minPrice, int maxPrice, int minPower, int maxPower, String searchWord, int inStock) throws DaoException {
        ProxyConnection proxyConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int result = 0;

        try {
            proxyConnection = DbConnectionPool.getInstance().takeConnection();
            preparedStatement =
                    proxyConnection.prepareStatement(String.format(SQL_COUNT_PRODUCTS_BY_PARAMS, FIND_ANY + searchWord + FIND_ANY));
            if (brandId == 0) {
                preparedStatement.setString(1, FIND_ANY);
            } else {
                preparedStatement.setInt(1, brandId);
            }
            if (typeId == 0) {
                preparedStatement.setString(2, FIND_ANY);
            } else {
                preparedStatement.setInt(2, typeId);
            }
            preparedStatement.setInt(3, minPrice);
            preparedStatement.setInt(4, maxPrice);
            preparedStatement.setInt(5, minPower);
            preparedStatement.setInt(6, maxPower);
            preparedStatement.setInt(7, inStock);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                result = resultSet.getInt(TOTAL);
            }
        } catch (SQLException e) {
            proxyConnection.setForChecking(true);
            logger.log(Level.ERROR, "error while countBySearchParam");
            throw new DaoException(e);
        } finally {
            finallyWhileClosing(proxyConnection, preparedStatement, resultSet);
        }
        return result;
    }

    @Override
    public Map<Product, String> findMapProductQuantityById(String paramName, String paramValue) throws DaoException {

        if (paramName != null && !paramName.matches(PARAMETER_VALIDATOR_PATTERN)) {
            logger.log(Level.ERROR, "findMapProductQuantityByIdincorrect paramName");
            throw new DaoException("findMapProductQuantityById incorrect paramName");
        }
        ProxyConnection proxyConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Optional<Product> optionalProduct = Optional.empty();
        Map<Product, String> productQuantity = new HashMap<>();
        try {
            proxyConnection = DbConnectionPool.getInstance().takeConnection();
            preparedStatement =
                    proxyConnection.prepareStatement(String.format(SQL_SELECT_PRODUCT_BY_VAR_PARAM, paramName));
            preparedStatement.setString(1, paramValue);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                optionalProduct = new ProductMapper().mapEntity(resultSet);
                if (optionalProduct.isPresent()) {
                    productQuantity.put(optionalProduct.get(), resultSet.getString(QUANTITY_IN_STOCK));
                }
                logger.log(Level.DEBUG, "product was found by param {} and param value {}", paramName, paramValue);
            }
        } catch (SQLException e) {
            proxyConnection.setForChecking(true);
            logger.log(Level.ERROR, "ProductDao error while findMapProductQuantityById");
            throw new DaoException(e);
        } finally {
            finallyWhileClosing(proxyConnection, preparedStatement, resultSet);
        }
        return productQuantity;
    }

    @Override
    public boolean updatePhoto(int id, byte[] bytesPhoto) throws DaoException {
        ProxyConnection proxyConnection = null;
        PreparedStatement preparedStatement = null;
        InputStream photoStream = null;
        int result = 0;

        try {
            photoStream = new ByteArrayInputStream(bytesPhoto);
            proxyConnection = DbConnectionPool.getInstance().takeConnection();
            preparedStatement = proxyConnection.prepareStatement(SQL_UPDATE_PHOTO);
            preparedStatement.setBlob(1, photoStream);
            preparedStatement.setInt(2, id);
            result = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.ERROR, "ProductDao error while updatePhoto. {}", e.getMessage());
            proxyConnection.setForChecking(true);
            throw new DaoException("ProductDao error while updatePhoto", e);
        } finally {
            finallyWhileClosing(proxyConnection, preparedStatement);
            try {
                if (photoStream != null) {
                    photoStream.close();
                }
            } catch (IOException e) {
                logger.log(Level.ERROR, "ProductDao error while updatePhoto closing resources. {}", e.getMessage());
            }
        }
        return result > 0;
    }

    //TODO дописать методы
    @Override
    public boolean delete(Product entity) throws DaoException {
        return false;
    }

    @Override
    public boolean delete(int id) throws DaoException {
        return false;
    }

    @Override
    public boolean create(Product entity) throws DaoException {
        return false;
    }

    @Override
    public Product update(Product entity) throws DaoException {
        return null;
    }

}
