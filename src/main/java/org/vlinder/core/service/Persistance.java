package org.vlinder.core.service;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fjerp.service.Dao;
import org.fjerp.service.DaoImpl;
import org.vlinder.core.model.Model;
import org.vlinder.core.model.VColumn;
import org.vlinder.core.model.VField;
import org.vlinder.core.model.WorkUnit;

public class Persistance
{
    private final static Logger log = LogManager.getLogger(Persistance.class);

    private static DataSource dataSource = null;

    public static DateFormat dateFormatLong = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    public static DateFormat dateFormatCourt = new SimpleDateFormat("dd/MM/yyyy");

    public static DaoImpl daoImpl = null;

    private static Connection connection = null;

    public static Map<String, Model> dictionary = new HashMap<String, Model>();

    public static VField findVField(Model classModel, String name)
    {
        log.debug("findVField");

        for (int i = 0; i < classModel.getBricks().length; i++)
        {
            VField field = (VField) classModel.getBricks()[i];

            if (
                field.getName().equals(name)
            )
            {
                return field;
            }
        }

        return null;
    }

    public static String extractStacktrace(Throwable throwable)
    {
        log.debug("extractStacktrace");

        StringBuilder builder = new StringBuilder();

        StackTraceElement[] traces = throwable.getStackTrace();

        for (StackTraceElement traceElement : traces)
        {
            builder.append("\tat " + traceElement + "\n");
        }

        return builder.toString();
    }

    // TODO load from configuration setup file
    private static void buildDictionary() throws Exception
    {
        log.debug("buildDictionary");

        // table
        Model refdataTable = new Model(2, Model.MODEL_TABLE);

        refdataTable.setName("refdata");

        // columns
        VColumn[] columns = new VColumn[refdataTable.getSize()];

        String[] colNames =
        { "id", "code" };

        int[] colTypes =
        { VColumn.SQL_INT, VColumn.SQL_VARCHAR };

        boolean[] nullableFlags =
        { false, false };

        boolean[] keyFlags =
        { true, false };

        for (int i = 0; i < columns.length; i++)
        {
            columns[i] = new VColumn(colNames[i], colTypes[i]);

            columns[i].setPseudo(colNames[i]);

            columns[i].setNullable(nullableFlags[i]);

            columns[i].setKey(keyFlags[i]);
        }

        refdataTable.setBricks(columns);

        refdataTable.setPseudo("org.vlinder.core.model.RefData");

        dictionary.put("refdata", refdataTable);

        // class
        Model refdataClass = new Model(Model.MODEL_CLASS);

        refdataClass.setName("org.vlinder.core.model.RefData");

        refdataClass.setPseudo("refdata");

        Class<?> clazz = Class.forName("org.vlinder.core.model.RefData");

        // fields
        List<Field> fieldList = new LinkedList<Field>();

        while (
            clazz != null
        )
        {
            Field[] declaredFields = clazz.getDeclaredFields();

            fieldList.addAll(Arrays.asList(declaredFields));

            clazz = clazz.getSuperclass();
        }

        VField[] vfields = new VField[fieldList.size()];

        for (int i = 0; i < fieldList.size(); i++)
        {
            Field javaField = fieldList.get(i);

            vfields[i] = new VField(javaField.getName());

            vfields[i].setJavaField(javaField);

        }

        refdataClass.setBricks(vfields);

        dictionary.put(refdataClass.getName(), refdataClass);

    }

    public static void setup()
    {
        log.debug("setup");

        try
        {
            BasicDataSource basicDataSource = new BasicDataSource();

            basicDataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
            basicDataSource.setUsername("root");
            basicDataSource.setPassword("root");
            // basicDataSource.setUrl("jdbc:mysql://localhost:3306/formation?useLegacyDatetimeCode=false&serverTimezone=America/New_York");
            basicDataSource.setUrl(
                    "jdbc:mysql://localhost:3306/vlinder?useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC");
            basicDataSource.setMaxIdle(5);
            basicDataSource.setInitialSize(5);
            basicDataSource.setValidationQuery("SELECT 1");

            dataSource = basicDataSource;

            daoImpl = new DaoImpl(basicDataSource);

            connection = daoImpl.openConnection(dataSource);

            buildDictionary();

        }

        catch (Exception e)
        {
            log.error(e);

            log.error("Stacktrace:\n" + extractStacktrace(e));
        }
    }

    public static Connection getConnection()
    {
        log.debug("getConnection");

        return connection;
    }

    public static Dao getDao()
    {
        log.debug("getDao");

        return daoImpl;
    }

    public static DataSource getDataSource()
    {
        log.debug("getDataSource");

        return dataSource;
    }

    public static void close()
    {
        log.debug("close");

        if (
            connection != null
        )
        {
            try
            {
                daoImpl.closeConnection(connection);

                connection = null;
            }

            catch (Exception e)
            {
                log.error(e);
            }
        }
    }

