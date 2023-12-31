package org.vlinder.core.model;

import java.util.LinkedList;
import java.util.List;

public class WorkUnit
{

    private String sql = "";

    private Object[] in =
    {};

    private Model meta = new Model(Model.MODEL_TABLE);

    private List<Model> rows = new LinkedList<Model>();

    private int act;

    private int size;

    public String getSql()
    {
        return sql;
    }

    public void setSql(String sql)
    {
        this.sql = sql;
    }

    public Object[] getIn()
    {
        return in;
    }

    public void setIn(Object[] in)
    {
        this.in = in;
    }

    public int getSize()
    {
        return size;
    }

    public void setSize(int size)
    {
        this.size = size;
    }

    public int getAct()
    {
        return act;
    }

    public void setAct(int act)
    {
        this.act = act;
    }

    public Model getMeta()
    {
        return meta;
    }

    public void setMeta(Model meta)
    {
        this.meta = meta;
    }

    public List<Model> getRows()
    {
        return rows;
    }

    public void setRows(List<Model> rows)
    {
        this.rows = rows;
    }

    public String[] getNames()
    {
        return meta.getNames();
    }

    public int getNameIndex(String name)
    {
        String[] names = meta.getNames();

        for (int i = 0; i < names.length; i++)
        {
            if (
                names[i].equals(name)
            )
            {
                return i;
            }
        }

        return -1;
    }

    public Model getRow(int index)
    {
        return rows.get(index);
    }

    public int getRowCount()
    {
        return rows.size();
    }

    public Object getValue(int rowIndex, String name)
    {
        Object value = null;

        Model row = getRow(rowIndex);

        int index = meta.getIndex(name);

        value = row.getValue(index);

        return value;
    }

    public Object[] getValues(int rowIndex)
    {
        Object[] values =
        {};

        Model row = getRow(rowIndex);

        values = row.getValues();

        return values;
    }

}
