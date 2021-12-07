package org.aleks4ay.copier.service;

import org.aleks4ay.copier.dao.ConnectionPool;
import org.aleks4ay.copier.tools.Constants;
import org.aleks4ay.copier.dbf.DbfReader;
import org.aleks4ay.copier.dao.BaseDao;

import org.aleks4ay.copier.model.Worker;
import org.aleks4ay.copier.dao.WorkerDao;
import org.aleks4ay.copier.dbf.WorkerReader;

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