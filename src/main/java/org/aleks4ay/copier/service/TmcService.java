package org.aleks4ay.copier.service;

import org.aleks4ay.copier.dao.BaseDao;
import org.aleks4ay.copier.dao.ConnectionPool;
import org.aleks4ay.copier.dao.TmcDao;
import org.aleks4ay.copier.dbf.DbfReader;
import org.aleks4ay.copier.dbf.TmcReader;
import org.aleks4ay.copier.model.Tmc;
import org.aleks4ay.copier.tools.Constants;

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