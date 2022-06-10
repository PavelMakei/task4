package by.makei.shop.model.dao;

import by.makei.shop.exception.DaoException;
import by.makei.shop.model.connectionpool.ProxyConnection;
import by.makei.shop.model.entity.AbstractEntity;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.List;
import java.util.Optional;


public interface BaseDao< T extends AbstractEntity> {
    Logger logger = LogManager.getLogger();
    String PARAMETER_VALIDATOR_PATTERN = "[a-z_]+";

    Optional<T> findEntityByOneParam(String paramName, String paramValue) throws DaoException;

    List<T> findAll()throws DaoException;

    boolean delete(T entity)throws DaoException;

    boolean delete(int id)throws DaoException;

    boolean create(T entity)throws DaoException;

    T update(T entity)throws DaoException;

    default void close(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                logger.log(Level.ERROR, "statement was not closed. {}", e.getMessage());
                //TODO need additional action?
            }
        }
    }
    default void finallyWhileClosing(ProxyConnection proxyConnection, PreparedStatement preparedStatement, ResultSet...
            resultSets) {
        try {
            if (resultSets.length > 0 && resultSets[0] != null) {
                resultSets[0].close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (proxyConnection != null) {
                proxyConnection.close();
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Dao error while closing resources. {}", e.getMessage());
        }
    }

}
