package org.aleks4ay.block_files;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileLock;
import java.util.concurrent.TimeUnit;

public class LockFileAndWrite {

    public static void main(String[] args) {
//        writeFileWithLock(new File("c:/1C/Copy250106/SC172.DBF"), "mkyong");
//        writeFileWithLock(new File("c:/1C/Copy250106/SC1670.DBF"), "mkyong");
//        writeFileWithLock(new File("c:/1C/Copy250106/1SJOURN.DBF"), "mkyong");
        writeFileWithLock(new File("c:/1C/Copy250106/SC302.DBF"), "mkyong");
//        writeFileWithLock(new File("c:/1C/Copy250106/SC14716.DBF"), "mkyong");
//        writeFileWithLock(new File("c:/1C/Copy250106/DH1898.DBF"), "mkyong");
//        writeFileWithLock(new File("c:/1C/Copy250106/DT1898.DBF"), "mkyong");
//        writeFileWithLock(new File("c:/1C/Copy250106/DT2728.DBF"), "mkyong");
//        writeFileWithLock(new File("c:/1C/Copy250106/DH3592.DBF"), "mkyong");
//        writeFileWithLock(new File("c:/1C/Copy250106/DT3592.DBF"), "mkyong");
//        writeFileWithLock(new File("c:/1C/Copy250106/RG1253.DBF"), "mkyong");
    }

    public static void writeFileWithLock(File file, String content) {
        try (RandomAccessFile reader = new RandomAccessFile(file, "rw"); FileLock lock = reader.getChannel().lock()) {
            TimeUnit.SECONDS.sleep(18);
            reader.read();
//            reader.write(content.getBytes());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}