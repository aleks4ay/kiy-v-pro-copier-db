package org.aleks4ay.tools;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

public final class DataControl {

    public static String fileName;
    private static String fileName1C;
    private static final Logger log = LoggerFactory.getLogger(DataControl.class);

    static {
        //load DB properties
        try (InputStream in = DataControl.class.getClassLoader().getResourceAsStream("persistence.properties")){
            Properties properties = new Properties();
            properties.load(in);
            fileName = properties.getProperty("fileName");
            fileName1C = properties.getProperty("fileName1C");
        } catch (IOException e) {
            log.warn("Exception during Loaded properties from file {}.", new File("/persistence.properties").getPath(), e);
        }
    }

    // APPEND new time
    public static boolean writeTimeChange(){
        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(new File(fileName), true), StandardCharsets.UTF_8))) {
            LocalDateTime date = LocalDateTime.now();
            String dateString = date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")) + " < 1C  >";
            bw.write( dateString + " < 1C  >" + System.lineSeparator());
            bw.flush();
        }
        catch(IOException ex){
            log.warn(ex.getMessage());
            return false;
        }
        return true;
    }

    // REWRITE time from 1C
    public static boolean writeTimeChangeFrom1C(){
        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(new File(fileName1C), false), StandardCharsets.UTF_8))) {
            LocalDateTime date = LocalDateTime.now();
            String dateString = date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")) + " < 1C  >";
            bw.write(dateString + " < 1C  >");
            bw.flush();
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
            return false;
        }
        return true;
    }
}

