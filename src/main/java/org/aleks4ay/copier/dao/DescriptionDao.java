package org.aleks4ay.copier.dao;

import org.aleks4ay.copier.dao.mapper.DescriptionMapper;
import org.aleks4ay.copier.model.Description;
import org.aleks4ay.copier.tools.ConstantsSql;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class DescriptionDao extends AbstractDao<Description> implements BaseDao<Description> {

    public DescriptionDao(ConnectionPool connectionPool) {
        super(new DescriptionMapper(), connectionPool);
    }

    @Override
    public Optional<Description> findById(String id) throws SQLException {
        return findAbstractById(ConstantsSql.DESCRIPTION_GET_ONE, id);
    }

    @Override
    public List<Description> findAll() {
        return findAbstractAll(ConstantsSql.DESCRIPTION_GET_ALL);
    }

    @Override
    public boolean createAll(List<Description> list) {
        return saveOrUpdateAbstractAll(ConstantsSql.DESCRIPTION_CREATE, list, "Save");
    }

    @Override
    public boolean updateAll(List<Description> list) {
        return saveOrUpdateAbstractAll(ConstantsSql.DESCRIPTION_UPDATE, list, "Update");
    }

    @Override
    public boolean deleteAll(Collection<Description> list) {
        return deleteAbstractAll(ConstantsSql.DESCRIPTION_DELETE, list);
    }

    @Override
    public String getEntityName() {
        return "Description";
    }
}