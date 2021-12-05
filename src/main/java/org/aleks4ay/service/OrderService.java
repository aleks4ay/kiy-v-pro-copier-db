package org.aleks4ay.service;

import org.aleks4ay.dao.BaseDao;
import org.aleks4ay.dao.ConnectionPool;
import org.aleks4ay.dao.OrderDao;
import org.aleks4ay.dbf.DbfReader;
import org.aleks4ay.dbf.OrderReader;
import org.aleks4ay.model.Order;
import org.aleks4ay.tools.Constants;

import java.util.List;

public class OrderService extends AbstractService<Order> {

    private final static String FILE_PATH = Constants.DBF_PATH + Constants.ORDER_FILE;

    public OrderService(DbfReader<Order> reader, BaseDao<Order> orderDao) {
        super(reader, orderDao);
    }

    public static void main(String[] args) {
        new OrderService(new OrderReader(), new OrderDao(ConnectionPool.getInstance())).copyNewDataAbstract(FILE_PATH);
    }

    public Order findById(String id) {
        return findAbstractById(id);
    }

    public List<String> findAllOrderId() {
        return readAllId();
    }
}