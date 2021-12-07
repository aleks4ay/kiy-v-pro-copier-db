package org.aleks4ay.copier.dao;

import org.aleks4ay.copier.model.Tmc;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.*;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

public class TmcDaoTest {

    private final ConnectionBase connectionPoolTest = ConnectionPoolForTest.getInstance();
    private TmcDao dao;
    private Connection conn;

    @Before
    public void setUp() throws Exception {
        conn = connectionPoolTest.getConnection();
        dao = new TmcDao(connectionPoolTest);
        Statement statement = conn.createStatement();
        statement.execute("delete from tmc where true;");
        statement.execute("insert into tmc (id, id_parent, code, descr, size_a, size_b, size_c, is_folder, descr_all, type) " +
                "values ('01', '101', 'code1', 'descr1', 101, 102, 103, 1, 'descr all 1', 'kb'), " +
                " ('02', '102', 'code2', 'descr2', 201, 202, 203, 1, 'descr all 2', 'kb'), " +
                " ('03', '103', 'code3', 'descr3', 301, 302, 303, 1, 'descr all 3', 'kb');");
    }

    @After
    public void tearDown() {
        connectionPoolTest.closeConnection(conn);
    }

    @Test
    public void findByIdIfExistTest() throws SQLException {
        Optional<Tmc> actual = dao.findById("01");
        assertTrue(actual.isPresent());
    }

    @Test
    public void findByIdIfNotExistTest() throws SQLException {
        Optional<Tmc> actual = dao.findById("04");
        assertFalse(actual.isPresent());
    }

    @Test
    public void findAllTest(){
        List<Tmc> actual = dao.findAll();
        assertEquals(3, actual.size());
    }

    @Test
    public void updateAll() {
        Tmc tmc1 = new Tmc("01", "501", "code4", 101, 102, 103, "descr1", 1, "descr all 6", "ceh");
        Tmc tmc2 = new Tmc("02", "601", "code5", 101, 102, 103, "descr2", 1, "descr all 7", "ceh");
        Tmc tmc3 = new Tmc("03", "701", "code3", 101, 102, 103, "descr3", 1, "descr all 8", "ceh");
        List<Tmc> tmcs = Arrays.asList(tmc1, tmc2, tmc3);
        boolean actual = dao.updateAll(tmcs);
        assertTrue(actual);
    }

    @Test
    public void updateAllWhenNotExist() {
        Tmc tmc1 = new Tmc("01", "501", "code4", 101, 102, 103, "descr1", 1, "descr all 6", "ceh");
        Tmc tmc2 = new Tmc("05", "601", "code5", 101, 102, 103, "descr2", 1, "descr all 7", "ceh");
        Tmc tmc3 = new Tmc("03", "701", "code3", 101, 102, 103, "descr3", 1, "descr all 8", "ceh");
        List<Tmc> tmcs = Arrays.asList(tmc1, tmc2, tmc3);
        boolean actual = dao.updateAll(tmcs);
        assertFalse(actual);
    }

    @Test
    public void createAll() {
        Tmc tmc1 = new Tmc("04", "501", "code4", 101, 102, 103, "descr1", 1, "descr all 6", "ceh");
        Tmc tmc2 = new Tmc("05", "601", "code5", 101, 102, 103, "descr2", 1, "descr all 7", "ceh");
        Tmc tmc3 = new Tmc("06", "701", "code3", 101, 102, 103, "descr3", 1, "descr all 8", "ceh");
        List<Tmc> tmcs = Arrays.asList(tmc1, tmc2, tmc3);
        boolean actual = dao.createAll(tmcs);
        assertTrue(actual);
    }

    @Test
    public void createAllWhenAlreadyExist() {
        Tmc tmc1 = new Tmc("04", "501", "code4", 101, 102, 103, "descr1", 1, "descr all 6", "ceh");
        Tmc tmc2 = new Tmc("05", "601", "code5", 101, 102, 103, "descr2", 1, "descr all 7", "ceh");
        Tmc tmc3 = new Tmc("02", "701", "code3", 101, 102, 103, "descr3", 1, "descr all 8", "ceh");
        List<Tmc> tmcs = Arrays.asList(tmc1, tmc2, tmc3);
        assertFalse(dao.createAll(tmcs));
    }

    @Test
    public void deleteAllWhenExist() {
        Tmc tmc1 = new Tmc("01", "501", "code4", 101, 102, 103, "descr1", 1, "descr all 6", "ceh");
        Tmc tmc2 = new Tmc("02", "601", "code5", 101, 102, 103, "descr2", 1, "descr all 7", "ceh");
        List<Tmc> tmcs = Arrays.asList(tmc1, tmc2);
        boolean actual = dao.deleteAll(tmcs);
        assertTrue(actual);
    }

    @Test
    public void deleteAllWhenNotExist() {
        Tmc tmc1 = new Tmc("01", "501", "code4", 101, 102, 103, "descr1", 1, "descr all 6", "ceh");
        Tmc tmc2 = new Tmc("05", "601", "code5", 101, 102, 103, "descr2", 1, "descr all 7", "ceh");
        List<Tmc> tmcs = Arrays.asList(tmc1, tmc2);
        boolean actual = dao.deleteAll(tmcs);
        assertFalse(actual);
    }
}
