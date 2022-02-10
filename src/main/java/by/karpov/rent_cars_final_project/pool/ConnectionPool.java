package by.karpov.rent_cars_final_project.pool;


import by.karpov.rent_cars_final_project.exception.DaoException;

import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ConnectionPool {
    private static final String URL = PropertiesUtil.getProperty("db.url");
    private static final String USER = PropertiesUtil.getProperty("db.username");
    private static final String PASS = PropertiesUtil.getProperty("db.password");
    private static final String PATH_JDBC_DRIVER = PropertiesUtil.getProperty("mysqlDriver");
    private static final String DB_POLL_SIZE = "db.poll.size";
    private static final int DEFAULT_POOL_SIZE = 5;
    private static Connection connection;
    private static ConnectionPool instance;
    private static final BlockingQueue<Connection> pool;
    private static final List<Connection> sourceConnection;


    static {
        try {
            Class.forName(PATH_JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        final String poolSize = PropertiesUtil.getProperty(DB_POLL_SIZE);
        int size = poolSize == null ? DEFAULT_POOL_SIZE : Integer.parseInt(poolSize);
        pool = new ArrayBlockingQueue<>(size);
        sourceConnection = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            try {
                connection = open();
            } catch (DaoException e) {
                e.printStackTrace();
            }
            Connection proxyConnection = (Connection) Proxy.newProxyInstance(ConnectionPool.class.getClassLoader(), new Class[]{Connection.class},
                    (proxy, method, args) -> method.getName().equals("close")
                            ? pool.add((Connection) proxy)
                            : method.invoke(connection, args));
            pool.add(proxyConnection);
            sourceConnection.add(connection);
        }
    }

    private ConnectionPool() {
    }

    public static ConnectionPool pool() {
        if (instance == null) {
            instance = new ConnectionPool();
        }
        return instance;
    }

    public  Connection getConnection() {
        try {
            Connection connection = pool.take();
            return connection;
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }
    }

    public  boolean closePool() throws DaoException {
        for (Connection connection : sourceConnection) {
            try {
                connection.close();
            } catch (SQLException ex) {
                throw new DaoException(ex);
            }
        }
        return true;
    }

    private static Connection open() throws DaoException {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(URL, USER, PASS);
            } catch (SQLException ex) {
                throw new DaoException(ex);
            }
        }
        return connection;
    }
}
