package by.makei.shop.model.dao.impl;

import by.makei.shop.exception.DaoException;
import by.makei.shop.model.connectionpool.DbConnectionPool;
import by.makei.shop.model.connectionpool.ProxyConnection;
import by.makei.shop.model.dao.BrandDao;
import by.makei.shop.model.dao.mapper.impl.BrandMapper;
import by.makei.shop.model.entity.Brand;
import org.apache.logging.log4j.Level;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class BrandDaoImpl implements BrandDao {
    private static final BrandDaoImpl instance = new BrandDaoImpl();
    private static final String SQL_SELECT_BRAND_BY_VAR_PARAM = """
            SELECT id, brand_name FROM lightingshop.brands WHERE %s = ?""";
    private static final String SQL_SELECT_ALL_BRANDS = """
            SELECT id, brand_name FROM lightingshop.brands""";

    private BrandDaoImpl(){}

    public static BrandDaoImpl getInstance() {
        return instance;
    }

    @Override
    public Optional<Brand> findEntityByOneParam(String paramName, String paramValue) throws DaoException {
        if (paramName != null && !paramName.matches(PARAMETER_VALIDATOR_PATTERN)) {
            logger.log(Level.ERROR, "findBrandByOneParam incorrect paramName");
            throw new DaoException("findBrandByOneParam incorrect paramName");
        }
        ProxyConnection proxyConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Optional<Brand> optionalBrand = Optional.empty();
        try {
            proxyConnection = DbConnectionPool.getInstance().takeConnection();
            preparedStatement =
                    proxyConnection.prepareStatement(String.format(SQL_SELECT_BRAND_BY_VAR_PARAM, paramName));
            preparedStatement.setString(1, paramValue);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                optionalBrand = new BrandMapper().mapEntity(resultSet);
                logger.log(Level.INFO, "brand was found by param {} and param value {}", paramName, paramValue);
            }
        } catch (SQLException e) {
            proxyConnection.setForChecking(true);
            logger.log(Level.ERROR, "error while findBrandByOneParam");
            throw new DaoException(e);
        } finally {
            finallyWhileClosing(proxyConnection, preparedStatement, resultSet);
        }
        return optionalBrand;
    }

    @Override
    public List<Brand> findAll() throws DaoException {
        ProxyConnection proxyConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Optional<Brand> optionalBrand = Optional.empty();
        List<Brand> resultList = new ArrayList<>();
        try {
            proxyConnection = DbConnectionPool.getInstance().takeConnection();
            preparedStatement =
                    proxyConnection.prepareStatement(SQL_SELECT_ALL_BRANDS);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                optionalBrand = new BrandMapper().mapEntity(resultSet);
                if (optionalBrand.isPresent()) {
                    resultList.add(optionalBrand.get());
                }
                logger.log(Level.DEBUG, "brand was found by param");
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

    interface Action<R> {
        R execute(Connection connection, PreparedStatement preparedStatement, ResultSet resultSet) throws SQLException;
    }

   public <R> R withException(Action<R> action, String query) throws DaoException {
        ProxyConnection proxyConnection = DbConnectionPool.getInstance().takeConnection();
        try (proxyConnection;
             PreparedStatement preparedStatement = proxyConnection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery())
        {
            try {
                return action.execute(proxyConnection, preparedStatement, resultSet);
            } catch (SQLException e) {
                proxyConnection.setForChecking(true);
                throw e;
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "error while findAllBrands");
            throw new DaoException(e);
        }
    }


    @Override
    public boolean delete(Brand entity) throws DaoException {
        return false;
    }

    @Override
    public boolean delete(int id) throws DaoException {
        return false;
    }

    @Override
    public boolean create(Brand entity) throws DaoException {
        return false;
    }

    @Override
    public Brand update(Brand entity) throws DaoException {
        return null;
    }

}