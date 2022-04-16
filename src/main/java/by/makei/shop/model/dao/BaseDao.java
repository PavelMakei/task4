package by.makei.shop.model.dao;

import by.makei.shop.exception.DaoException;
import by.makei.shop.model.entity.AbstractEntity;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public interface BaseDao< T extends AbstractEntity> {
    static final Logger logger = LogManager.getLogger();

    List<T> findAll()throws DaoException;

    T findEntityById(int id)throws DaoException;

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

    default void close(ResultSet resultSet){
        try{
            if(resultSet != null){
                resultSet.close();
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR,"result set was not closed. {}", e.getMessage());
        }
    }

}
