package org.fjerp.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fjerp.modele.Data;
import org.vlinder.core.model.Model;
import org.vlinder.core.model.VColumn;
import org.vlinder.core.model.WorkUnit;

public class DaoImpl implements Dao
{
    private static final Logger log = LogManager.getLogger(DaoImpl.class);

    private DataSource dataSource;

    public DataSource getDataSource()
    {
        return this.dataSource;
    }

    public DaoImpl(DataSource dataSource)
    {
        this.dataSource = dataSource;
    }

    private List<Data> convert(final ResultSet resultSet) throws Exception
    {
        log.debug("convert");

        final List<Data> output = new LinkedList<Data>();

        // get metadate of resultset
        ResultSetMetaData metadata = resultSet.getMetaData();

        // allocate a string array for the column names of resultset
        String[] names = new String[metadata.getColumnCount()];

        // copy column names from metadata to name array
        for (int i = 1; i <= metadata.getColumnCount(); i++)
        {
            names[i - 1] = metadata.getColumnName(i).toLowerCase();
        }

        // iterate through rows of resultset
        while (
            resultSet.next()
        )
        {
            Data data = new Data(names);

            for (int i = 0; i < names.length; i++)
            {
                int type = metadata.getColumnType(i + 1);
                if (
                    type == java.sql.Types.LONGVARBINARY
                )
                {
                    byte[] content = resultSet.getBytes(names[i]);

                    data.setValue(i, names[i], content);
                } else
                {
                    Object value = resultSet.getObject(names[i]);
                    data.setValue(i, names[i], value);
                }
            }

            output.add(data);
        }

        return output;
    }

    @Override
    public Connection openConnection(DataSource dataSource) throws Exception
    {
        log.debug("openConnection");

        return dataSource.getConnection();
    }

    private void extract(final ResultSet resultSet, WorkUnit workUnit) throws Exception
    {
        log.debug("extract");

        // get metadate of resultset
        ResultSetMetaData meta = resultSet.getMetaData();

        // allocate a string array for the column names of resultset
        VColumn[] columns = new VColumn[meta.getColumnCount()];

        // copy column names from metadata to name array
        for (int i = 1; i <= meta.getColumnCount(); i++)
        {
            columns[i - 1] = new VColumn();

            columns[i - 1].setName(meta.getColumnName(i).toLowerCase());

            columns[i - 1].setSqlType(meta.getColumnType(i));
        }

        Model metaModel = new Model(Model.MODEL_TABLE);

        metaModel.setBricks(columns);

        workUnit.setMeta(metaModel);

        // rows allocation
        List<Model> rows = new LinkedList<Model>();

        // iterate through rows of resultset
        while (
            resultSet.next()
        )
        {
            Model row = new Model(meta.getColumnCount(), Model.MODEL_BLOCK);

            for (int i = 1; i <= meta.getColumnCount(); i++)
            {
                // column sql type
                int type = meta.getColumnType(i);

                Object content = null;

                if (
                    type == java.sql.Types.LONGVARBINARY
                )
                {
                    content = resultSet.getBytes(i);

                } else
                {
                    content = resultSet.getObject(i);
                }

                // save column value in row (model) instance
                row.setValue(i - 1, content);
            }

            rows.add(row);
        }

        workUnit.setRows(rows);
    }

    @Override
    public WorkUnit read(Connection connection, String sql, Object[] params) throws Exception
    {
        log.debug("read");

        PreparedStatement statement = null;

        ResultSet resultSet = null;

        WorkUnit workUnit = new WorkUnit();

        try
        {
            log.debug("sql=" + sql);

            statement = connection.prepareStatement(sql);

            for (int i = 0; i < params.length; i++)
            {
                statement.setObject(i + 1, params[i]);
            }

            resultSet = statement.executeQuery();

            extract(resultSet, workUnit);

            resultSet.close();

            resultSet = null;
        }

        catch (Exception e)
        {
            log.error(e);

            throw new Exception(e);
        }

        finally
        {
            if (
                statement != null
            )
            {
                try
                {
                    statement.close();

                    statement = null;
                }

                catch (Exception e)
                {
                    log.error(e);

                    throw new Exception(e);
                }
            }

        }

        return workUnit;
    }

