package org.aleks4ay.dbf;

import com.linuxense.javadbf.DBFException;
import com.linuxense.javadbf.DBFReader;
import com.linuxense.javadbf.DBFRow;
import com.linuxense.javadbf.DBFUtils;
import org.aleks4ay.exception.CannotReadDataFromByteArrayException;
import org.aleks4ay.exception.EmptyByteArrayException;
import org.aleks4ay.model.Description;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class DescriptionReader  implements DbfReader<Description>{
    private static final Logger log = LoggerFactory.getLogger(DescriptionReader.class);

    @Override
    public List<Description> getAllFromDbfByteArray(byte[] dataByteArray) throws EmptyByteArrayException, CannotReadDataFromByteArrayException {
        if (dataByteArray.length == 0) {
            throw new EmptyByteArrayException();
        }
        List<Description> descriptions = new ArrayList<>();
        InputStream is = new ByteArrayInputStream(dataByteArray);
        DBFReader reader = new DBFReader(is);
        try {
            DBFRow row;
            while ((row = reader.nextRow()) != null) {
                String idDoc = row.getString("IDDOC");
                String idTmc = row.getString("SP1902");//Must not be: 'Go designer to size measurement', 'Shipment', 'Fixing', 'DELETED'
                if (idTmc.equalsIgnoreCase("   CBN") || idTmc.equalsIgnoreCase("   7LH") ||
                        idTmc.equalsIgnoreCase("   9VQ") || idTmc.equalsIgnoreCase("     0")) {
                    continue;
                }
                int position = row.getInt("LINENO");
                int quantity = row.getInt("SP1905");
                String descrSecond = new String(row.getString("SP14676").getBytes("ISO-8859-15"), "Windows-1251");
                int sizeA = row.getInt("SP14686");
                int sizeB = row.getInt("SP14687");
                int sizeC = row.getInt("SP14688");
                String idEmbodiment = row.getString("SP14717");
                String kod = idDoc + "-" + position;
                descriptions.add(new Description(kod, idDoc, position, idTmc, quantity, descrSecond, sizeA, sizeB, sizeC, idEmbodiment));
            }
            log.debug("Was read {} Description from 1C 'DT1898'.", descriptions.size());
            return descriptions;
        } catch (DBFException | UnsupportedEncodingException e) {
            log.warn("Exception during writing all 'Description'.", e);
            throw new CannotReadDataFromByteArrayException();
        } finally {
            DBFUtils.close(reader);
        }
    }
}
