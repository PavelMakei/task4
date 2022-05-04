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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class ProductDaoImpl implements ProductDao {
    private static final String SQL_ADD_NEW_PRODUCT = """
            INSERT INTO lightingshop.products (brand_id, type_id, product_name, description, price, colour, power, size, photo, quantity_in_stock)
            values (?,?,?,?,?,?,?,?,?,?)""";

    private static final String SQL_SELECT_PRODUCT_BY_VAR_PARAM = """
            SELECT id, brand_id, type_id, product_name, description, price, colour, power, size, photo, quantity_in_stock
            FROM lightingshop.products WHERE %s = ?""";

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


    public boolean create(Product product, int quantity) throws DaoException {
        ProxyConnection proxyConnection = null;
        PreparedStatement preparedStatement = null;
        InputStream photoStream = null;

        try {
            photoStream = new ByteArrayInputStream(product.getPhoto());

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
            preparedStatement.setInt(10, quantity);
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
        return null;
    }


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

//    private void finallyWhileClosing(Connection connection, PreparedStatement preparedStatement, ResultSet
//            resultSet) {
//        try {
//            if (resultSet != null) {
//                resultSet.close();
//            }
//            if (preparedStatement != null) {
//                preparedStatement.close();
//            }
//            if (connection != null) {
//                connection.close();
//            }
//        } catch (SQLException e) {
//            logger.log(Level.ERROR, "error while closing. {}", e.getMessage());
//        }
//    }
}
