package org.aleks4ay.copier.service;

import org.aleks4ay.copier.dao.BaseDao;
import org.aleks4ay.copier.dao.ConnectionPool;
import org.aleks4ay.copier.dao.OrderDao;
import org.aleks4ay.copier.dbf.DbfReader;
import org.aleks4ay.copier.dbf.OrderReader;
import org.aleks4ay.copier.model.Order;
import org.aleks4ay.copier.tools.Constants;

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
        return ((OrderDao)getDao()).findAllId();
    }
}