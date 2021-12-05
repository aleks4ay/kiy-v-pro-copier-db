package org.aleks4ay.service;

import org.aleks4ay.dao.BaseDao;
import org.aleks4ay.dbf.DbfReader;
import org.aleks4ay.exception.*;
import org.aleks4ay.model.BaseEntity;
import org.aleks4ay.tools.File1CReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public abstract class AbstractService<T extends BaseEntity<T>> {
    private static final Logger log = LoggerFactory.getLogger(AbstractService.class);

    private final DbfReader<T> reader;
    private final BaseDao<T> dao;

    public AbstractService(DbfReader<T> reader, BaseDao<T> dao) {
        this.reader = reader;
        this.dao = dao;
    }

    public T findAbstractById(String id) {
        T entity = null;
        try {
            entity = dao.findById(id).orElseThrow(
                    () -> {
                        log.warn(dao.getEntityName() + " with id='" + id + "' not found");
                        return new NotFoundException(dao.getEntityName() + " with id='" + id + "' not found");
                    });
        } catch (NotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return entity;
    }

    public boolean copyNewDataAbstract(String filePath) {
        List<T> newDataList;
        try {
            newDataList = readNewData(filePath);
        } catch (CannotReadDataFromByteArrayException | EmptyByteArrayException e) {
            return false;
        }

        Map<String, T> oldDataMap = readOldData();
        deleteNotUnique(newDataList, oldDataMap);

        return updateAll(newDataList, oldDataMap)
                && createAll(newDataList)
                && deleteAll(oldDataMap);
    }

    public List<T> readNewData(String filePath) throws CannotReadDataFromByteArrayException, EmptyByteArrayException {
        log.info("   reading '" + dao.getEntityName().toUpperCase() + "'.");
        byte[] bytesEntity = new File1CReader().file2byteArray(filePath);
        try {
            return reader.getAllFromDbfByteArray(bytesEntity);
        } catch (EmptyByteArrayException | CannotReadDataFromByteArrayException e) {
            log.warn("Can't copy new data from '{}'.", dao.getEntityName(), e);
            throw e;
        }
    }

    public Map<String, T> readOldData() {
        return dao.findAll()
                .stream()
                .collect(Collectors.toMap(T::getId, t -> t));
    }

    public void deleteNotUnique(List<T> newData, Map<String, T> oldDataMap) {
        Iterator<T> iterator = newData.iterator();
        while (iterator.hasNext()) {
            T t = iterator.next();
            String idCurrentEntity = t.getId();
            if (oldDataMap.containsKey(idCurrentEntity) && oldDataMap.get(idCurrentEntity).equals(t)) {
                oldDataMap.remove(idCurrentEntity);
                iterator.remove();
            }
        }
    }

    public boolean updateAll(List<T> newData, Map<String, T> oldDataMap) {
        List<T> listUpdatingEntity = new ArrayList<>();
        Iterator<T> iterator = newData.iterator();
        while (iterator.hasNext()) {
            T t = iterator.next();
            String idCurrentEntity = t.getId();
            if (oldDataMap.containsKey(idCurrentEntity) && !oldDataMap.get(idCurrentEntity).equals(t)) {
                log.info("   '{}' with code '{}' is different. Different fields: {}.",
                        dao.getEntityName(),
                        idCurrentEntity,
                        t.getDifferences(oldDataMap.get(idCurrentEntity))
                );
                listUpdatingEntity.add(t);
                iterator.remove();
                oldDataMap.remove(idCurrentEntity);
            }
        }
        return dao.updateAll(listUpdatingEntity);
    }

    public boolean createAll(List<T> newDataAfterRemoving) {
        return dao.createAll(newDataAfterRemoving);
    }

    public boolean deleteAll(Map<String, T> oldDataMapAfterRemoving) {
        return dao.deleteAll(oldDataMapAfterRemoving.values());
    }
}