    public List<Data> readBis(Connection connection, String sql, List<Object> parameters) throws Exception
    {
        log.debug("readBis");

        List<Data> dataSet = new LinkedList<Data>();

        PreparedStatement statement = null;

        try
        {
            log.debug("sql=" + sql);

            statement = connection.prepareStatement(sql);

            int i = 1;
            for (Iterator<Object> parameterIterator = parameters.iterator(); parameterIterator.hasNext();)
            {
                Object parameter = parameterIterator.next();

                statement.setObject(i, parameter);

                i++;
            }

            ResultSet resultSet = statement.executeQuery();

            dataSet = convert(resultSet);

            resultSet.close();
            resultSet = null;
        }

        catch (Exception e)
        {
            log.error(e);
            throw new Exception(e);
        }

        finally
        {
            if (
                statement != null
            )
            {
                try
                {
                    statement.close();
                    statement = null;
                }

                catch (Exception e)
                {
                    log.error(e);
                    throw new Exception(e);
                }
            }

        }

        return dataSet;
    }

    public List<Data> readBis(Connection connection, String sql, Object[] parameters) throws Exception
    {
        log.debug("readBis");

        List<Data> dataSet = new LinkedList<Data>();

        PreparedStatement statement = null;

        try
        {
            log.debug("sql=" + sql);

            statement = connection.prepareStatement(sql);

            for (int i = 0; i < parameters.length; i++)
            {
                statement.setObject(i + 1, parameters[i]);
            }

            ResultSet resultSet = statement.executeQuery();

            dataSet = convert(resultSet);

            resultSet.close();
            resultSet = null;
        }

        catch (Exception e)
        {
            log.error(e);
            throw new Exception(e);
        }

        finally
        {
            if (
                statement != null
            )
            {
                try
                {
                    statement.close();
                    statement = null;
                }

                catch (Exception e)
                {
                    log.error(e);
                    throw new Exception(e);
                }
            }

        }

        return dataSet;

    }

    @Override
    public Object[] write(Connection connection, String sql, Object[] parameters) throws Exception
    {
        log.debug("write");

        PreparedStatement statement = null;
        Object[] content = new Object[]
        {};
        ResultSet resultSet = null;

        try
        {
            statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            for (int i = 0; i < parameters.length; i++)
            {
                if (
                    parameters[i] instanceof File
                )
                {
                    File file = (File) parameters[i];
                    InputStream is = new FileInputStream(file);
                    statement.setBinaryStream(i + 1, is, (int) file.length());
                } else if (
                    parameters[i] instanceof Date
                )
                {
                    statement.setTimestamp(i + 1, new java.sql.Timestamp(((Date) parameters[i]).getTime()));
                } else
                    statement.setObject(i + 1, parameters[i]);
            }

            statement.executeUpdate();

            resultSet = statement.getGeneratedKeys();

            if (
                resultSet != null && resultSet.next()
            )
            {
                content = new Integer[]
                { resultSet.getInt(1) };
            }

        }

        catch (Exception e)
        {
            log.error(e);

            throw new Exception(e);
        }

        finally
        {
            if (
                resultSet != null
            )
            {
                resultSet.close(); // close result set
                resultSet = null;
            }

            if (
                statement != null
            )
            {
                try
                {
                    statement.close();
                    statement = null;
                }

                catch (Exception e)
                {
                    log.error(e);
                }
            }

        }

        return content;

    }

    public Object[] write(Connection connection, String sql, List<Object> parameters) throws Exception
    {
        log.debug("write");

        return new Object[]
        {};
    }

    @Override
    public void closeConnection(Connection connection) throws Exception
    {
        log.debug("closeConnection");

        if (
            connection != null
        )
        {
            try
            {
                connection.close();
                connection = null;
            }

            catch (Exception e)
            {
                log.error(e);
                throw new Exception(e);
            }
        }

    }
}
