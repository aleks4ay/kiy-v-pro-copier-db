package org.aleks4ay.dbf;

import org.aleks4ay.exception.CannotReadDataFromByteArrayException;
import org.aleks4ay.exception.EmptyByteArrayException;

import java.util.List;

public interface DbfReader<T> {

    List<T> getAllFromDbfByteArray(byte[] dataByteArray)
            throws EmptyByteArrayException, CannotReadDataFromByteArrayException;
}
