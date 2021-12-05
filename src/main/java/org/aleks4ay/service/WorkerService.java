package org.aleks4ay.service;

import org.aleks4ay.dao.ConnectionPool;
import org.aleks4ay.tools.Constants;
import org.aleks4ay.dbf.DbfReader;
import org.aleks4ay.dao.BaseDao;

import org.aleks4ay.model.Worker;
import org.aleks4ay.dao.WorkerDao;
import org.aleks4ay.dbf.WorkerReader;

public class WorkerService extends AbstractService<Worker> {

    private final static String FILE_PATH = Constants.DBF_PATH + Constants.WORKER_FILE;

    public WorkerService(DbfReader<Worker> reader, BaseDao<Worker> workerDao) {
        super(reader, workerDao);
    }

    public static void main(String[] args) {
        new WorkerService(new WorkerReader(), new WorkerDao(ConnectionPool.getInstance())).copyNewDataAbstract(FILE_PATH);
    }

    public Worker findById(String id) {
        return findAbstractById(id);
    }
}