package org.aleks4ay.copier.service;

import org.aleks4ay.copier.dao.BaseDao;
import org.aleks4ay.copier.dao.ConnectionPool;
import org.aleks4ay.copier.dao.DescriptionDao;
import org.aleks4ay.copier.dao.OrderDao;
import org.aleks4ay.copier.dbf.DbfReader;
import org.aleks4ay.copier.dbf.DescriptionReader;
import org.aleks4ay.copier.dbf.OrderReader;
import org.aleks4ay.copier.exception.CannotReadDataFromByteArrayException;
import org.aleks4ay.copier.exception.EmptyByteArrayException;
import org.aleks4ay.copier.model.Description;
import org.aleks4ay.copier.tools.Constants;
import org.aleks4ay.copier.tools.File1CReader;

import java.util.List;
import java.util.stream.Collectors;

public class DescriptionService extends AbstractService<Description> {
    private final OrderService orderService = new OrderService(new OrderReader(), new OrderDao(ConnectionPool.getInstance()));

    private final static String FILE_PATH = Constants.DBF_PATH + Constants.DESCRIPTION_FILE;

    public DescriptionService(DbfReader<Description> reader, BaseDao<Description> descriptionDao) {
        super(reader, descriptionDao);
    }

    public static void main(String[] args) {
        new DescriptionService(new DescriptionReader(), new DescriptionDao(ConnectionPool.getInstance())).copyNewDataAbstract(FILE_PATH);
    }

    public Description findById(String id) {
        return findAbstractById(id);
    }

    @Override
    public List<Description> readNewData(String filePath) throws CannotReadDataFromByteArrayException, EmptyByteArrayException {
        log.info("   reading 'DESCRIPTION'.");

        final List<String> keys = orderService.findAllOrderId();

        byte[] bytesEntity = new File1CReader().file2byteArray(filePath);
        try {
            List<Description> descriptionList = new DescriptionReader().getAllFromDbfByteArray(bytesEntity);
            return descriptionList
                    .stream()
                    .filter(i -> keys.contains(i.getIdDoc()))
                    .collect(Collectors.toList());
        } catch (EmptyByteArrayException | CannotReadDataFromByteArrayException e) {
            log.warn("Can't copy new data from 'Description'.", e);
            throw e;
        }
    }
}