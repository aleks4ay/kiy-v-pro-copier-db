package org.aleks4ay.copier.dao;

import org.aleks4ay.copier.dao.mapper.OrderMapper;
import org.aleks4ay.copier.model.Order;
import org.aleks4ay.copier.tools.ConstantsSql;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.*;
import java.util.stream.IntStream;

public class OrderDao extends AbstractDao<Order> implements BaseDao<Order> {
    private static final Logger log = LoggerFactory.getLogger(OrderDao.class);

    public OrderDao(ConnectionPool connectionPool) {
        super(new OrderMapper(), connectionPool);
    }

    @Override
    public Optional<Order> findById(String id) throws SQLException {
        return findAbstractById(ConstantsSql.ORDER_GET_ONE, id);
    }

    @Override
    public List<Order> findAll() {
        return findAbstractAll(ConstantsSql.ORDER_GET_ALL);
    }

    @Override
    public boolean createAll(List<Order> list) {
        boolean resultCreateOrder = saveOrUpdateAbstractAll(ConstantsSql.ORDER_CREATE, list, "Save");
        if (resultCreateOrder) {
            return createStatusOrTimeAll(ConstantsSql.ORDER_STATUS_CREATE, list, "status")
                    && createStatusOrTimeAll(ConstantsSql.ORDER_TIME_CREATE, list, "time");
        }
        return false;
    }

    @Override
    public boolean updateAll(List<Order> list) {
        return saveOrUpdateAbstractAll(ConstantsSql.ORDER_UPDATE, list, "Update");
    }

    @Override
    public boolean deleteAll(Collection<Order> list) {
        return deleteAbstractAll(ConstantsSql.ORDER_DELETE, list);
    }

    @Override
    public String getEntityName() {
        return "Order";
    }


    public List<String> findAllId() {
        Connection connection = getConnection();
        try (Statement st = connection.createStatement()){
            List<String> entities = new ArrayList<>();
            ResultSet rs = st.executeQuery(ConstantsSql.ORDER_GET_ALL_ID);
            while (rs.next()) {
                entities.add(rs.getString("id"));
            }
            return entities;
        } catch (SQLException e) {
            log.warn("Exception during reading 'Order.id'. Sql: '{}'.", ConstantsSql.ORDER_GET_ALL_ID, e);
            return Collections.emptyList();
        } finally {
            closeConnection(connection);
        }
    }
/*
    boolean createStatusAll(List<Order> orders) {
        if (orders.isEmpty()) {
            return true;
        }
        Connection connection = getConnection();
        try (PreparedStatement prepStatement = connection.prepareStatement(ConstantsSql.ORDER_STATUS_CREATE)) {
            connection.setAutoCommit(false);
            int result = 0;
            for (Order order : orders) {
                ((OrderMapper)objectMapper).insertToResultSetStatus(prepStatement, order);
                prepStatement.addBatch();
                int[] numberOfUpdates = prepStatement.executeBatch();
                result += IntStream.of(numberOfUpdates).sum();
                log.debug("Will be save 'status' for Order: {}", order);
            }
            if (result == orders.size()) {
                connection.commit();
                log.debug("{} Order.Status saved.", result);
                return true;
            } else {
                log.debug("Saved {}, but need to Save {} Order.Status. Not equals!!!", result, orders.size());
                connection.rollback();
                return false;
            }
        } catch (SQLException e) {
            log.warn("Exception during saving {} 'Order.Status'. SQL = {}.", orders.size(), ConstantsSql.ORDER_STATUS_CREATE, e);
            return false;
        } finally {
            try {
                connection.setAutoCommit(true);
                closeConnection(connection);
            } catch (SQLException e) {
                log.warn("Can't close connection.", e);
            }
        }
    }

    boolean createTimeAll(List<Order> orders) {
        if (orders.isEmpty()) {
            return true;
        }
        Connection connection = getConnection();
        try (PreparedStatement prepStatement = connection.prepareStatement(ConstantsSql.ORDER_TIME_CREATE)) {
            connection.setAutoCommit(false);
            int result = 0;
            for (Order order : orders) {
                ((OrderMapper)objectMapper).insertToResultSetTime(prepStatement, order);
                prepStatement.addBatch();
                int[] numberOfUpdates = prepStatement.executeBatch();
                result += IntStream.of(numberOfUpdates).sum();
                log.debug("Will be save 'time' for Order: {}", order);
            }
            if (result == orders.size()) {
                connection.commit();
                log.debug("{} Time for order Status saved.", result);
                return true;
            } else {
                log.debug("Saved {}, but need to Save {} Time. Not equals!!!", result, orders.size());
                connection.rollback();
                return false;
            }
        } catch (SQLException e) {
            log.warn("Exception during saving {} time for 'Order.Status'. SQL = {}.", orders.size(), ConstantsSql.ORDER_TIME_CREATE, e);
            return false;
        } finally {
            try {
                connection.setAutoCommit(true);
                closeConnection(connection);
            } catch (SQLException e) {
                log.warn("Can't close connection.", e);
            }
        }
    }*/

    boolean createStatusOrTimeAll(String sql, List<Order> list, String target) {
        if (list.isEmpty()) {
            return true;
        }
        Connection connection = getConnection();
        try (PreparedStatement prepStatement = connection.prepareStatement(sql)) {
            connection.setAutoCommit(false);
            int result = 0;
            for (Order order : list) {
                if (sql.contains("order_status")) {
                    ((OrderMapper) objectMapper).insertToResultSetStatus(prepStatement, order);
                } else {
                    ((OrderMapper) objectMapper).insertToResultSetTime(prepStatement, order);
                }
                prepStatement.addBatch();
                int[] numberOfUpdates = prepStatement.executeBatch();
                result += IntStream.of(numberOfUpdates).sum();
                log.debug("Will be save '{}' for Order: {}", target, order);
            }
            if (result == list.size()) {
                connection.commit();
                log.debug("{} '{}' for saved.", target, result);
                return true;
            } else {
                log.debug("Saved {}, but need to Save {} '{}' for Order. Not equals!!!", result, list.size(), target);
                connection.rollback();
                return false;
            }
        } catch (SQLException e) {
            log.warn("Exception during saving {} '{}' for Order. SQL = {}.", list.size(), target, sql, e);
            return false;
        } finally {
            try {
                connection.setAutoCommit(true);
                closeConnection(connection);
            } catch (SQLException e) {
                log.warn("Can't close connection.", e);
            }
        }
    }
}