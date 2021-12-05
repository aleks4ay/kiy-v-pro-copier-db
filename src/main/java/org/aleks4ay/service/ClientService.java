package org.aleks4ay.service;

import org.aleks4ay.dao.BaseDao;
import org.aleks4ay.dao.ConnectionPool;
import org.aleks4ay.dao.ClientDao;
import org.aleks4ay.dbf.DbfReader;
import org.aleks4ay.dbf.ClientReader;
import org.aleks4ay.model.Client;
import org.aleks4ay.tools.Constants;

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