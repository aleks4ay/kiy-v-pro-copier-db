package org.aleks4ay.model;

public interface BaseEntity<T> {
    String getId();
    String getDifferences(T t);
    String getEntityName();
}
