package by.makei.shop.model.dao.impl;

import by.makei.shop.exception.DaoException;
import by.makei.shop.model.connectionpool.DbConnectionPool;
import by.makei.shop.model.connectionpool.ProxyConnection;
import by.makei.shop.model.dao.ProductDao;
import by.makei.shop.model.dao.mapper.impl.BrandMapper;
import by.makei.shop.model.dao.mapper.impl.TypeMapper;
import by.makei.shop.model.entity.Product;
import by.makei.shop.model.entity.ProductType;
import org.apache.logging.log4j.Level;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductTypeDaoImpl implements ProductDao {

    private static final String SQL_SELECT_TYPE_BY_VAR_PARAM = """
            SELECT id, type_name FROM lightingshop.types WHERE %s = ?""";

    private static final String SQL_SELECT_ALL_TYPES = """
            SELECT id, type_name FROM lightingshop.types""";

    public Optional<ProductType> findTypeByOneParam(String paramName, String paramValue) throws DaoException {
        if (!paramName.matches(PARAMETER_VALIDATOR_PATTERN)) {
            logger.log(Level.ERROR, "findBrandByOneParam incorrect paramName");
            throw new DaoException("findTypeByOneParam incorrect paramName");
        }
        Connection connection = null;
        ProxyConnection proxyConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Optional<ProductType> optionalType = Optional.empty();
        String fullQuery = SQL_SELECT_TYPE_BY_VAR_PARAM + "WHERE " + paramName + " = ?";
        try {
            connection = DbConnectionPool.getInstance().takeConnection();
            preparedStatement =
                    connection.prepareStatement(String.format(SQL_SELECT_TYPE_BY_VAR_PARAM, paramName));
            proxyConnection = (ProxyConnection) connection;
            preparedStatement.setString(1, paramValue.toLowerCase());
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                optionalType = new TypeMapper().mapEntity(resultSet);
                logger.log(Level.INFO, "product type was found by param {} and param value {}", paramName, paramValue);
            }
        } catch (SQLException e) {
            if (proxyConnection != null) {
                proxyConnection.setForChecking(true);
            }
            logger.log(Level.ERROR, "error while findTypeByOneParam");
            throw new DaoException(e);
        } finally {
            finallyWhileClosing(connection, preparedStatement, resultSet);
        }
        return optionalType;
    }

    public List<ProductType> findAllTypes() throws DaoException {
        Connection connection = null;
        ProxyConnection proxyConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Optional<ProductType> optionalType = Optional.empty();
        List<ProductType> resultList = new ArrayList<>();
        try {
            connection = DbConnectionPool.getInstance().takeConnection();
            preparedStatement =
                    connection.prepareStatement(SQL_SELECT_ALL_TYPES);
            proxyConnection = (ProxyConnection) connection;
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                optionalType = new TypeMapper().mapEntity(resultSet);
                if (optionalType.isPresent()) {
                    resultList.add(optionalType.get());
                }
                logger.log(Level.DEBUG, "ProductTypeDao findAllTypes was found");
            }
        } catch (SQLException e) {
            if (proxyConnection != null) {
                proxyConnection.setForChecking(true);
            }
            logger.log(Level.ERROR, "error while findAllBrands");
            throw new DaoException(e);
        } finally {
            finallyWhileClosing(connection, preparedStatement, resultSet);
        }
        return resultList;
    }


    @Override
    public List<Product> findAll() throws DaoException {
        return null;
    }

    @Override
    public Product findEntityById(int id) throws DaoException {
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

    private void finallyWhileClosing(Connection connection, PreparedStatement preparedStatement, ResultSet
            resultSet) {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "error while closing. {}", e.getMessage());
        }
    }
}
