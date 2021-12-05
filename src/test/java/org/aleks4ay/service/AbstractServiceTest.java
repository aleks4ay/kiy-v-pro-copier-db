package org.aleks4ay.service;

import org.aleks4ay.dao.ConnectionBase;
import org.aleks4ay.dao.ConnectionPoolForTest;
import org.aleks4ay.dao.TmcDao;
import org.aleks4ay.dbf.TmcReader;
import org.aleks4ay.model.Tmc;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.Statement;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.*;

public class AbstractServiceTest {

    private final ConnectionBase connectionPoolTest = ConnectionPoolForTest.getInstance();
    private TmcDao dao;
    private Connection conn;
    private TmcService service = new TmcService(new TmcReader(), new TmcDao(connectionPoolTest));

    Tmc tmcEqual_1 = new Tmc("01", "X3", "128", 451, 371, 531, "Хот-дог АПХ-П", 2, "Апарат для хот-догів АПХ-П", "VA");
    Tmc tmcEqual_2 = new Tmc("02", "X3", "128", 452, 372, 532, "Хот-дог АПХ-П", 2, "Апарат для хот-догів АПХ-П", "VA");
    Tmc tmcNeedChangeBefore = new Tmc("   15D", "X3", "128", 1450, 370, 1530, "Хот-дог АПХ-П", 2, "Апарат для хот-догів АПХ-П", "    VA");
    Tmc tmcNew_1 = new Tmc("04", "X3", "128", 452, 372, 532, "Хот-дог АПХ-П", 2, "Апарат для хот-догів АПХ-П", "VA");
    Tmc tmcNew_2 = new Tmc("05", "X3", "128", 452, 372, 532, "Хот-дог АПХ-П", 2, "Апарат для хот-догів АПХ-П", "VA");
    Tmc tmcNeedChangeAfter = new Tmc("   15D", "    X3", "  128", 450, 370, 530, "Хот-дог АПХ-П", 2, "Апарат для хот-догів АПХ-П", "    VA");
    Tmc tmcNeedDelete_1 = new Tmc("    6B", "   25X", "3111", 7800, 7700, 8850, "Стіл", 2, "Стіл", "    VA");
    Tmc tmcNeedDelete_2 = new Tmc("    6C", "   25X", "3112", 2000, 2700, 2850, "Стіл", 2, "Стіл", "    VA");

    private final String SQL_RIGHT_OLD_DATA = "insert into tmc (id, id_parent, code, descr, size_a, size_b, size_c, is_folder, descr_all, type) " +
            "values ('   15D', '    X3', '  128', 'Хот-дог АПХ-П', 450, 370, 530, 2, 'Апарат для хот-догів АПХ-П', '    VA'), " +
            " ('   157', '    X3', '  129', 'Хот-дог АПХ-Ш', 400, 280, 2800, 2, 'Апарат для хот-догів АПХ-Ш', '    VA'), " +
            "('    6A', '   25X', ' 3110', 'Стіл', 800, 700, 850, 2, 'Стіл', '    VA'), " +
            " ('    69', '   25X', ' 3109', 'Стіл', 700, 700, 850, 2, 'Стіл', '    VA');";
    private final String SQL_WRONG_OLD_DATA = "insert into tmc (id, id_parent, code, descr, size_a, size_b, size_c, is_folder, descr_all, type) " +
            "values ('    6B', '   25X', ' 3111', 'Стіл', 7800, 7700, 8850, 2, 'Стіл', '    VA'), " +
            " ('    6C', '   25X', ' 3112', 'Стіл', 2000, 2700, 2850, 2, 'Стіл', '    VA');";

    @Before
    public void setUp() throws Exception {
        conn = connectionPoolTest.getConnection();
        dao = new TmcDao(connectionPoolTest);
        Statement statement = conn.createStatement();
        statement.execute("delete from tmc where true;");
        statement.execute(SQL_RIGHT_OLD_DATA);
        statement.execute(SQL_WRONG_OLD_DATA);
    }

    @After
    public void tearDown() {
        connectionPoolTest.closeConnection(conn);
    }

    @Test
    public void findAbstractById() {
        Tmc actual = service.findById("   15D");
        Tmc expected = new Tmc("   15D", "    X3", "  128", 450, 370, 530, "Хот-дог АПХ-П", 2, "Апарат для хот-догів АПХ-П", "    VA");
        assertEquals(expected, actual);
    }

    @Test
    public void readNewData() throws Exception{
        List<Tmc> tmcs = service.readNewData("src/test/java/org/aleks4ay/service/SC302_test.DBF");
        tmcs.forEach(System.out::println);
        assertEquals(16, tmcs.size());
    }

    @Test
    public void readOldData() {
        Map<String, Tmc> oldDataMap = service.readOldData();
        oldDataMap.values().forEach(System.out::println);
        assertEquals(6, oldDataMap.size());
    }

    @Test
    public void deleteUnique() {
        Map<String, Tmc> oldDataMap = Stream.of(tmcEqual_1, tmcEqual_2, tmcNeedChangeBefore, tmcNeedDelete_1, tmcNeedDelete_2)
                .collect(Collectors.toMap(Tmc::getId, t -> t));

        List<Tmc> newTmc = Stream.of(tmcEqual_1, tmcEqual_2, tmcNeedChangeAfter, tmcNew_1, tmcNew_2)
                .collect(Collectors.toList());

        service.deleteNotUnique(newTmc, oldDataMap);
        assertEquals(3, newTmc.size());
        assertEquals(3, oldDataMap.size());
    }

    @Test
    public void updateAll() {
        Map<String, Tmc> oldDataMap = Stream.of(tmcNeedChangeBefore, tmcNeedDelete_1, tmcNeedDelete_2)
                .collect(Collectors.toMap(Tmc::getId, t -> t));

        List<Tmc> newTmc = Stream.of(tmcNeedChangeAfter, tmcNew_1, tmcNew_2)
                .collect(Collectors.toList());

        service.updateAll(newTmc, oldDataMap);

        assertEquals(2, newTmc.size());
        assertEquals(2, oldDataMap.size());
        assertEquals(tmcNeedChangeAfter, service.findById("   15D"));
    }

    @Test
    public void createAll() {
        List<Tmc> newTmc = Stream.of(tmcNew_1, tmcNew_2)
                .collect(Collectors.toList());

        service.createAll(newTmc);
        assertNotNull(service.findById("05"));
    }

    @Test
    public void deleteAll() {
        Map<String, Tmc> oldDataMap = Stream.of(tmcNeedDelete_1, tmcNeedDelete_2)
                .collect(Collectors.toMap(Tmc::getId, t -> t));

        assertNotNull(service.findById("    6B"));
        service.deleteAll(oldDataMap);
        assertNull(service.findById("    6B"));
    }
/*
    @Test
    public void copyNewDataAbstract() {
    }*/
}