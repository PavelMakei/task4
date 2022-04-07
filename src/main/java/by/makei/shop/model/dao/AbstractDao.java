package by.makei.shop.model.dao;

import by.makei.shop.model.entity.AbstractEntity;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public abstract class AbstractDao< T extends AbstractEntity> {
    private static final Logger logger = LogManager.getLogger();

    public abstract List<T> findAll();

    public abstract T findEntityById(int id);

    public abstract boolean delete(T entity);

    public abstract boolean delete(int id);

    public abstract boolean create(T entity);

    public abstract T update(T entity);

    public void close(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                logger.log(Level.ERROR, "statement was not closed. {}", e.getMessage());
                //TODO need additional action?
            }

        }
    }

    public void close(ResultSet resultSet){
        try{
            if(resultSet != null){
                resultSet.close();
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR,"result set was not closed. {}", e.getMessage());
        }
    }


}
