package org.aleks4ay.copier.dao;

import java.sql.Connection;

public interface ConnectionBase {

    Connection getConnection();
    void closeConnection(Connection conn);
}