package org.aleks4ay.dao;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

public class ConnectionPool {
    private static final Logger log = LoggerFactory.getLogger(ConnectionPool.class);

    static DataSource dataSource;
    private static final String DRIVER_NAME;
    private static final String URL;
    private static final String URL_TEST;
    private static final String USER_NAME;
    private static final String PASSWORD;

    static {
        final ResourceBundle config = ResourceBundle
                .getBundle("persistence", Locale.ENGLISH);
        DRIVER_NAME = config.getString("database.driver");
        URL = config.getString("database.url");
        URL_TEST = config.getString("database.test.url");
        USER_NAME = config.getString("database.username");
        PASSWORD = config.getString("database.password");
        dataSource = initDataSource(URL);
    }

    public Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            log.error("SQLException during get Connection from resource {}. {}",
                    new File("/persistence.properties").getAbsolutePath(), e);
        }
        return null;
    }

    static DataSource initDataSource(String url) {
        ComboPooledDataSource cpds = new ComboPooledDataSource();
        try {
            cpds.setDriverClass(DRIVER_NAME);
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }
        cpds.setJdbcUrl(url);
        cpds.setUser(USER_NAME);
        cpds.setPassword(PASSWORD);
        cpds.setMinPoolSize(10);
        cpds.setAcquireIncrement(5);
        cpds.setMaxPoolSize(30);
        return cpds;
    }

    static String getUrlTest() {
        return URL_TEST;
    }

    public void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                log.debug("SQLException during close connection from {}. {}", ConnectionPool.class, e);
            }
        }
    }
}
