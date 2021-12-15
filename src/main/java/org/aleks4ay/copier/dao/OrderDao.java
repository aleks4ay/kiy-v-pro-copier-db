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
            return createTimeAll(ConstantsSql.ORDER_TIME_CREATE, list);
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

    boolean createTimeAll(String sql, List<Order> list) {
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
                log.debug("Will be save 'time' for Order: {}", order);
            }
            if (result == list.size()) {
                connection.commit();
                log.debug("time '{}' saved.", result);
                return true;
            } else {
                log.debug("Saved {}, but need to Save {} 'time' for Order. Not equals!!!", result, list.size());
                connection.rollback();
                return false;
            }
        } catch (SQLException e) {
            log.warn("Exception during saving {} 'time' for Order. SQL = {}.", list.size(), sql, e);
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