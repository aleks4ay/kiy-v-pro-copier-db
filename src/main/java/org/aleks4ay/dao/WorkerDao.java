package org.aleks4ay.dao;

import org.aleks4ay.dao.mapper.WorkerMapper;
import org.aleks4ay.model.Worker;
import org.aleks4ay.tools.ConstantsSql;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class WorkerDao extends AbstractDao<Worker> implements BaseDao<Worker> {

    public WorkerDao(ConnectionPool connectionPool) {
        super(new WorkerMapper(), connectionPool);
    }

    @Override
    public Optional<Worker> findById(String id) throws SQLException {
        return findAbstractById(ConstantsSql.WORKER_GET_ONE, id);
    }

    @Override
    public List<Worker> findAll() {
        return findAbstractAll(ConstantsSql.WORKER_GET_ALL);
    }

    @Override
    public boolean createAll(List<Worker> list) {
        return saveOrUpdateAbstractAll(ConstantsSql.WORKER_CREATE, list, "Save");
    }

    @Override
    public boolean updateAll(List<Worker> list) {
        return saveOrUpdateAbstractAll(ConstantsSql.WORKER_UPDATE, list, "Update");
    }

    @Override
    public boolean deleteAll(Collection<Worker> list) {
        return deleteAbstractAll(ConstantsSql.WORKER_DELETE, list);
    }

    @Override
    public String getEntityName() {
        return "Worker";
    }
}