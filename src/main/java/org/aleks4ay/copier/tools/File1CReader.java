package org.aleks4ay.copier.tools;

import org.aleks4ay.copier.exception.FileNotReadException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

public final class File1CReader {
    private static final Logger log = LoggerFactory.getLogger(File1CReader.class);

    public byte[] file2byteArray(String fileName) {
        File file = new File(fileName);
        int numberOfTry = 0;
        while (numberOfTry++ < 4) {
            try (InputStream fis = new FileInputStream(file)) {
                byte[] fileInArray = new byte[(int) file.length()];
                fis.read(fileInArray);
                if (fileInArray.length == file.length()) {
                    return fileInArray;
                } else {
                    throw new FileNotReadException(fileName);
                }
            } catch (IOException e) {
                log.warn("Exception during read '{}'.", fileName);
                try {
                    TimeUnit.SECONDS.sleep(10);
                } catch (InterruptedException ie) {
                    log.warn("Exception during sleep 10 second.", ie);
                }
            }
        }
        return new byte[0];
    }
}