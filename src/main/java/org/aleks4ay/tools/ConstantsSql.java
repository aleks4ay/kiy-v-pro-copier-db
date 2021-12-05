package org.aleks4ay.tools;

public final class ConstantsSql {

    public static final String TMC_GET_ONE = "SELECT * FROM tmc WHERE id = ?;";
    public static final String TMC_GET_ALL = "SELECT * FROM tmc;";
    public static final String TMC_DELETE = "DELETE FROM tmc WHERE id = ?;";
    public static final String TMC_CREATE = "INSERT INTO tmc (id_parent, code, descr, " +
            "size_a, size_b, size_c, is_folder, descr_all, type, id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
    public static final String TMC_UPDATE = "UPDATE tmc SET id_parent=?, code=?, descr=?, " +
            "size_a=?, size_b=?, size_c=?, is_folder=?, descr_all=?, type=? WHERE id = ?;";

    public static final String JOURNAL_GET_ONE = "SELECT * FROM journal WHERE iddoc = ?;";
    public static final String JOURNAL_GET_ALL = "SELECT * FROM journal;";
    public static final String JOURNAL_DELETE = "DELETE FROM journal WHERE iddoc = ?;";
    public static final String JOURNAL_CREATE = "INSERT INTO journal (docno, t_create, iddoc) VALUES (?, ?, ?);";
    public static final String JOURNAL_UPDATE = "UPDATE journal SET docno=?, t_create=? where iddoc=?;";

}
