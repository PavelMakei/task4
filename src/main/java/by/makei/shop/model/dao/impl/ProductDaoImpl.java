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

import static by.makei.shop.model.command.AttributeName.QUANTITY_IN_STOCK;

public class ProductDaoImpl implements ProductDao {
    private static final String FIND_ANY = "%";
    private static final String SQL_ADD_NEW_PRODUCT = """
            INSERT INTO lightingshop.products (brand_id, type_id, product_name, description, price, colour, power, size, photo, quantity_in_stock)
            values (?,?,?,?,?,?,?,?,?,?)""";

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
                logger.log(Level.INFO, "product was found by param {} and param value {}", paramName, paramValue);
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
    public Map<Product, String> findBySearchParam(int brandId, int typeId, int minPrice, int maxPrice, int minPower, int maxPower) throws DaoException {
        ProxyConnection proxyConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Optional<Product> optionalProduct = Optional.empty();
        Map<Product, String> resultMap = new HashMap<>();

        try {
            proxyConnection = DbConnectionPool.getInstance().takeConnection();
            preparedStatement = proxyConnection.prepareStatement(SQL_SELECT_PRODUCTS_BY_PARAMS);
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
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                optionalProduct = new ProductMapper().mapEntity(resultSet);
                if (optionalProduct.isPresent()) {
                    resultMap.put(optionalProduct.get(), resultSet.getString(QUANTITY_IN_STOCK));
                }
                logger.log(Level.DEBUG, "ProductDao findBeSearchParam product was found");
            }
        } catch (SQLException e) {
            proxyConnection.setForChecking(true);
            logger.log(Level.ERROR, "error while findBeSearchParam");
            throw new DaoException(e);
        } finally {
            finallyWhileClosing(proxyConnection, preparedStatement, resultSet);
        }
        return resultMap;
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
