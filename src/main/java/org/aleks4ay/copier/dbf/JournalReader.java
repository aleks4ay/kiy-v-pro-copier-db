package org.aleks4ay.copier.dbf;

import com.linuxense.javadbf.DBFException;
import com.linuxense.javadbf.DBFReader;
import com.linuxense.javadbf.DBFRow;
import com.linuxense.javadbf.DBFUtils;
import org.aleks4ay.copier.exception.CannotReadDataFromByteArrayException;
import org.aleks4ay.copier.exception.EmptyByteArrayException;
import org.aleks4ay.copier.model.Journal;
import org.aleks4ay.copier.tools.Constants;
import org.aleks4ay.copier.tools.File1CReader;
import org.aleks4ay.copier.tools.TimeConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

public class JournalReader implements DbfReader<Journal> {
    private static final Logger log = LoggerFactory.getLogger(JournalReader.class);
    private final long FIRST_OF_JULY_2021_IN_MILLIS =
            LocalDateTime.of(2021, 7, 1, 0, 0).toEpochSecond(java.time.ZoneOffset.UTC) * 1000;

    public static void main(String[] args) throws IOException {
        String fileName = Constants.DBF_PATH + Constants.JOURNAL_FILE;
        System.out.println("fileName: " + fileName);
        new JournalReader().getAllFromDbfByteArray(new File1CReader().file2byteArray(fileName))
                .forEach(System.out::println);
    }

    @Override
    public List <Journal> getAllFromDbfByteArray(byte[] dataByteArray)
            throws EmptyByteArrayException, CannotReadDataFromByteArrayException {
        if (dataByteArray.length == 0) {
            throw new EmptyByteArrayException();
        }
        List<Journal> mapJournal = new ArrayList<>();
        InputStream is = new ByteArrayInputStream(dataByteArray);
        DBFReader reader = new DBFReader(is);
        try {
            DBFRow row;
            while ((row = reader.nextRow()) != null) {
                Date keyOrderYear = row.getDate("DATE");
                int keyOrderIsEnable = row.getInt("CLOSED");
                if (keyOrderYear.getTime() < FIRST_OF_JULY_2021_IN_MILLIS || keyOrderIsEnable ==4 ) {
                    continue;
                }
                String idDoc = row.getString("IDDOC");
                String docNumber = new String(row.getString("DOCNO").getBytes("ISO-8859-15"), "Windows-1251");
                long dateCreate = row.getDate("DATE").getTime();
                long timeCreate = TimeConverter.convertStrToTimeMillisecond(row.getString("TIME"));
                Journal journal = new Journal(idDoc, docNumber, new Timestamp(dateCreate + timeCreate));
                mapJournal.add(journal);
            }
            log.debug("Was read {} Journal from 1C '1SJOURN'.", mapJournal.size());
            return mapJournal;
        } catch (DBFException | UnsupportedEncodingException e) {
            log.warn("Exception during writing all 'Journal'.", e);
            throw new CannotReadDataFromByteArrayException();
        } finally {
            DBFUtils.close(reader);
        }
    }
}
