package org.aleks4ay.dao;

public final class ConnectionPoolTest extends ConnectionPool {
    static {
        dataSource = initDataSource(getUrlTest());
    }
}
