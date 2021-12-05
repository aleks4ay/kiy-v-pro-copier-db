package org.aleks4ay.service;

import org.aleks4ay.dao.BaseDao;
import org.aleks4ay.dao.ConnectionPool;
import org.aleks4ay.dao.JournalDao;
import org.aleks4ay.dbf.DbfReader;
import org.aleks4ay.dbf.JournalReader;
import org.aleks4ay.model.Journal;
import org.aleks4ay.tools.Constants;

public class JournalService extends AbstractService<Journal> {

    private final static String FILE_PATH = Constants.DBF_PATH + Constants.JOURNAL_FILE;

    public JournalService(DbfReader<Journal> reader, BaseDao<Journal> journalDao) {
        super(reader, journalDao);
    }

    public static void main(String[] args) {
        new JournalService(new JournalReader(), new JournalDao(ConnectionPool.getInstance())).copyNewDataAbstract(FILE_PATH);
    }

    public Journal findById(String id) {
        return findAbstractById(id);
    }
}