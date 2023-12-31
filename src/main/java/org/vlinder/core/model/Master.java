package org.vlinder.core.model;

import java.util.HashMap;
import java.util.Map;

public class Master extends Atom
{

    public static final int MASTER_NONE = 0;
    public static final int MASTER_TABLE = 1;
    public static final int MASTER_FIELD = 2;

    private String classId = "";

    private String fieldId = "";

    private String tableId = "";

    private String columnId = "";

    private int sqlType;

    private boolean nullable = true;

    private boolean key = false;

    private boolean auto = false;

    private boolean editable = true;

    private boolean searchable = false;

    private int maxLength;

    private int minLength;

    private int length;

    private Master parent;

    private final Map<String, Master> children = new HashMap<String, Master>();

    public String getClassId()
    {
        return classId;
    }

    public void setClassId(String classId)
    {
        this.classId = classId;
    }

    public String getFieldId()
    {
        return fieldId;
    }

    public void setFieldId(String fieldId)
    {
        this.fieldId = fieldId;
    }

    public int getSqlType()
    {
        return sqlType;
    }

    public void setSqlType(int sqlType)
    {
        this.sqlType = sqlType;
    }

    public boolean isNullable()
    {
        return nullable;
    }

    public void setNullable(boolean nullable)
    {
        this.nullable = nullable;
    }

    public boolean isKey()
    {
        return key;
    }

    public void setKey(boolean key)
    {
        this.key = key;
    }

    public int getMaxLength()
    {
        return maxLength;
    }

    public void setMaxLength(int maxLength)
    {
        this.maxLength = maxLength;
    }

    public int getMinLength()
    {
        return minLength;
    }

    public void setMinLength(int minLength)
    {
        this.minLength = minLength;
    }

    public boolean isAuto()
    {
        return auto;
    }

    public void setAuto(boolean auto)
    {
        this.auto = auto;
    }

    public boolean isEditable()
    {
        return editable;
    }

    public void setEditable(boolean editable)
    {
        this.editable = editable;
    }

    public String getTableId()
    {
        return tableId;
    }

    public void setTableId(String tableId)
    {
        this.tableId = tableId;
    }

    public String getColumnId()
    {
        return columnId;
    }

    public void setColumnId(String columnId)
    {
        this.columnId = columnId;
    }

    public int getLength()
    {
        return length;
    }

    public void setLength(int length)
    {
        this.length = length;
    }

    public Master getParent()
    {
        return parent;
    }

    public void setParent(Master parent)
    {
        this.parent = parent;
    }

    public Map<String, Master> getChildren()
    {
        return children;
    }

    public boolean isSearchable()
    {
        return searchable;
    }

    public void setSearchable(boolean searchable)
    {
        this.searchable = searchable;
    }

}
