package org.example.connection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.locks.ReentrantLock;

public class ConnectionPool {
    private static final Logger logger = LogManager.getLogger(ConnectionPool.class);
    private static final String PROPERTY_PATH = "db";

    private final ArrayBlockingQueue<Connection> freeConnections;
    private final ArrayBlockingQueue<Connection> releaseConnections;

    private static final ReentrantLock lock = new ReentrantLock();
    private static volatile ConnectionPool connectionPool;

    public static ConnectionPool getInstance() {
        try {
            lock.lock();
            if (connectionPool == null) {
                connectionPool = new ConnectionPool();
            }
        } catch (Exception e) {
            logger.error("Не удалось получить экземпляр ConnectionPool", e);
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
        return connectionPool;
    }

    private ConnectionPool() {
        ResourceBundle resource = ResourceBundle.getBundle(PROPERTY_PATH, Locale.getDefault());
        int poolSize = Integer.parseInt(resource.getString("db.poolsize"));

        freeConnections = new ArrayBlockingQueue<>(poolSize);
        releaseConnections = new ArrayBlockingQueue<>(poolSize);

        init();
    }

    private void init() {
        ResourceBundle resource = ResourceBundle.getBundle(PROPERTY_PATH, Locale.getDefault());

        String url = resource.getString("db.url");
        String user = resource.getString("db.user");
        String password = resource.getString("db.password");
        int poolSize = Integer.parseInt(resource.getString("db.poolsize"));

        for (int i = 0; i < poolSize; i++) {
            try {
                Connection connection = DriverManager.getConnection(url, user, password);
                freeConnections.add(connection);
            } catch (SQLException e) {
                logger.error("Пул не может быть проинициализирован", e);
                throw new RuntimeException("Пул не может быть проинициализирован", e);
            }
        }
    }

    public Connection getConnection() {
        try {
            Connection connection = freeConnections.take();
            releaseConnections.offer(connection);
            logger.info("Соединение взято. Свободных соединений: " + freeConnections.size());
            return connection;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Невозможно получить соединение", e);
        }
    }

    public void releaseConnection(Connection connection) {
        if (connection == null) {
            return;
        }

        releaseConnections.remove(connection);
        freeConnections.offer(connection);
        logger.info("Соединение возвращено. Свободных соединений: " + freeConnections.size());
    }

    public void destroy() {
        closeQueue(freeConnections);
        closeQueue(releaseConnections);

        try {
            Enumeration<Driver> drivers = DriverManager.getDrivers();
            while (drivers.hasMoreElements()) {
                DriverManager.deregisterDriver(drivers.nextElement());
            }
        } catch (SQLException e) {
            throw new RuntimeException("Драйвер не был закрыт", e);
        }
    }

    private void closeQueue(ArrayBlockingQueue<Connection> queue) {
        while (!queue.isEmpty()) {
            try {
                Connection connection = queue.take();
                connection.close();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException("Ошибка закрытия соединения", e);
            } catch (SQLException e) {
                throw new RuntimeException("БД не закрыта", e);
            }
        }
    }
}