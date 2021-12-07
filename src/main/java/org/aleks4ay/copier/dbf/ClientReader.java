package org.aleks4ay.copier.dbf;

import com.linuxense.javadbf.*;
import org.aleks4ay.copier.exception.CannotReadDataFromByteArrayException;
import org.aleks4ay.copier.exception.EmptyByteArrayException;
import org.aleks4ay.copier.model.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class ClientReader implements DbfReader<Client>{
    private static final Logger log = LoggerFactory.getLogger(ClientReader.class);

    public List<Client> getAllFromDbfByteArray(byte[] dataByteArray)
            throws EmptyByteArrayException, CannotReadDataFromByteArrayException {
        if (dataByteArray.length == 0) {
            throw new EmptyByteArrayException();
        }
        DBFReader reader = null;
        try {
            List<Client> listClient = new ArrayList<>();
            InputStream is = new ByteArrayInputStream(dataByteArray);
            reader = new DBFReader(is);
            DBFRow row;
            while ((row = reader.nextRow()) != null) {
                String id = row.getString("ID");
                String name = new String(row.getString("DESCR").getBytes("ISO-8859-15"), "Windows-1251");
                if (name.equals("")) {
                    name = "-";
                }
                listClient.add(new Client(id, name));
            }
            log.debug("Was read {} Clients from 1S.", listClient.size());
            return listClient;
        } catch (DBFException | UnsupportedEncodingException e) {
            log.warn("Exception during writing all 'Client'.", e);
            throw new CannotReadDataFromByteArrayException();
        } finally {
            DBFUtils.close(reader);
        }
    }
}
