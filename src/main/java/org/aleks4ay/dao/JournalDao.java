package org.aleks4ay.dao;

import org.aleks4ay.dao.mapper.JournalMapper;
import org.aleks4ay.model.Journal;
import org.aleks4ay.tools.ConstantsSql;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class JournalDao extends AbstractDao<Journal> implements BaseDao<Journal> {

    public JournalDao(ConnectionPool connectionPool) {
        super(new JournalMapper(), connectionPool);
    }

    @Override
    public Optional<Journal> findById(String id) throws SQLException {
        return findAbstractById(ConstantsSql.JOURNAL_GET_ONE, id);
    }

    @Override
    public List<Journal> findAll() {
        return findAbstractAll(ConstantsSql.JOURNAL_GET_ALL);
    }

    @Override
    public boolean createAll(List<Journal> list) {
        return saveOrUpdateAbstractAll(ConstantsSql.JOURNAL_CREATE, list, "Save");
    }

    @Override
    public boolean updateAll(List<Journal> list) {
        return saveOrUpdateAbstractAll(ConstantsSql.JOURNAL_UPDATE, list, "Update");
    }

    @Override
    public boolean deleteAll(Collection<Journal> list) {
        return deleteAbstractAll(ConstantsSql.JOURNAL_DELETE, list);
    }

    @Override
    public String getEntityName() {
        return "Journal";
    }
}