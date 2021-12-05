package org.aleks4ay.dao;

import org.aleks4ay.dao.mapper.ObjectMapper;
import org.aleks4ay.model.BaseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.*;
import java.util.stream.IntStream;

abstract class AbstractDao<T extends BaseEntity<T>> {

    private static final Logger log = LoggerFactory.getLogger(AbstractDao.class);
    public abstract String getEntityName();
    private final ConnectionBase connectionBase;

    ObjectMapper<T> objectMapper;

    AbstractDao(ObjectMapper<T> objectMapper, ConnectionBase connectionBase) {
        this.objectMapper = objectMapper;
        this.connectionBase = connectionBase;
    }

    Connection getConnection() {
        return connectionBase.getConnection();
    }

    void closeConnection(Connection connection) {
        connectionBase.closeConnection(connection);
    }

    Optional<T> findAbstractById(String sql, String id) throws SQLException {
        Connection connection = getConnection();
        try (PreparedStatement prepStatement = connection.prepareStatement(sql)){
            prepStatement.setString(1, id);
            ResultSet rs = prepStatement.executeQuery();
            if (rs.next()) {
                return Optional.of(objectMapper.extractFromResultSet(rs));
            }
        } catch (SQLException e) {
            log.warn("Exception during reading '{}'. Sql: '{}'.", getEntityName(), sql, e);
            throw e;
        } finally {
            closeConnection(connection);
        }
        return Optional.empty();
    }

    List<T> findAbstractAll(String sql) {
        Connection connection = getConnection();
        try (Statement st = connection.createStatement()){
            List<T> entities = new ArrayList<>();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                T t = objectMapper.extractFromResultSet(rs);
                entities.add(t);
            }
            return entities;

        } catch (SQLException e) {
            log.warn("Exception during reading '{}'. Sql: '{}'.", getEntityName(), sql, e);
            return Collections.emptyList();
        } finally {
            closeConnection(connection);
        }
    }

    boolean saveOrUpdateAbstractAll(String sql, List<T> ts, String target) {
        if (ts.isEmpty()) {
            return true;
        }
        Connection connection = getConnection();
        try (PreparedStatement prepStatement = connection.prepareStatement(sql)) {
            connection.setAutoCommit(false);
            int result = 0;
            for (T t : ts) {
                objectMapper.insertToResultSet(prepStatement, t);
                prepStatement.addBatch();
                int[] numberOfUpdates = prepStatement.executeBatch();
                result += IntStream.of(numberOfUpdates).sum();
                log.debug("Will be {}: {}", target, t);
            }
            if (result == ts.size()) {
                connection.commit();
                log.debug("{} {} {}d.", result, getEntityName(), target);
                return true;
            } else {
                log.debug("{}d {}, but need to {} {} {}. Not equals!!!", target, result, target, ts.size(), getEntityName());
                connection.rollback();
                return false;
            }
        } catch (SQLException e) {
            log.warn("Exception during saving/updating {} '{}'. SQL = {}.", ts.size(), getEntityName(), sql, e);
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

    boolean deleteAbstractAll(String sql, Collection<T> ts) {
        if (ts.isEmpty()) {
            return true;
        }
        Connection connection = getConnection();
        try (PreparedStatement prepStatement = connection.prepareStatement(sql)) {
            connection.setAutoCommit(false);
            int result = 0;
            for (T t : ts) {
                objectMapper.insertIdToResultSet(prepStatement, t);
                prepStatement.addBatch();
                int[] numberOfUpdates = prepStatement.executeBatch();
                result += IntStream.of(numberOfUpdates).sum();
                log.debug("Will be delete: {}", t);
            }
            if (result == ts.size()) {
                connection.commit();
                log.debug("{} {} deleted.", result, getEntityName());
                return true;
            } else {
                log.debug("Deleted {}, but need to delete {} {}. Not equals!!!", result, ts.size(), getEntityName());
                connection.rollback();
                return false;
            }
        } catch (SQLException e) {
            log.warn("Exception during deleting {} '{}'. SQL = {}.", ts.size(), getEntityName(), sql, e);
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