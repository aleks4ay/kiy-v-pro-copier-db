package org.aleks4ay.copier.tools;

public final class ConstantsSql {

    public static final String TMC_GET_ONE = "SELECT * FROM tmc WHERE id = ?;";
    public static final String TMC_GET_ALL = "SELECT * FROM tmc;";
    public static final String TMC_DELETE = "DELETE FROM tmc WHERE id = ?;";
    public static final String TMC_CREATE = "INSERT INTO tmc (id_parent, code, descr, " +
            "size_a, size_b, size_c, is_folder, descr_all, type, id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
    public static final String TMC_UPDATE = "UPDATE tmc SET id_parent=?, code=?, descr=?, " +
            "size_a=?, size_b=?, size_c=?, is_folder=?, descr_all=?, type=? WHERE id = ?;";

    public static final String JOURNAL_GET_ONE = "SELECT * FROM journal WHERE id = ?;";
    public static final String JOURNAL_GET_ALL = "SELECT * FROM journal;";
    public static final String JOURNAL_DELETE = "DELETE FROM journal WHERE id = ?;";
    public static final String JOURNAL_CREATE = "INSERT INTO journal (doc_number, t_create, id) VALUES (?, ?, ?);";
    public static final String JOURNAL_UPDATE = "UPDATE journal SET doc_number=?, t_create=? where id=?;";

    public static final String CLIENT_GET_ONE = "SELECT * FROM client WHERE id = ?;";
    public static final String CLIENT_GET_ALL = "SELECT * FROM client;";
    public static final String CLIENT_DELETE = "DELETE FROM client WHERE id = ?;";
    public static final String CLIENT_CREATE = "INSERT INTO client (name, id) VALUES (?, ?);";
    public static final String CLIENT_UPDATE = "UPDATE client SET name=? where id=?;";

    public static final String WORKER_GET_ONE = "SELECT * FROM worker WHERE id = ?;";
    public static final String WORKER_GET_ALL = "SELECT * FROM worker;";
    public static final String WORKER_DELETE = "DELETE FROM worker WHERE id = ?;";
    public static final String WORKER_CREATE = "INSERT INTO worker (name, id) VALUES (?, ?);";
    public static final String WORKER_UPDATE = "UPDATE worker SET name=? where id=?;";

    public static final String ORDER_GET_ONE = "SELECT * FROM orders WHERE id = ?;";
    public static final String ORDER_GET_ALL = "SELECT * FROM orders;";
    public static final String ORDER_GET_ALL_ID = "SELECT id FROM orders;";
    public static final String ORDER_DELETE = "DELETE FROM orders WHERE id = ?;";
    public static final String ORDER_CREATE = "insert into orders (id_client, id_manager, duration, t_factory, price, id)" +
            " VALUES (?, ?, ?, ?, ?, ?);";
    public static final String ORDER_TIME_CREATE = "insert into order_time (id_order, status, time) VALUES (?, ?, ?);";
    public static final String ORDER_UPDATE = "UPDATE orders " +
            "SET id_client=?, id_manager=?, duration=?, t_factory=?, price=? where id=?;";

    public static final String DESCRIPTION_GET_ONE = "SELECT * FROM descriptions WHERE id = ?;";
    public static final String DESCRIPTION_GET_ALL = "SELECT * FROM descriptions;";
    public static final String DESCRIPTION_DELETE = "DELETE FROM descriptions WHERE id = ?;";
    public static final String DESCRIPTION_CREATE = "INSERT INTO descriptions (id_order, position, id_tmc, quantity, descr_second, " +
            "size_a, size_b, size_c, embodiment, id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
    public static final String DESCRIPTION_TIME_CREATE = "insert into description_time (id_description, status, time) VALUES (?, ?, ?);";

    public static final String DESCRIPTION_UPDATE = "UPDATE descriptions SET id_order=?, position=?, id_tmc=?, quantity=?, descr_second=?, " +
            "size_a=?, size_b=?, size_c=?, embodiment=? WHERE id = ?;";

    public static final String EMBODIMENT_GET_ONE = "SELECT * FROM embodiment WHERE id = ?;";
    public static final String EMBODIMENT_GET_ALL = "SELECT * FROM embodiment;";
    public static final String EMBODIMENT_DELETE = "DELETE FROM embodiment WHERE id = ?;";
    public static final String EMBODIMENT_CREATE = "INSERT INTO embodiment (description, id) VALUES (?, ?);";
    public static final String EMBODIMENT_UPDATE = "UPDATE embodiment SET description=? where id=?;";
}
