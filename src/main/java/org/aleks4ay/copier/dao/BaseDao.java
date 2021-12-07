package org.aleks4ay.copier.dao;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface BaseDao<T> {
    Optional<T> findById(String key) throws SQLException;
    List<T> findAll();
    boolean createAll(List<T> list);
    boolean updateAll(List<T> list);
    boolean deleteAll(Collection<T> list);
    String getEntityName();
}
