package org.gema.model;

public class Value extends Node
{
    public static final int VALUE_NONE = 0;

    public static final int VALUE_STRING = 1;

    public static final int VALUE_INTEGER = 2;

    public static final int VALUE_REAL = 3;

    public static final int VALUE_BOOLEAN = 4;

    public static final int VALUE_BLOB = 5;

    public static final int VALUE_DATE = 6;

    public static final int VALUE_TIME = 7;

    private int size, maxLengh, minLength;

    private Object defaultValue;

    private int valueType;

    private boolean readOnly = false, required = false;

    public int getSize()
    {
        return size;
    }

    public void setSize(int size)
    {
        this.size = size;
    }

    public int getMaxLengh()
    {
        return maxLengh;
    }

    public void setMaxLengh(int maxLengh)
    {
        this.maxLengh = maxLengh;
    }

    public int getMinLength()
    {
        return minLength;
    }

    public void setMinLength(int minLength)
    {
        this.minLength = minLength;
    }

    public Object getDefaultValue()
    {
        return defaultValue;
    }

    public void setDefaultValue(Object defaultValue)
    {
        this.defaultValue = defaultValue;
    }

    public int getValueType()
    {
        return valueType;
    }

    public void setValueType(int valueType)
    {
        this.valueType = valueType;
    }

    public boolean isReadOnly()
    {
        return readOnly;
    }

    public void setReadOnly(boolean readOnly)
    {
        this.readOnly = readOnly;
    }

    public boolean isRequired()
    {
        return required;
    }

    public void setRequired(boolean required)
    {
        this.required = required;
    }

}
