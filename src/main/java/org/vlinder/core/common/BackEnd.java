package org.vlinder.core.common;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fjerp.service.Dao;
import org.vlinder.core.model.Brick;
import org.vlinder.core.model.Model;
import org.vlinder.core.model.RefData;
import org.vlinder.core.model.VColumn;
import org.vlinder.core.model.VField;
import org.vlinder.core.model.WorkUnit;
import org.vlinder.core.service.Persistance;

public abstract class BackEnd
{
    static final Logger log = LogManager.getLogger(BackEnd.class);

    private List<Object> dataset = new LinkedList<Object>();

    private int offset, size, limit, level;

    protected Stack<Brick> notices = new Stack<Brick>();

    public void pushMessage(Throwable throwable)
    {
        log.debug("pushMessage");

        StringBuilder builder = new StringBuilder();

        StackTraceElement[] traces = throwable.getStackTrace();

        for (StackTraceElement traceElement : traces)
        {
            builder.append("\tat " + traceElement + "\n");
        }

        Brick msg = new Brick(builder.toString(), Brick.BRICK_EXCEPT);

        notices.push(msg);
    }

    public void pushMessage(String message, int type)
    {
        log.debug("pushMessage");

        Brick msg = new Brick(message, type);

        notices.push(msg);
    }

    public int getOffset()
    {
        return offset;
    }

    public void setOffset(int offset)
    {
        this.offset = offset;
    }

    public int getSize()
    {
        return size;
    }

    public void setSize(int size)
    {
        this.size = size;
    }

    public int getLimit()
    {
        return limit;
    }

    public void setLimit(int limit)
    {
        this.limit = limit;
    }

    public int store(Object object)
    {
        log.debug("store");

        int result = 0;

        try
        {
            // retrieve class model from dictionary
            Model classModel = Persistance.dictionary.get(object.getClass().getName());

            if (
                classModel == null
            )
            {
                pushMessage("Model for class " + object.getClass().getName() + " not found in dictionary ",
                        Brick.BRICK_ERROR);

                return Brick.BRICK_ERROR;
            }

            // retrieve table model from dictionary
            Model tableModel = null;

            if (
                classModel.getPseudo() != null
            )
            {
                tableModel = Persistance.dictionary.get(classModel.getPseudo());
            }

            validateStore(object, classModel, tableModel);

            if (
                peekNotice().getType() == Brick.BRICK_ERROR || peekNotice().getType() == Brick.BRICK_EXCEPT
            )
            {
                log.error("object could not be stored because of validateStore result ");

                return result;
            }

            Dao dao = Persistance.getDao();

            // ouvrir connexion
            Connection connection = dao.openConnection(Persistance.getDataSource());

            WorkUnit workUnit = Persistance.process(object, classModel, tableModel, VColumn.SQL_INSERT);

            // enregistrer refdata
            Object[] output = dao.write(connection, workUnit.getSql(), workUnit.getIn());

            // recuper id refdata
            // int id = (Integer) output[0];

            // fermer connexion
            dao.closeConnection(connection);

            // afficher message confirmation
            pushMessage("Refdata enregistrée ", Brick.BRICK_MSG);

            result = 1;
        }

        catch (Exception e)
        {
            log.error(e);

            pushMessage(e);

        }

        return result;
    }

    public WorkUnit fetch(String sql, Object[] params)
    {
        log.debug("fetch");

        WorkUnit workUnit = new WorkUnit();

        try
        {
            Dao dao = Persistance.getDao();

            // ouvrir connexion
            Connection connexion = dao.openConnection(Persistance.getDataSource());

            // lancer requete
            workUnit = dao.read(connexion, sql, params);

            // fermer connexion
            dao.closeConnection(connexion);
        }

        catch (Exception e)
        {
            log.error(e);

            pushMessage(e);
        }

        return workUnit;
    }

