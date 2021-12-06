package org.aleks4ay;

import org.aleks4ay.dao.*;
import org.aleks4ay.dbf.*;
import org.aleks4ay.service.*;
import org.aleks4ay.tools.Constants;
import org.aleks4ay.tools.DataControl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class CopyNewData1C {
    private final static String EMBODIMENT_PATH = Constants.DBF_PATH + Constants.EMBODIMENT_FILE;
    private final static String TMC_PATH = Constants.DBF_PATH + Constants.TMC_FILE;
    private final static String CLIENT_PATH = Constants.DBF_PATH + Constants.CLIENT_FILE;
    private final static String WORKER_PATH = Constants.DBF_PATH + Constants.WORKER_FILE;
    private final static String JOURNAL_PATH = Constants.DBF_PATH + Constants.JOURNAL_FILE;
    private final static String ORDER_PATH = Constants.DBF_PATH + Constants.ORDER_FILE;
    private final static String DESCRIPTION_PATH = Constants.DBF_PATH + Constants.DESCRIPTION_FILE;

    private static boolean wasChangingEmbodiment = false;
    private static boolean wasChangingTmc = false;
    private static boolean wasChangingClient = false;
    private static boolean wasChangingWorker = false;
    private static boolean wasChangingJournal = false;
    private static boolean wasChangingOrder = false;
    private static boolean wasChangingDescription = false;

    private final Map<String, Long> times = new HashMap<>();

    private static final Logger log = LoggerFactory.getLogger(CopyNewData1C.class);

    public static void main(String[] args) {
        CopyNewData1C app = new CopyNewData1C();
        while (true) {
            if (app.checkChanges()) {
                log.info("* * * * Start copy files from 1C (5 version) * * * *");
                app.update();
            } else {
                log.info("* * * *  There are no new Orders or new changes * * * *");
            }
            try {
                Thread.sleep(10 * 60 * 1000); // sleep 10 min
            } catch (InterruptedException e) {
                log.warn("Exception during sleep 15 min.", e);
            }
        }
    }

    public void update() {
        long t1 = System.currentTimeMillis();

        if (wasChangingEmbodiment) {
            new EmbodimentService(new EmbodimentReader(), new EmbodimentDao(ConnectionPool.getInstance()))
                    .copyNewDataAbstract(EMBODIMENT_PATH);
        }
        if (wasChangingTmc) {
            new TmcService(new TmcReader(), new TmcDao(ConnectionPool.getInstance()))
                    .copyNewDataAbstract(TMC_PATH);
        }
        if (wasChangingClient) {
            new ClientService(new ClientReader(), new ClientDao(ConnectionPool.getInstance()))
                    .copyNewDataAbstract(CLIENT_PATH);
        }
        if (wasChangingWorker) {
            new WorkerService(new WorkerReader(), new WorkerDao(ConnectionPool.getInstance()))
                    .copyNewDataAbstract(WORKER_PATH);
        }
        if (wasChangingJournal) {
            new JournalService(new JournalReader(), new JournalDao(ConnectionPool.getInstance()))
                    .copyNewDataAbstract(JOURNAL_PATH);
        }
        if (wasChangingOrder) {
            new OrderService(new OrderReader(), new OrderDao(ConnectionPool.getInstance()))
                    .copyNewDataAbstract(ORDER_PATH);
        }
        if (wasChangingDescription) {
            new DescriptionService(new DescriptionReader(), new DescriptionDao(ConnectionPool.getInstance()))
                    .copyNewDataAbstract(DESCRIPTION_PATH);
        }
        DataControl.writeTimeChange();
        DataControl.writeTimeChangeFrom1C();
        long t2 = System.currentTimeMillis();
        log.info("Total time for copy = {} c.", (double) ((t2 - t1) / 1000));
    }

    boolean checkChanges() {
        wasChangingEmbodiment = isChanged(EMBODIMENT_PATH, "timeEmbodiment");
        wasChangingTmc = isChanged(TMC_PATH, "timeTmc");
        wasChangingClient = isChanged(CLIENT_PATH, "timeClient");
        wasChangingWorker = isChanged(WORKER_PATH, "timeWorker");
        wasChangingJournal = isChanged(JOURNAL_PATH, "timeJournal");
        wasChangingOrder = isChanged(ORDER_PATH, "timeOrder");
        wasChangingDescription = isChanged(DESCRIPTION_PATH, "timeDescription");
        return wasChangingEmbodiment || wasChangingTmc || wasChangingClient || wasChangingWorker
                || wasChangingJournal || wasChangingOrder || wasChangingDescription;
    }

    public Map<String, Long> getTimes() {
        return times;
    }

    boolean isChanged(String filePath, String timeName) {
        Long newTime = new File(filePath).lastModified();
        Long oldTime = getTimes().put(timeName, newTime);
        return !newTime.equals(oldTime);
    }
}
