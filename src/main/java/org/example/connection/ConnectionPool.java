package org.example.connection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.*;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.locks.ReentrantLock;

public class ConnectionPool
{
    private static final Logger logger = LogManager.getLogger(ConnectionPool.class);
    private static final String PROPERTY_PATH = "db";
    private static final int INITIAL_CAPACITY = 5;
    private ArrayBlockingQueue<Connection> freeConnections = new ArrayBlockingQueue<>(INITIAL_CAPACITY);
    private ArrayBlockingQueue<Connection> releaseConnections = new ArrayBlockingQueue<>(INITIAL_CAPACITY);
    private static ReentrantLock lock = new ReentrantLock();
    private volatile static ConnectionPool connectionPool;



    private static ConnectionPool getInstance()
    {
        try
        {
            lock.lock();
            if(connectionPool == null)
            {
                connectionPool = new ConnectionPool();
            }
        }catch(Exception exception)
        {
            logger.error("Не удалось получить соединение ",exception);
            throw new RuntimeException();
        } finally {
            lock.unlock();
        }
        return connectionPool;
    }


    private void init()
    {
        Properties properties = new Properties();
        ResourceBundle resource = ResourceBundle.getBundle(PROPERTY_PATH, Locale.getDefault());
        if(resource == null)
        {
            logger.error("Ошибка чтения свойств");
        }
        else
        {
            String connerctionURL = resource.getString("db.url");
            String initCapacityStr = resource.getString("db.poolsize");
            String user = resource.getString("db.user");
            String password = resource.getString("db.password");
            Integer initCapacity = Integer.valueOf(initCapacityStr);

            for(int i = 0; i < initCapacity; i++)
            {
                try
                {
                    Connection connection = DriverManager.getConnection(connerctionURL, user, password);

                } catch (SQLException e)
                {
                    logger.error("Пул не может быть проинициализирован");
                    throw new RuntimeException("Пул не может быть проинициализирован", e);
                }
            }
        }
    }

    public Connection getConnection()
    {
        try
        {
            Connection connection = freeConnections.take();
            releaseConnections.offer(connection);
            logger.info("Соединение было установлено. Количество свободных соединений: " + freeConnections.size());
            return connection;
        } catch (InterruptedException e) {
            throw new RuntimeException("Невозможно найти БД",e);
        }
    }



    public void destroy()
    {
        for(int i = 0; i < freeConnections.size(); i++)
        {
            try
            {
                Connection connection = (Connection) freeConnections.take();
                connection.close();
            } catch (InterruptedException e) {
                throw new RuntimeException("Ошибка закрытия соеднения ",e);
            } catch (SQLException e) {
                throw new RuntimeException("БД не закрыта! ",e);
            }
        }
        try
        {
            Enumeration<Driver> drivers = DriverManager.getDrivers();
            while(drivers.hasMoreElements())
            {
                Driver driver = drivers.nextElement();
                DriverManager.deregisterDriver(driver);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Драйвер не был закрыт ",e);
        }
    }

}
