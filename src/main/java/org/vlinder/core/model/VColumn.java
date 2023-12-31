package org.vlinder.core.model;

public class VColumn extends Brick
{
    public static final int SQL_NOP = 0;

    public static final int SQL_INSERT = 1;

    public static final int SQL_SELECT = 2;

    public static final int SQL_UPDATE = 3;

    public static final int SQL_DELETE = 4;

    public static final int SQL_VARCHAR = 5;
    public static final int SQL_INT = 6;
    public static final int SQL_DOUBLE = 7;
    public static final int SQL_DATE = 8;
    public static final int SQL_TIME = 9;
    public static final int SQL_DATETIME = 10;
    public static final int SQL_BLOB = 11;

    private int sqlType;

    private boolean nullable = true;

    private boolean key = false;

    private boolean auto = false;

    private boolean editable = true;

    private boolean searchable = false;

    private boolean reference = false;

    private int maxLength;

    private int minLength;

    private int length;

    private String pseudo;

    private VColumn foreignKey;

    public VColumn()
    {
        super("", BRICK_COLUMN);
    }

    public VColumn(String name, int sqlType)
    {
        super(name, sqlType);
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

    public boolean isSearchable()
    {
        return searchable;
    }

    public void setSearchable(boolean searchable)
    {
        this.searchable = searchable;
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

    public int getLength()
    {
        return length;
    }

    public void setLength(int length)
    {
        this.length = length;
    }

    public String getPseudo()
    {
        return pseudo;
    }

    public void setPseudo(String pseudo)
    {
        this.pseudo = pseudo;
    }

    public boolean isReference()
    {
        return reference;
    }

    public void setReference(boolean reference)
    {
        this.reference = reference;
    }

    public VColumn getForeignKey()
    {
        return foreignKey;
    }

    public void setForeignKey(VColumn foreignKey)
    {
        this.foreignKey = foreignKey;
    }

}
