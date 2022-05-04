package by.makei.shop.model.dao.impl;

import by.makei.shop.exception.DaoException;
import by.makei.shop.model.connectionpool.DbConnectionPool;
import by.makei.shop.model.connectionpool.ProxyConnection;
import by.makei.shop.model.dao.ProductTypeDao;
import by.makei.shop.model.dao.mapper.impl.TypeMapper;
import by.makei.shop.model.entity.ProductType;
import org.apache.logging.log4j.Level;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductTypeDaoImpl implements ProductTypeDao {

    private static final String SQL_SELECT_TYPE_BY_VAR_PARAM = """
            SELECT id, type_name FROM lightingshop.types WHERE %s = ?""";

    private static final String SQL_SELECT_ALL_TYPES = """
            SELECT id, type_name FROM lightingshop.types""";

    @Override
    public Optional<ProductType> findEntityByOneParam(String paramName, String paramValue) throws DaoException {
        if (paramName != null && !paramName.matches(PARAMETER_VALIDATOR_PATTERN)) {
            logger.log(Level.ERROR, "findBrandByOneParam incorrect paramName");
            throw new DaoException("findTypeByOneParam incorrect paramName");
        }
        ProxyConnection proxyConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Optional<ProductType> optionalType = Optional.empty();
        try {
            proxyConnection = DbConnectionPool.getInstance().takeConnection();
            preparedStatement =
                    proxyConnection.prepareStatement(String.format(SQL_SELECT_TYPE_BY_VAR_PARAM, paramName));
            preparedStatement.setString(1, paramValue.toLowerCase());
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                optionalType = new TypeMapper().mapEntity(resultSet);
                logger.log(Level.INFO, "product type was found by param {} and param value {}", paramName, paramValue);
            }
        } catch (SQLException e) {
            proxyConnection.setForChecking(true);
            logger.log(Level.ERROR, "error while findTypeByOneParam");
            throw new DaoException(e);
        } finally {
            finallyWhileClosing(proxyConnection, preparedStatement, resultSet);
        }
        return optionalType;
    }

    @Override
    public List<ProductType> findAll() throws DaoException {
        ProxyConnection proxyConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Optional<ProductType> optionalType = Optional.empty();
        List<ProductType> resultList = new ArrayList<>();
        try {
            proxyConnection = DbConnectionPool.getInstance().takeConnection();
            preparedStatement =
                    proxyConnection.prepareStatement(SQL_SELECT_ALL_TYPES);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                optionalType = new TypeMapper().mapEntity(resultSet);
                optionalType.ifPresent(resultList::add);
                logger.log(Level.DEBUG, "ProductTypeDao findAllTypes was found");
            }
        } catch (SQLException e) {
                proxyConnection.setForChecking(true);
            logger.log(Level.ERROR, "error while findAllBrands");
            throw new DaoException(e);
        } finally {
            finallyWhileClosing(proxyConnection, preparedStatement, resultSet);
        }
        return resultList;
    }


    @Override
    public boolean delete(ProductType entity) throws DaoException {
        return false;
    }

    @Override
    public boolean delete(int id) throws DaoException {
        return false;
    }

    @Override
    public boolean create(ProductType entity) throws DaoException {
        return false;
    }

    @Override
    public ProductType update(ProductType entity) throws DaoException {
        return null;
    }

}
