package org.aleks4ay.copier.dao;

import org.aleks4ay.copier.dao.mapper.EmbodimentMapper;
import org.aleks4ay.copier.model.Embodiment;
import org.aleks4ay.copier.tools.ConstantsSql;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class EmbodimentDao extends AbstractDao<Embodiment> implements BaseDao<Embodiment> {

    public EmbodimentDao(ConnectionPool connectionPool) {
        super(new EmbodimentMapper(), connectionPool);
    }

    @Override
    public Optional<Embodiment> findById(String id) throws SQLException {
        return findAbstractById(ConstantsSql.EMBODIMENT_GET_ONE, id);
    }

    @Override
    public List<Embodiment> findAll() {
        return findAbstractAll(ConstantsSql.EMBODIMENT_GET_ALL);
    }

    @Override
    public boolean createAll(List<Embodiment> list) {
        return saveOrUpdateAbstractAll(ConstantsSql.EMBODIMENT_CREATE, list, "Save");
    }

    @Override
    public boolean updateAll(List<Embodiment> list) {
        return saveOrUpdateAbstractAll(ConstantsSql.EMBODIMENT_UPDATE, list, "Update");
    }

    @Override
    public boolean deleteAll(Collection<Embodiment> list) {
        return deleteAbstractAll(ConstantsSql.EMBODIMENT_DELETE, list);
    }

    @Override
    public String getEntityName() {
        return "Embodiment";
    }
}