package org.fjerp.bplustree.model;

public abstract class Node
{

    public static final int UNDEFINED_KEY = Integer.MAX_VALUE;
    private int[] keys;

    private int order;
    private Node parent;

    public Node(int n)
    {
        this.order = n;
        keys = new int[this.order - 1];

        for (int i = 0; i < keys.length; i++)
        {
            keys[i] = UNDEFINED_KEY;
        }
    }

    abstract public void append(Node node);

    public abstract void remove(int value);

    public abstract String toString();

    public static int getMiddleKey(Node n1, Node n2)
    {
        int s = n1.getSize() + n2.getSize();

        if (s > 0)
        {
            int[] data = new int[s];

            int j = 0;

            // n1 case
            for (int i = 0; i < (n1.getOrder() - 1); i++)
            {

                if (n1.keys[i] != UNDEFINED_KEY)
                {
                    data[j++] = n1.keys[i];
                }
            }

            // n2 case
            for (int i = 0; i < (n2.getOrder() - 1); i++)
            {

                if (n2.keys[i] != UNDEFINED_KEY)
                {
                    data[j++] = n2.keys[i];
                }
            }

            return Node.getMiddleKey(data);
        }

        return UNDEFINED_KEY;
    }

    public int addKey(int value)
    {
        int index = -1;

        if (!this.isFull())
        {
            index = nextIndex();
            keys[index] = value;
        }

        return index;
    }


    public int contains(int value)
    {

        for (int i = 0; i < this.keys.length; i++)
        {

            if (this.keys[i] == value)
            {
                return i;
            }
        }

        return -1;
    }

    public int getKey(int i)
    {
        return this.keys[i];
    }

    public int getMaxKey()
    {

        if (getSize() > 0)
        {

            for (int i = keys.length - 1; i >= 0; i--)
            {

                if (keys[i] != UNDEFINED_KEY)
                {
                    return keys[i];
                }
            }
        }

        return UNDEFINED_KEY;
    }

    public int getMiddleKey()
    {
        int s = this.getSize();

        if (s > 0)
        {
            int[] data = new int[s];

            int j = 0;

            for (int i = 0; i < (this.getOrder() - 1); i++)
            {

                if (this.keys[i] != UNDEFINED_KEY)
                {
                    data[j++] = this.keys[i];
                }
            }

            return Node.getMiddleKey(data);
        }
        else { return UNDEFINED_KEY; }
    }

    public int getMinKey()
    {

        if (getSize() > 0)
        {

            for (int i = 0; i < keys.length; i++)
            {

                if (keys[i] != UNDEFINED_KEY)
                {
                    return keys[i];
                }
            }
        }

        return UNDEFINED_KEY;
    }

    public Node getParent()
    {
        return parent;
    }

    public int getSize()
    {
        int count = 0;

        for (int i = 0; i < keys.length; i++)
        {

            if (keys[i] != UNDEFINED_KEY)
            {
                count++;
            }
        }

        return count;
    }

    public boolean isFull()
    {
        return this.getSize() == (order - 1);
    }

    public int lastKeyIndex()
    {

        for (int i = this.order - 2; i >= 0; i--)
        {

            if (this.keys[i] != Node.UNDEFINED_KEY)
            {
                return i;
            }
        }

        return 0;
    }

    public void replace(int oldValue, int newValue)
    {

        for (int i = 0; i < keys.length; i++)
        {

            if (keys[i] == oldValue)
            {
                keys[i] = newValue;

                break;
            }
        }

    }

    public void setKey(int i, int value)
    {
        this.keys[i] = value;
    }

    public void setParent(Node parent)
    {
        this.parent = parent;
    }

    abstract protected void shiftRight(int index);

    protected int getOrder()
    {
        return order;
    }

    protected void setOrder(int o)
    {
        this.order = o;
    }

    //TODO leaf case to be implemented
    protected void sort()
    {

        for (int i = this.getOrder() - 2; i >= 0; i--)
        {

            for (int j = 0; j < i; j++)
            {

                if (this.getKey(j) > this.getKey(i))
                {
                    int temp = this.getKey(i);
                    this.setKey(i, this.getKey(j));
                    this.setKey(j, temp);
                }
            }
        }
    }

    private static int getMiddleKey(int[] data)
    {
        int result = UNDEFINED_KEY;

        if ((data != null) && (data.length > 0))
        {

            if (data.length == 1)
            {
                result = data[0];
            }
            else { result = data[data.length / 2]; }
        }

        return result;
    }

    private int nextIndex()
    {
        int index = -1;

        for (int i = 0; i < keys.length; i++)
        {

            if (keys[i] == UNDEFINED_KEY)
            {
                return i;
            }
        }

        return index;
    }

    /*
    public String toString()
    {
            StringBuffer sb=new StringBuffer();

            sb.append("leaf(");
            for(int i=0; i<this.getOrder()-1;i++)
            {
                    if (i>0) sb.append(",");

                    if (this.getKey(i) != UNDEFINED_KEY)
                    {
                            if (BPlusTree.PRINT_CHAR==true)
                                    sb.append((char)this.getKey(i));
                            else
                                    sb.append(this.getKey(i));
                    }
            }
            sb.append(")");
            return sb.toString();
    }
    */
}
