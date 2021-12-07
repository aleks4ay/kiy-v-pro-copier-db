package org.aleks4ay.copier.service;

import org.aleks4ay.copier.dao.BaseDao;
import org.aleks4ay.copier.dao.EmbodimentDao;
import org.aleks4ay.copier.dao.ConnectionPool;
import org.aleks4ay.copier.dbf.EmbodimentReader;
import org.aleks4ay.copier.dbf.DbfReader;
import org.aleks4ay.copier.model.Embodiment;
import org.aleks4ay.copier.tools.Constants;

public class EmbodimentService extends AbstractService<Embodiment> {

    private final static String FILE_PATH = Constants.DBF_PATH + Constants.EMBODIMENT_FILE;

    public EmbodimentService(DbfReader<Embodiment> reader, BaseDao<Embodiment> embodimentDao) {
        super(reader, embodimentDao);
    }

    public static void main(String[] args) {
        new EmbodimentService(new EmbodimentReader(), new EmbodimentDao(ConnectionPool.getInstance())).copyNewDataAbstract(FILE_PATH);
    }

    public Embodiment findById(String id) {
        return findAbstractById(id);
    }
}