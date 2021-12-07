package org.aleks4ay.copier.dbf;

import com.linuxense.javadbf.*;
import org.aleks4ay.copier.exception.CannotReadDataFromByteArrayException;
import org.aleks4ay.copier.exception.EmptyByteArrayException;
import org.aleks4ay.copier.model.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.*;

public class OrderReader implements DbfReader<Order>{
    private static final Logger log = LoggerFactory.getLogger(OrderReader.class);

    @Override
    public List<Order> getAllFromDbfByteArray(byte[] dataByteArray)
            throws EmptyByteArrayException, CannotReadDataFromByteArrayException {
        if (dataByteArray.length == 0) {
            throw new EmptyByteArrayException();
        }
        DBFReader reader = null;
        try {
            InputStream is = new ByteArrayInputStream(dataByteArray);
            reader = new DBFReader(is);
            DBFRow row;
            List<Order> orders = new ArrayList<>();
            while ((row = reader.nextRow()) != null) {
                int keyOrderToFactory = row.getInt("SP14694");
                if (keyOrderToFactory != 1) {
                    continue;
                }
                String idDoc = row.getString("IDDOC");
                String idClient = row.getString("SP1899");
                Date date = row.getDate("SP14836");
                Timestamp dateToFactory;
                if (date == null) {
                    dateToFactory = null;
                }
                else if (date.getTime() < 1560805200000L) {
                    continue;
                }
                else {
                    dateToFactory = new Timestamp(date.getTime());
                }
                int durationTime = row.getInt("SP14695");
                String idManager = row.getString("SP14680");
                double price = row.getDouble("SP14684");

                orders.add(new Order(idDoc, idClient, idManager, durationTime, dateToFactory, price));
            }
            log.debug("Was read {} orders from 1C 'DH1898'.", orders.size());
            return orders;
        } catch (DBFException e) {
            log.warn("Exception during writing all 'Orders'.", e);
            throw new CannotReadDataFromByteArrayException();
        } finally {
            DBFUtils.close(reader);
        }
    }
}
