package org.aleks4ay.dbf;

import com.linuxense.javadbf.DBFException;
import com.linuxense.javadbf.DBFReader;
import com.linuxense.javadbf.DBFRow;
import com.linuxense.javadbf.DBFUtils;
import org.aleks4ay.exception.CannotReadDataFromByteArrayException;
import org.aleks4ay.exception.EmptyByteArrayException;
import org.aleks4ay.model.Tmc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class TmcReader {
    private static final Logger log = LoggerFactory.getLogger(TmcReader.class);

    public List<Tmc> getAllFromDbfByteArray(byte[] dataByteArray)
            throws EmptyByteArrayException, CannotReadDataFromByteArrayException {
        if (dataByteArray.length == 0) {
            throw new EmptyByteArrayException();
        }
        DBFReader reader = null;
        try {
            InputStream is = new ByteArrayInputStream(dataByteArray);
            reader = new DBFReader(is);
            DBFRow row;
            List<Tmc> listTmc = new ArrayList<>();
            while ((row = reader.nextRow()) != null) {
                String id = row.getString("ID");
                String parentId = row.getString("PARENTID");
                String code = row.getString("CODE");
                String descr = new String(row.getString("DESCR").getBytes("ISO-8859-15"), "Windows-1251");
                int isFolder = row.getInt("ISFOLDER");
                int sizeA = row.getInt("SP14690");
                int sizeB = row.getInt("SP14691");
                int sizeC = row.getInt("SP14692");
                String descrAll = new String(row.getString("SP276").getBytes("ISO-8859-15"), "Windows-1251");
                String type = row.getString("SP277");
                listTmc.add(new Tmc(id, parentId, code, sizeA, sizeB, sizeC, descr, isFolder, descrAll, type));
            }
            log.debug("Was read {} Tmc from 1S.", listTmc.size());
            return listTmc;
        } catch (DBFException | UnsupportedEncodingException e) {
            log.warn("Exception during reading all 'Tmc'.", e);
            throw new CannotReadDataFromByteArrayException();
        } finally {
            DBFUtils.close(reader);
        }
    }
}
