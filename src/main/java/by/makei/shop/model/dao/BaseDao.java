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

    /**
     * check if paramName is valid, else throw DaoException
     * @param paramName String like ("id", "name"...etc)
     * @param paramValue String as value of searched paramName like("25", "Ivan"...etc)
     * @return Optional
     * @throws DaoException
     */
    Optional<T> findEntityByOneParam(String paramName, String paramValue) throws DaoException;

    /**
     * find all entity
     * @return List of entity
     * @throws DaoException
     */
    List<T> findAll()throws DaoException;

    boolean delete(T entity)throws DaoException;

    boolean delete(int id)throws DaoException;

    /**
     * create entity
     * @param entity filled entity
     * @return boolean as result
     * @throws DaoException
     */
    boolean create(T entity)throws DaoException;

    T update(T entity)throws DaoException;

    /**
     * Check if resources != 0 and close them.
     * @param proxyConnection as {@link ProxyConnection}
     * @param preparedStatement as {@link PreparedStatement}
     * @param resultSets as {@link ResultSet} will be closed [0], can be skipped in arguments
     */
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
