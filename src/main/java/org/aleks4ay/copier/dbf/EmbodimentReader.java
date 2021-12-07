package org.aleks4ay.copier.dbf;

import com.linuxense.javadbf.DBFException;
import com.linuxense.javadbf.DBFReader;
import com.linuxense.javadbf.DBFRow;
import com.linuxense.javadbf.DBFUtils;
import org.aleks4ay.copier.exception.CannotReadDataFromByteArrayException;
import org.aleks4ay.copier.exception.EmptyByteArrayException;
import org.aleks4ay.copier.model.Embodiment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;


public class EmbodimentReader implements DbfReader<Embodiment>{
    private static final Logger log = LoggerFactory.getLogger(EmbodimentReader.class);

    @Override
    public List<Embodiment> getAllFromDbfByteArray(byte[] dataByteArray)
            throws EmptyByteArrayException, CannotReadDataFromByteArrayException {
        if (dataByteArray.length == 0) {
            throw new EmptyByteArrayException();
        }
        DBFReader reader = null;
        try {
            List<Embodiment> listEmbodiment = new ArrayList<>();
            InputStream is = new ByteArrayInputStream(dataByteArray);
            reader = new DBFReader(is);
            DBFRow row;
            while ((row = reader.nextRow()) != null) {
                String id = row.getString("ID");
                String descr = new String(row.getString("DESCR").getBytes("ISO-8859-15"), "Windows-1251");
                listEmbodiment.add(new Embodiment(id, descr));
            }
            log.debug("Was read {} rows from 1C SC14716.", listEmbodiment.size());
            return listEmbodiment;
        } catch (DBFException | UnsupportedEncodingException e) {
            log.warn("Exception during writing all 'Embodiment'.", e);
            throw new CannotReadDataFromByteArrayException();
        } finally {
            DBFUtils.close(reader);
        }
    }
}
