package org.vlinder.core.model;

public class Brick
{
    private String name = "";

    private int type;

    private Model model;

    // TODO could be used as default value
    private Object value;

    public static final int BRICK_NONE = 0;

    public static final int BRICK_FIELD = 1;

    public static final int BRICK_COLUMN = 2;

    public static final int BRICK_WORKUNIT = 3;

    public static final int BRICK_ERROR = 4;

    public static final int BRICK_WARNING = 5;

    public static final int BRICK_MSG = 6;

    public static final int BRICK_ACTION = 7;

    public static final int BRICK_EXCEPT = 8;

    public Brick(int type)
    {
        this.type = type;
    }

    public Brick(String name, int type)
    {
        this.name = name;

        this.type = type;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getType()
    {
        return type;
    }

    public void setType(int type)
    {
        this.type = type;
    }

    public Model getModel()
    {
        return model;
    }

    public void setModel(Model model)
    {
        this.model = model;
    }

    public Object getValue()
    {
        return value;
    }

    public void setValue(Object value)
    {
        this.value = value;
    }

}