    public static String oterBlancs(String entree)
    {
        log.debug("oterBlancs");

        String sortie = entree;

        if (
            sortie == null
        )
        {
            sortie = "";
        } else
        {
            sortie = sortie.replaceAll("^\\s+", "");

            sortie = sortie.replaceAll("\\s+$", "");
        }

        return sortie;
    }

    public static boolean validerDateCourte(String valeur)
    {
        log.debug("validerDateCourte");

        boolean etat = true;

        try
        {
            if (
                valeur != null || valeur.isEmpty()
            )
            {
                etat = false;
            }

            Date date = dateFormatCourt.parse(valeur);
        }

        catch (Exception e)
        {
            log.error(e);

            etat = false;
        }

        return etat;
    }

    public static boolean validerDateLongue(String valeur)
    {
        log.debug("validerDateLongue");

        boolean etat = true;

        try
        {
            if (
                valeur == null || valeur.isEmpty()
            )
            {
                etat = false;
            }

            Date date = dateFormatLong.parse(valeur);
        }

        catch (Exception e)
        {
            log.error(e);

            etat = false;
        }

        return etat;
    }

    public static boolean validerEntier(String valeur)
    {
        log.debug("validerEntier");

        boolean etat = true;

        try
        {
            if (
                valeur == null || valeur.isEmpty()
            )
            {
                etat = false;
            }

            int n = Integer.parseInt(valeur);
        }

        catch (Exception e)
        {
            log.error(e);

            etat = false;
        }

        return etat;
    }

    // TODO
    public static void defineMasters()
    {
        log.debug("defineMasters");

    }

    private static void processValues(Object in, Model classModel, Model tableModel, int type, StringBuilder sql,
            List<Object> paramList) throws Exception
    {
        log.debug("processValues");

        boolean flag = false;

        for (int i = 0; i < tableModel.getBricks().length; i++)
        {
            VColumn vcolumn = (VColumn) tableModel.getBricks()[i];

            VField vfield = findVField(classModel, vcolumn.getPseudo());

            Field javaField = vfield.getJavaField();

            javaField.setAccessible(true);

            if (
                vcolumn.isAuto() == false && vcolumn.isNullable() == false
            )
            {
                if (
                    flag == true
                )
                {
                    sql.append(",");
                }

                if (
                    javaField.getType().equals(java.lang.String.class)
                )
                {
                    String strValue = (String) javaField.get(in);

                    // TODO nullable check
                    if (
                        strValue == null
                    )
                    {
                        strValue = "";
                    }

                    // TODO check length

                    // sql.append("\"");
                    // sql.append(strValue);
                    // sql.append("\"");

                    sql.append("?");

                    paramList.add(strValue);

                } else if (
                    javaField.getType().equals(int.class)
                )
                {
                    int intValue = (Integer) javaField.get(in);

                    // TODO nullable check

                    // TODO check length
                    // sql.append("");
                    // sql.append(Integer.toString(intValue));
                    // sql.append("");

                    sql.append("?");

                    paramList.add(intValue);
                } else if (
                    javaField.getType().equals(java.util.Date.class)
                )
                {
                    Date dateValue = (Date) javaField.get(in);

                    sql.append("?");

                    paramList.add(dateValue);
                } else if (
                    javaField.getType().equals(float.class)
                )
                {
                    float floatValue = (Float) javaField.get(in);

                    sql.append("?");

                    paramList.add(floatValue);
                } else if (
                    javaField.getType().equals(double.class)
                )
                {
                    double doubleValue = (Double) javaField.get(in);

                    sql.append("?");

                    paramList.add(doubleValue);
                }
                // TODO check other java types (byte[], boolean)

                flag = true;
            }

        }

    }

    private static String processNames(Object in, Model classModel, Model tableModel, int type) throws Exception
    {
        log.debug("processNames");

        StringBuilder sql = new StringBuilder("");

        boolean flag = false;

        for (int i = 0; i < tableModel.getBricks().length; i++)
        {
            VColumn column = (VColumn) tableModel.getBricks()[i];

            if (
                !(column.isAuto() == true && type == VColumn.SQL_INSERT)
            )
            {
                if (
                    flag == true
                )
                {
                    sql.append(",");
                }

                sql.append(column.getName());

                flag = true;
            }

        }

        return sql.toString();
    }

    // myql specific string conversion
    // TODO maxLength for string to be implemented
    private static String extractStrValue(Object value)
    {
        log.debug("extractStrValue");

        StringBuilder sql = new StringBuilder();

        if (
            value.getClass().equals(int.class)
        )
        {

            sql.append(Integer.toString((int) value));

        } else if (
            value.getClass().equals(String.class)
        )
        {
            sql.append((String) value);
        }
        // TODO Date, byte[], boolean, ...

        return sql.toString();
    }

    private static void processWhere(Object in, Model classModel, Model tableModel, int type, StringBuilder sql,
            List<Object> params) throws Exception
    {
        log.debug("processWhere");

        for (int i = 0; i < tableModel.getBricks().length; i++)
        {
            VColumn column = (VColumn) tableModel.getBricks()[i];

            VField vfield = findVField(classModel, column.getPseudo());

            Field javaField = vfield.getJavaField();

            javaField.setAccessible(true);

            Object value = javaField.get(in);

            if (
                (column.isKey() == true && ((type == VColumn.SQL_UPDATE || type == VColumn.SQL_DELETE)))
                        || (value != null)
            )
            {
                sql.append("and ");

                sql.append(column.getName());

                sql.append("=? ");

                params.add(value);
            }
        }

    }

