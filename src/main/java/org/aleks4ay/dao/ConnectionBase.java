package org.aleks4ay.dao;

import java.sql.Connection;

public interface ConnectionBase {

    Connection getConnection();
    void closeConnection(Connection conn);
}