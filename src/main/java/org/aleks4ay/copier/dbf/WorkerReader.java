package org.aleks4ay.copier.dbf;

import com.linuxense.javadbf.*;
import org.aleks4ay.copier.exception.CannotReadDataFromByteArrayException;
import org.aleks4ay.copier.exception.EmptyByteArrayException;
import org.aleks4ay.copier.model.Worker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class WorkerReader implements DbfReader<Worker>{
    private static final Logger log = LoggerFactory.getLogger(WorkerReader.class);

    public List<Worker> getAllFromDbfByteArray(byte[] dataByteArray)
            throws CannotReadDataFromByteArrayException, EmptyByteArrayException {
        if (dataByteArray.length == 0) {
            throw new EmptyByteArrayException();
        }
        List<Worker> workers = new ArrayList<>();
        workers.add(new Worker("     0", "-"));
        DBFReader reader = null;
        try {
            InputStream is = new ByteArrayInputStream(dataByteArray);
            reader = new DBFReader(is);
            DBFRow row;
            while ((row = reader.nextRow()) != null) {
                String id = row.getString("ID");
                String name = new String(row.getString("DESCR").getBytes("ISO-8859-15"), "Windows-1251");
                workers.add(new Worker(id, name));
            }
            log.debug("Was read {} Worker from 1S.", workers.size());
            return workers;
        } catch (DBFException | UnsupportedEncodingException e) {
            log.warn("Exception during writing all 'Worker'.", e);
            throw new CannotReadDataFromByteArrayException();
        } finally {
            DBFUtils.close(reader);
        }
    }
}
