package org.aleks4ay.copier.dao;

import org.aleks4ay.copier.dao.mapper.DescriptionMapper;
import org.aleks4ay.copier.model.Description;
import org.aleks4ay.copier.tools.ConstantsSql;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

public class DescriptionDao extends AbstractDao<Description> implements BaseDao<Description> {
    private static final Logger log = LoggerFactory.getLogger(DescriptionDao.class);

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
        boolean resultCreateDescription = saveOrUpdateAbstractAll(ConstantsSql.DESCRIPTION_CREATE, list, "Save");
        if (resultCreateDescription) {
            return createStatusOrTimeAll(ConstantsSql.DESCRIPTION_STATUS_CREATE, list, "status")
                    && createStatusOrTimeAll(ConstantsSql.DESCRIPTION_TIME_CREATE, list, "time");
        }
        return false;
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


    boolean createStatusOrTimeAll(String sql, List<Description> list, String target) {
        if (list.isEmpty()) {
            return true;
        }
        Connection connection = getConnection();
        try (PreparedStatement prepStatement = connection.prepareStatement(sql)) {
            connection.setAutoCommit(false);
            int result = 0;
            for (Description description : list) {
                if (sql.contains("description_status")) {
                    ((DescriptionMapper) objectMapper).insertToResultSetStatus(prepStatement, description);
                } else {
                    ((DescriptionMapper) objectMapper).insertToResultSetTime(prepStatement, description);
                }
                prepStatement.addBatch();
                int[] numberOfUpdates = prepStatement.executeBatch();
                result += IntStream.of(numberOfUpdates).sum();
                log.debug("Will be save '{}' for Description: {}", target, description);
            }
            if (result == list.size()) {
                connection.commit();
                log.debug("{} '{}' for saved.", target, result);
                return true;
            } else {
                log.debug("Saved {}, but need to Save {} '{}' for Description. Not equals!!!", result, list.size(), target);
                connection.rollback();
                return false;
            }
        } catch (SQLException e) {
            log.warn("Exception during saving {} '{}' for Description. SQL = {}.", list.size(), target, sql, e);
            return false;
        } finally {
            try {
                connection.setAutoCommit(true);
                closeConnection(connection);
            } catch (SQLException e) {
                log.warn("Can't close connection.", e);
            }
        }
    }
}