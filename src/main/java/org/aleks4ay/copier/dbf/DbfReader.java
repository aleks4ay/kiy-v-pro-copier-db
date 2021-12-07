package org.aleks4ay.copier.dbf;

import org.aleks4ay.copier.exception.CannotReadDataFromByteArrayException;
import org.aleks4ay.copier.exception.EmptyByteArrayException;

import java.util.List;

public interface DbfReader<T> {

    List<T> getAllFromDbfByteArray(byte[] dataByteArray)
            throws EmptyByteArrayException, CannotReadDataFromByteArrayException;
}
