package org.aleks4ay.dao;

import org.aleks4ay.dao.mapper.TmcMapper;
import org.aleks4ay.model.Tmc;
import org.aleks4ay.tools.ConstantsSql;

import java.sql.*;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class TmcDao extends AbstractDao<Tmc> implements BaseDao<Tmc> {

    public TmcDao(ConnectionBase connectionBase) {
        super(new TmcMapper(), connectionBase);
    }

    @Override
    public Optional<Tmc> findById(String id) throws SQLException {
        return findAbstractById(ConstantsSql.TMC_GET_ONE, id);
    }

    @Override
    public List<Tmc> findAll() {
        return findAbstractAll(ConstantsSql.TMC_GET_ALL);
    }

    @Override
    public boolean createAll(List<Tmc> list) {
        return saveOrUpdateAbstractAll(ConstantsSql.TMC_CREATE, list, "Save");
    }

    @Override
    public boolean updateAll(List<Tmc> list) {
        return saveOrUpdateAbstractAll(ConstantsSql.TMC_UPDATE, list, "Update");
    }

    @Override
    public boolean deleteAll(Collection<Tmc> list) {
        return deleteAbstractAll(ConstantsSql.TMC_DELETE, list);
    }

    @Override
    public String getEntityName() {
        return "Tmc";
    }
}