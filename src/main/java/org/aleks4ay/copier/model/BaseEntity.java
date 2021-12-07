package org.aleks4ay.copier.model;

public interface BaseEntity<T> {
    String getId();
    String getDifferences(T t);
    String getEntityName();
}
