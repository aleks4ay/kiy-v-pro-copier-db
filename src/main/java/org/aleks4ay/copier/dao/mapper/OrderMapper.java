package org.aleks4ay.copier.dao.mapper;

import org.aleks4ay.copier.model.Order;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class OrderMapper implements ObjectMapper<Order> {
    @Override
    public Order extractFromResultSet(ResultSet rs) throws SQLException {
        return new Order(
                rs.getString("id"),
                rs.getString("id_client"),
                rs.getString("id_manager"),
                rs.getInt("duration"),
                rs.getTimestamp("t_factory"),
                rs.getDouble("price")
        );
    }

    @Override
    public void insertToResultSet(PreparedStatement statement, Order order) throws SQLException {
        statement.setString(6, order.getId());
        statement.setString(1, order.getClientId());
        statement.setString(2, order.getManagerId());
        statement.setInt(3, order.getDurationTime());
        statement.setTimestamp(4, order.getDateToFactory());
        statement.setDouble(5, order.getPrice());
    }

    public void insertToResultSetStatus(PreparedStatement statement, Order order) throws SQLException {
        statement.setString(1, "NEW");
        statement.setString(2, order.getId());
    }

    public void insertToResultSetTime(PreparedStatement statement, Order order) throws SQLException {
        statement.setString(1, order.getId());
        statement.setString(2, "NEW");
        statement.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
    }

    @Override
    public void insertIdToResultSet(PreparedStatement statement, Order order) throws SQLException {
        statement.setString(1, order.getId());
    }
}