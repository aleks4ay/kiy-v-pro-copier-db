package org.aleks4ay.dao;

import org.aleks4ay.dao.mapper.OrderMapper;
import org.aleks4ay.model.Order;
import org.aleks4ay.tools.ConstantsSql;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class OrderDao extends AbstractDao<Order> implements BaseDao<Order> {

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
        return saveOrUpdateAbstractAll(ConstantsSql.ORDER_CREATE, list, "Save");
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
}