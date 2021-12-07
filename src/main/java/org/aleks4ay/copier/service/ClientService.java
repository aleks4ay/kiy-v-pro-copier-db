package org.aleks4ay.copier.service;

import org.aleks4ay.copier.dao.BaseDao;
import org.aleks4ay.copier.dao.ConnectionPool;
import org.aleks4ay.copier.dao.ClientDao;
import org.aleks4ay.copier.dbf.DbfReader;
import org.aleks4ay.copier.dbf.ClientReader;
import org.aleks4ay.copier.model.Client;
import org.aleks4ay.copier.tools.Constants;

public class ClientService extends AbstractService<Client> {

    private final static String FILE_PATH = Constants.DBF_PATH + Constants.CLIENT_FILE;

    public ClientService(DbfReader<Client> reader, BaseDao<Client> clientDao) {
        super(reader, clientDao);
    }

    public static void main(String[] args) {
        new ClientService(new ClientReader(), new ClientDao(ConnectionPool.getInstance())).copyNewDataAbstract(FILE_PATH);
    }

    public Client findById(String id) {
        return findAbstractById(id);
    }
}