package org.vlinder.core.model;

import java.util.List;

public class Model
{
    public static int MODEL_CLASS = 0;

    public static int MODEL_TABLE = 1;

    public static int MODEL_BLOCK = 2;

    private String name;

    private String pseudo;

    private int type;

    private Brick[] bricks =
    {};

    private List<Brick> children;

    private int size;

    private Model parent;

    public Model(int type)
    {
        this.type = type;
    }

    public Model(int size, int type)
    {
        this.size = size;
        this.type = type;

        bricks = new Brick[size];

        for (int i = 0; i < size; i++)
        {
            bricks[i] = new Brick("", Brick.BRICK_FIELD);
        }
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

    public Brick[] getBricks()
    {
        return bricks;
    }

    public void setBricks(Brick[] bricks)
    {
        this.bricks = bricks;
    }

    public int getSize()
    {
        return size;
    }

    public void setSize(int size)
    {
        this.size = size;
    }

    public String getPseudo()
    {
        return pseudo;
    }

    public void setPseudo(String pseudo)
    {
        this.pseudo = pseudo;
    }

    public Object getValue(int index)
    {
        Object object = null;

        if (
            index >= 0 && index <= bricks.length - 1
        )
        {
            object = bricks[index].getValue();
        }

        return object;
    }

    public void setValue(int index, Object value)
    {
        if (
            index >= 0 && index <= bricks.length - 1
        )
        {
            bricks[index].setValue(value);
        }
    }

    public void setName(int index, String name)
    {
        if (
            index >= 0 && index <= bricks.length - 1
        )
        {
            bricks[index].setName(name);
        }
    }

    public String getName(int index)
    {
        String name = null;

        if (
            index >= 0 && index <= bricks.length - 1
        )
        {
            name = bricks[index].getName();
        }

        return name;
    }

    public String[] getNames()
    {
        String[] names = new String[bricks.length];

        for (int i = 0; i < bricks.length; i++)
        {
            names[i] = bricks[i].getName();
        }

        return names;
    }

    public Object[] getValues()
    {
        Object[] values = new String[bricks.length];

        for (int i = 0; i < bricks.length; i++)
        {
            values[i] = bricks[i].getValue();
        }

        return values;
    }

    public int getIndex(String name)
    {
        int index = -1;

        String[] names = getNames();

        for (int i = 0; i < names.length; i++)
        {
            if (
                names[i].equals(name)
            )
            {
                return i;
            }
        }

        return index;
    }

    public Model getParent()
    {
        return parent;
    }

    public void setParent(Model parent)
    {
        this.parent = parent;
    }

    public List<Brick> getChildren()
    {
        return children;
    }

    public void setChildren(List<Brick> children)
    {
        this.children = children;
    }

}
