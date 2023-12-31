package org.fjerp.service;

import java.sql.Connection;

import javax.sql.DataSource;

import org.vlinder.core.model.WorkUnit;

public interface Dao
{
    public Connection openConnection(DataSource dataSource) throws Exception;

    public void closeConnection(Connection connection) throws Exception;

    public WorkUnit read(Connection connection, String sql, Object[] params) throws Exception;

    public Object[] write(Connection connection, String sql, Object[] parameters) throws Exception;

}
