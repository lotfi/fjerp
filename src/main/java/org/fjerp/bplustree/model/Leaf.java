package org.fjerp.bplustree.model;

public class Leaf extends Node
{
    private Record[] records;

    public Leaf(int n)
    {
        super(n);

        records = new Record[this.getOrder() - 1];
        // TODO Auto-generated constructor stub
    }

    @Override public int addKey(int value)
    {
        int i = super.addKey(value);

        if (i != -1)
        {
            this.sort();
        }

        return i;
    }

    public void append(Node node)
    {

        for (int i = 0; i < (node.getOrder() - 1); i++)
        {

            if (node.getKey(i) != UNDEFINED_KEY)
            {
                this.addKey(node.getKey(i));
            }
        }
    }

    @Override public void remove(int value)
    {

        for (int i = 0; i < (this.getOrder() - 1); i++)
        {

            if (this.getKey(i) == value)
            {
                this.setKey(i, Node.UNDEFINED_KEY);

                break;
            }
        }

        this.sort();

    }

    public Leaf[] split(int value)
    {
        int[] data = new int[this.getOrder()];

        // copy all the keys and the value to be added
        for (int i = 0; i < (this.getOrder() - 1); i++)
        {
            data[i] = this.getKey(i);
        }

        data[this.getOrder() - 1] = value;

        // sort the keys
        for (int i = data.length - 1; i >= 0; i--)
        {

            for (int j = 0; j < i; j++)
            {

                if (data[j] > data[i])
                {
                    int temp = data[i];
                    data[i] = data[j];
                    data[j] = temp;

                    //TODO swap records
                }
            }
        }

        // split the keys through two leaves
        Leaf[] leaves = new Leaf[2];

        for (int i = 0; i < leaves.length; i++)
        {
            leaves[i] = new Leaf(Internal.ORDER);
        }

        int i1 = 0;
        int i2 = 0;

        for (int i = 0; i < data.length; i++)
        {

            if (i < (this.getOrder() / 2))
            {
                leaves[0].setKey(i1++, data[i]);
            }
            else { leaves[1].setKey(i2++, data[i]); }
        }

        return leaves;
    }

    @Override public String toString()
    {
        StringBuffer sb = new StringBuffer();

        sb.append("leaf(" + Integer.toHexString(hashCode()));

        if (getParent() == null)
        {
            sb.append(" parent(null)");
        }
        else
        {
            sb.append(" parent(" + Integer.toHexString(getParent().hashCode()) +
                ")");
        }

        // copy all the keys and value
        for (int i = 0; i < (this.getOrder() - 1); i++)
        {

            if (this.getKey(i) != UNDEFINED_KEY)
            {
                sb.append("," + (char) this.getKey(i));
            }
            else { sb.append(",-1"); }
        }

        sb.append(")");

        return sb.toString();
    }

    @Override protected void shiftRight(int index)
    {

        for (int i = this.getOrder() - 2; i >= (index + 1); i--)
        {
            int key = getKey(i - 1);
            setKey(i, key);
            setKey(i - 1, Internal.UNDEFINED_KEY);
        }

        setKey(index, UNDEFINED_KEY);
    }

}
