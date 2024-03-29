package org.aleks4ay.copier.dao.mapper;

import org.aleks4ay.copier.model.Journal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JournalMapper implements ObjectMapper<Journal> {
    @Override
    public Journal extractFromResultSet(ResultSet rs) throws SQLException {
        return new Journal(
                rs.getString("id"),
                rs.getString("doc_number"),
                rs.getTimestamp("t_create")
                );
    }

    @Override
    public void insertToResultSet(PreparedStatement statement, Journal journal) throws SQLException {
        statement.setString(3, journal.getId());
        statement.setString(1, journal.getDocNumber());
        statement.setTimestamp(2, journal.getDateCreate());
    }

    @Override
    public void insertIdToResultSet(PreparedStatement statement, Journal journal) throws SQLException {
        statement.setString(1, journal.getId());
    }
}