    public void delete(RefData refdata)
    {
        log.debug("delete");

        try
        {
            validateDelete(null);

            if (
                peekNotice().getName() != null
            )
            {
                return;
            }

            Dao dao = Persistance.getDao();

            // ouvrir connexion
            Connection connection = dao.openConnection(Persistance.getDataSource());

            // supprimer personne
            dao.write(connection, "delete from refdata r where id=? ", new Object[]
            { refdata.getId() });

            // fermer connexion
            dao.closeConnection(connection);

            // afficher message confirmation
            pushMessage("Refdata supprimée", Brick.BRICK_MSG);
        }

        catch (Exception e)
        {
            log.error(e);

            pushMessage(e);
        }

    }

    public void goUp()
    {
        this.level++;
    }

    public void goDown()
    {
        this.level--;
    }

    public void goLevel(int level)
    {
        this.level = level;
    }

    public void goFirst()
    {
        log.debug("goFirst");

        setOffset(0);

    }

    public void goLast()
    {
        log.debug("goLast");

        if (
            (getSize() - getLimit()) >= 0
        )
        {
            setOffset(getSize() - getLimit());

        }
    }

    public void goNext()
    {
        log.debug("goNext");

        if (
            getOffset() + getLimit() <= getSize()
        )
        {
            setOffset(getOffset() + getLimit());
        } else
        {
            setOffset(getOffset() - getLimit());

            if (
                getOffset() < 0
            )
            {
                setOffset(0);
            }
        }

    }

    public void goPrevious()
    {
        log.debug("goPrevious");

        if (
            getOffset() - getLimit() >= 0
        )
        {
            setOffset(getOffset() - getLimit());
        } else
        {
            setOffset(0);
        }

    }

    public void validateStore(Object in, Model classModel, Model tableModel) throws Exception
    {
        log.debug("validateStore");

        for (int i = 0; i < tableModel.getBricks().length; i++)
        {
            VColumn column = (VColumn) tableModel.getBricks()[i];

            VField field = Persistance.findVField(classModel, column.getPseudo());

            if (
                column.isAuto() == false && column.isNullable() == false
            )
            {
                switch (column.getSqlType())
                {
                case VColumn.SQL_INT:
                    break;
                case VColumn.SQL_VARCHAR:
                    Field javaField = field.getJavaField();

                    String strValue = (String) javaField.get(in);

                    if (
                        strValue == null || strValue.isEmpty() == true
                    )
                    {
                        pushMessage("column: " + column.getName() + " cannot be null", Brick.BRICK_ERROR);
                    }

                    // TODO check string length

                    break;
                }
            }
        }

    }

    abstract public void validateModify(Object in);

    abstract public void validateDelete(Object in);

    public Brick popNotice()
    {
        log.debug("popNotice");

        Brick msg = null;

        if (
            notices.isEmpty() == false
        )
        {
            msg = notices.pop();

        } else
        {
            msg = new Brick("None", Brick.BRICK_MSG);

        }

        return msg;
    }

    public Brick peekNotice()
    {
        log.debug("peekNotice");

        Brick msg = null;

        if (
            notices.isEmpty() == false
        )
        {
            msg = notices.peek();

        } else
        {
            msg = new Brick("None", Brick.BRICK_MSG);

        }

        return msg;
    }

    public Object[] generateInsertSQL(String table, Map<String, Object> inputs)
    {
        log.debug("generateInsertSQL");

        Object[] results = new Object[2];

        StringBuffer sql1 = new StringBuffer("insert into " + table + "(");

        StringBuffer sql2 = new StringBuffer(" values (");

        List<Object> objects = new LinkedList<Object>();

        int i = 0;

        for (Iterator<String> keyIterator = inputs.keySet().iterator(); keyIterator.hasNext();)
        {
            if (
                i > 0
            )
            {
                sql1.append(",");

                sql2.append(",");
            }

            String name = keyIterator.next();

            Object value = inputs.get(name);

            objects.add(value);

            sql1.append(name);

            sql2.append("?");

            i++;
        }

        sql1.append(") ");

        sql2.append(") ");

        results[0] = new String(sql1.append(sql2));

        Object[] params = new Object[objects.size()];

        for (i = 0; i < params.length; i++)
        {
            params[i] = objects.get(i);
        }

        results[1] = params;

        return results;
    }

}