    public static WorkUnit process(Object in, Model classModel, Model tableModel, int type) throws Exception
    {
        log.debug("process");

        // TODO validateStore

        WorkUnit workUnit = new WorkUnit();

        StringBuilder sql = new StringBuilder("");

        List<Object> params = new LinkedList<Object>();

        // insert
        if (
            type == VColumn.SQL_INSERT
        )
        {
            sql.append("insert into " + tableModel.getName() + "(");

            sql.append(processNames(in, classModel, tableModel, type));

            sql.append(") values(");

            processValues(in, classModel, tableModel, type, sql, params);

            sql.append(" ) ");

        } else
        // update
        if (
            type == VColumn.SQL_UPDATE
        )
        {
            sql.append("update " + tableModel.getName() + " set ");

            processValues(in, classModel, tableModel, type, sql, params);

            sql.append(" where (1=1) ");

            processWhere(in, classModel, tableModel, type, sql, params);

        } else
        // delete
        if (
            type == VColumn.SQL_DELETE
        )
        {
            sql.append("delete from " + tableModel.getName() + " where (1=1) ");

            processWhere(in, classModel, tableModel, type, sql, params);
        }
        // TODO select
        if (
            type == VColumn.SQL_SELECT
        )
        {
            processNames(in, classModel, tableModel, type);

            sql.append(" from " + tableModel.getName() + " where (1=1) ");

            processWhere(in, classModel, tableModel, type, sql, params);
        }

        workUnit.setSql(sql.toString());

        workUnit.setAct(type);

        Object[] paramsArray = new Object[params.size()];

        params.toArray(paramsArray);

        workUnit.setIn(paramsArray);

        return workUnit;
    }

    // TODO
    public static WorkUnit processBis(Object in, Model classModel, Model tableModel, int type) throws Exception
    {
        log.debug("processBis");

        // TODO validateStore

        WorkUnit workUnit = new WorkUnit();

        StringBuilder sql = new StringBuilder("");

        List<Object> params = new LinkedList<Object>();

        // insert
        if (
            type == VColumn.SQL_INSERT
        )
        {
            sql.append("insert into " + tableModel.getName() + "(");

            boolean flag = false;

            for (int i = 0; i < tableModel.getBricks().length; i++)
            {
                VColumn column = (VColumn) tableModel.getBricks()[i];

                if (
                    column.isAuto() == false && column.isNullable() == false
                )
                {
                    if (
                        flag == true
                    )
                    {
                        sql.append(",");
                    }

                    sql.append(column.getName());

                    flag = true;
                }

            }

            sql.append(") values(");

            Class<?> clazz = Class.forName(classModel.getName());

            // TODO check clazz.equals(Object.getClass())
            if (
                clazz.equals(in.getClass()) == false
            )
            {
                log.error("in.class doesn't match master class");

            }

            // generate values
            flag = false;

            for (int i = 0; i < tableModel.getBricks().length; i++)
            {
                VColumn vcolumn = (VColumn) tableModel.getBricks()[i];

                VField vfield = findVField(classModel, vcolumn.getPseudo());

                Field javaField = vfield.getJavaField();

                javaField.setAccessible(true);

                if (
                    vcolumn.isAuto() == false && vcolumn.isNullable() == false
                )
                {
                    if (
                        flag == true
                    )
                    {
                        sql.append(",");
                    }

                    if (
                        javaField.getType().equals(java.lang.String.class)
                    )
                    {
                        String strValue = (String) javaField.get(in);

                        // TODO nullable check
                        if (
                            strValue == null
                        )
                        {
                            strValue = "";
                        }

                        // TODO check length

                        // sql.append("\"");
                        // sql.append(strValue);
                        // sql.append("\"");

                        sql.append("?");

                        params.add(strValue);

                    } else if (
                        javaField.getType().equals(int.class)
                    )
                    {
                        int intValue = (Integer) javaField.get(in);

                        // TODO nullable check

                        // TODO check length
                        // sql.append("");
                        // sql.append(Integer.toString(intValue));
                        // sql.append("");

                        sql.append("?");

                        params.add(intValue);
                    }
                    // TODO check other java types (date, byte[], boolean, float/double, ...)

                    flag = true;
                }

            }

            sql.append(" ) ");

        }

        // TODO update

        // TODO select

        // TODO delete

        workUnit.setSql(sql.toString());

        workUnit.setAct(type);

        Object[] paramsArray = new Object[params.size()];

        params.toArray(paramsArray);

        workUnit.setIn(paramsArray);

        return workUnit;
    }

    // TODO
    public static void populateObject(Object target, WorkUnit workUnit)
    {
        log.debug("populateObject");
    }

}
