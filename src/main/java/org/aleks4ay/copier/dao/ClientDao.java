package org.aleks4ay.copier.dao;

import org.aleks4ay.copier.dao.mapper.ClientMapper;
import org.aleks4ay.copier.model.Client;
import org.aleks4ay.copier.tools.ConstantsSql;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class ClientDao extends AbstractDao<Client> implements BaseDao<Client> {

    public ClientDao(ConnectionPool connectionPool) {
        super(new ClientMapper(), connectionPool);
    }

    @Override
    public Optional<Client> findById(String id) throws SQLException {
        return findAbstractById(ConstantsSql.CLIENT_GET_ONE, id);
    }

    @Override
    public List<Client> findAll() {
        return findAbstractAll(ConstantsSql.CLIENT_GET_ALL);
    }

    @Override
    public boolean createAll(List<Client> list) {
        return saveOrUpdateAbstractAll(ConstantsSql.CLIENT_CREATE, list, "Save");
    }

    @Override
    public boolean updateAll(List<Client> list) {
        return saveOrUpdateAbstractAll(ConstantsSql.CLIENT_UPDATE, list, "Update");
    }

    @Override
    public boolean deleteAll(Collection<Client> list) {
        return deleteAbstractAll(ConstantsSql.CLIENT_DELETE, list);
    }

    @Override
    public String getEntityName() {
        return "Client";
    }
}