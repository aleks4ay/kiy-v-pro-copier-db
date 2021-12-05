package org.aleks4ay.dao;

import org.aleks4ay.dao.mapper.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.*;
import java.util.stream.IntStream;

abstract class AbstractDao<K, T> {

    private static final Logger log = LoggerFactory.getLogger(AbstractDao.class);
    public abstract Optional<T> findById(K key) throws SQLException;
    public abstract List<T> findAll();
    public abstract boolean updateAll(List<T> list);
    public abstract boolean deleteAll(Collection<T> list);
    public abstract boolean createAll(List<T> list);

    ObjectMapper<T> objectMapper;
    Connection connection;
    private final String entityName;

    AbstractDao(Connection connection, ObjectMapper<T> objectMapper, String entityName) {
        this.connection = connection;
        this.objectMapper = objectMapper;
        this.entityName = entityName;
    }

    Optional<T> findAbstractById(String sql, String id) throws SQLException {
        try (PreparedStatement prepStatement = connection.prepareStatement(sql)){
            prepStatement.setString(1, id);
            ResultSet rs = prepStatement.executeQuery();
            if (rs.next()) {
                return Optional.of(objectMapper.extractFromResultSet(rs));
            }
        } catch (SQLException e) {
            log.warn("Exception during reading '{}'. Sql: '{}'.", entityName, sql, e);
            throw e;
        }
        return Optional.empty();
    }

    List<T> findAbstractAll(String sql) {
        try (Statement st = connection.createStatement()){
            List<T> entities = new ArrayList<>();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                T t = objectMapper.extractFromResultSet(rs);
                entities.add(t);
            }
            return entities;

        } catch (SQLException e) {
            log.warn("Exception during reading '{}'. Sql: '{}'.", entityName, sql, e);
            return Collections.emptyList();
        }
    }

    boolean saveOrUpdateAbstractAll(String sql, List<T> ts, String target) {
        if (ts.isEmpty()) {
            return true;
        }
        try (PreparedStatement prepStatement = connection.prepareStatement(sql)) {
            connection.setAutoCommit(false);
            int result = 0;
            for (T t : ts) {
                objectMapper.insertToResultSet(prepStatement, t);
                prepStatement.addBatch();
                int[] numberOfUpdates = prepStatement.executeBatch();
                result += IntStream.of(numberOfUpdates).sum();
                log.debug("Will be {}d: {}", target, t);
            }
            if (result == ts.size()) {
                connection.commit();
                log.debug("Commit - OK. {} {} {}d.", result, entityName, target);
                return true;
            } else {
                log.debug("{}d {}, but need to {} {} {}. Not equals!!!", target, result, target, ts.size(), entityName);
                connection.rollback();
                return false;
            }
        } catch (SQLException e) {
            log.warn("Exception during saving/updating {} '{}'. SQL = {}.", ts.size(), entityName, sql, e);
            return false;
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                log.warn("Can't close connection.", e);
            }
        }
    }

    boolean deleteAbstractAll(String sql, Collection<T> ts) {
        if (ts.isEmpty()) {
            return true;
        }
        try (PreparedStatement prepStatement = connection.prepareStatement(sql)) {
            connection.setAutoCommit(false);
            int result = 0;
            for (T t : ts) {
                objectMapper.insertIdToResultSet(prepStatement, t);
                prepStatement.addBatch();
                int[] numberOfUpdates = prepStatement.executeBatch();
                result += IntStream.of(numberOfUpdates).sum();
                log.debug("Will be deleted: {}", t);
            }
            if (result == ts.size()) {
                connection.commit();
                log.debug("Commit - OK. {} {} deleted.", result, entityName);
                return true;
            } else {
                log.debug("Deleted {}, but need to delete {} {}. Not equals!!!", result, ts.size(), entityName);
                connection.rollback();
                return false;
            }
        } catch (SQLException e) {
            log.warn("Exception during deleting {} '{}'. SQL = {}.", ts.size(), entityName, sql, e);
            return false;
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                log.warn("Can't close connection.", e);
            }
        }
    }
}