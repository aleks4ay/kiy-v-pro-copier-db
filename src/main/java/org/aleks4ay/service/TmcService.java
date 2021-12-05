package org.aleks4ay.service;

import org.aleks4ay.dao.BaseDao;
import org.aleks4ay.dao.ConnectionPool;
import org.aleks4ay.dao.TmcDao;
import org.aleks4ay.dbf.DbfReader;
import org.aleks4ay.dbf.TmcReader;
import org.aleks4ay.model.Tmc;
import org.aleks4ay.tools.Constants;

public class TmcService extends AbstractService<Tmc>{

    private final static String FILE_PATH = Constants.DBF_PATH + Constants.TMC_FILE;

    public TmcService(DbfReader<Tmc> reader, BaseDao<Tmc> tmcDao) {
        super(reader, tmcDao);
    }


    public static void main(String[] args) {
        TmcService tmcService = new TmcService(new TmcReader(), new TmcDao(ConnectionPool.getInstance()));
        tmcService.copyNewDataAbstract(FILE_PATH);
    }

    public Tmc findById(String id) {
        return findAbstractById(id);
    }
}