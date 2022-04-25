package org.fjerp.bplustree.model;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;


public class Internal extends Node
{
    public static final int ORDER = 3;

    private Node[] pointers;

    public Internal(int n)
    {
        super(n);

        pointers = new Node[n];

        for (int i = 0; i < pointers.length; i++)
        {
            pointers[i] = null;
            // TODO Auto-generated constructor stub
        }

    }

    public static Node delete(Node tree, int v)
    {
        Node l = Internal.find(tree, v);

        // key found
        if (l != null)
        {

            // delete entry which matches the key v
            l.remove(v);

            // is it the root and one child is remaining
            if ((l.getParent() == null) &&
                    (((Internal) l).getChildren().size() == 1))
            {

                // the child becomes the new root
                return ((Internal) l).getChildren().get(0);
            }
            else
            // too few entries
            if (l.getSize() < (Internal.ORDER / 2))
            {
                boolean left = true;

                // get previous neighbour
                Node l1 = ((Internal) l.getParent()).getPrevious(l);

                if (l1 == null)
                {

                    // get the next neighbour
                    l1 = ((Internal) l.getParent()).getNext(l);
                    left = false;
                }

                int v1 = -1;

                if (left)
                {
                    v1 = ((Internal) l.getParent()).getKey(l1, l);
                }
                else { v1 = ((Internal) l.getParent()).getKey(l, l1); }

                // entries of l and l1 can fit one node
                if ((l.getSize() + l1.getSize()) <= (Internal.ORDER - 1))
                {

                    if (left == true)
                    {
                        l1.append(l);
                    }
                    else { l.append(l1); }

                    return Internal.delete(l.getParent(), v1);
                }

                // redistribution required
                else
                {

                    // redistribute from l1 to l (max-th(l1) to 0-th(l) )
                    if (left == true)
                    {

                        while ((l1.getSize() >= (Internal.ORDER / 2)) &&
                                (l.getSize() < (Internal.ORDER / 2)))
                        {

                            // common case
                            int k = l1.contains(l1.getMaxKey());
                            l.shiftRight(0);
                            l.setKey(0, l1.getKey(k));
                            l1.setKey(k, Node.UNDEFINED_KEY);

                            // internal case
                            if (l instanceof Internal)
                            {
                                ((Internal) l).setLeftPointer(0,
                                    ((Internal) l1).getLeftPointer(k));
                                ((Internal) l).setRightPointer(0,
                                    ((Internal) l1).getRightPointer(k));

                                ((Internal) l1).setLeftPointer(0, null);
                                ((Internal) l1).setRightPointer(0, null);
                            }
                        }
                    }

                    // redistribute from l1 to l ( 0-th(l1) to max-th-(l) )
                    else
                    {

                        while ((l1.getSize() >= (Internal.ORDER / 2)) &&
                                (l.getSize() < (Internal.ORDER / 2)))
                        {

                            // common case
                            int k = l1.contains(l1.getMinKey());
                            int j = l.addKey(l1.getKey(k));
                            l1.setKey(k, Node.UNDEFINED_KEY);

                            // internal case
                            if (l instanceof Internal)
                            {
                                ((Internal) l).setLeftPointer(j,
                                    ((Internal) l1).getLeftPointer(k));
                                ((Internal) l).setRightPointer(j,
                                    ((Internal) l1).getRightPointer(k));

                                ((Internal) l1).setLeftPointer(k, null);
                                ((Internal) l1).setRightPointer(k, null);
                            }
                        }

                    }
                }
            }

            // update parent entry if required
            else
            {
            }
        }


        return tree;
    }

    public static Leaf find(Node tree, int value)
    {

        if (tree instanceof Leaf)
        {
            return (Leaf) tree;
        }
        else
        {
            Internal internal = (Internal) tree;

            if (internal != null)
            {

                for (int i = 0; i < internal.getOrder(); i++)
                {

                    if (value < internal.getKey(i))
                    {
                        return Internal.find(internal.getLeftPointer(i), value);
                    }
                    else if (value >= internal.getKey(i))
                    {
                        return Internal.find(internal.getRightPointer(i),
                                value);
                    }
                }
            }
        }

        return null;
    }

    public static Node insert(Node tree, int value)
    {

        Leaf leaf = Internal.find(tree, value);

        if (leaf != null)
        {

            // the value doesn't exist in leaf
            if (leaf.contains(value) == -1)
            {

                // leaf with at least an empty entry
                if (leaf.isFull() == false)
                {
                    leaf.addKey(value);

                    leaf.sort();

                    return tree;
                }
                else
                // leaf is full
                {

                    // add the value to the leaf, and split in to two different leaves
                    Leaf[] leaves = leaf.split(value);

                    // create an internal node with the two leaves
                    Internal newTree = new Internal(Internal.ORDER);

                    int index = newTree.addKey(Node.getMiddleKey(leaves[0],
                                leaves[1]));
                    newTree.setLeftPointer(index, leaves[0]);
                    newTree.setRightPointer(index, leaves[1]);

                    leaves[0].setParent(newTree);
                    leaves[1].setParent(newTree);

                    // insert newTree internal to the parent of the leaf target node
                    return Internal.insert((Internal) leaf.getParent(),
                            newTree);
                }
            }
        }
        else
        {

            // create a leaf node
            leaf = new Leaf(Internal.ORDER);
            leaf.addKey(value);

            return leaf;
        }

        return null;
    }

    public static Internal merge(Internal t1, Internal t2)
    {
        Internal tree = new Internal((t1.getOrder() * 2) - 1);

        // add t1 (keys and pointers)
        int j = 0;

        for (int i = 0; i < (t1.getOrder() - 1); i++)
        {

            if (t1.getKey(i) != Node.UNDEFINED_KEY)
            {
                tree.setKey(j, t1.getKey(i));

                tree.setLeftPointer(j, t1.getLeftPointer(i));
                tree.setRightPointer(j, t1.getRightPointer(i));

                j++;
            }
        }

        // add t2 (keys and pointers)
        for (int i = 0; i < (t2.getOrder() - 1); i++)
        {

            if (t2.getKey(i) != Node.UNDEFINED_KEY)
            {
                tree.setKey(j, t2.getKey(i));

                tree.setLeftPointer(j, t2.getLeftPointer(i));
                tree.setRightPointer(j, t2.getRightPointer(i));

                j++;
            }
        }

        // sort keys & pointers
        for (int i = tree.getOrder() - 2; i >= 0; i--)
        {

            for (j = 0; j < i; j++)
            {

                if (tree.getKey(j) > tree.getKey(i))
                {

                    // swap keys
                    int temp = tree.getKey(i);
                    tree.setKey(i, tree.getKey(j));
                    tree.setKey(j, temp);

                    // swap left pointers
                    Node tempNode = tree.getLeftPointer(i);
                    tree.setLeftPointer(i, tree.getLeftPointer(j));
                    tree.setLeftPointer(j, tempNode);

                    // swap right pointers
                    tempNode = tree.getRightPointer(i);
                    tree.setRightPointer(i, tree.getRightPointer(j));
                    tree.setRightPointer(j, tempNode);

                }
            }
        }


        return tree;
    }


    public static String print(Node n)
    {
        StringBuffer sb = new StringBuffer();

        if (n != null)
        {
            sb.append(n.toString() + "\n");

            if (n instanceof Internal)
            {
                Internal n1 = (Internal) n;

                /*

                for(int i=0; i<n1.getOrder(); i++)
                {
                        sb.append(n1.getPointer(i)+"\n");
                }
                */
                // print the child nodes
                for (int i = 0; i < n1.getOrder(); i++)
                {
                    sb.append(Internal.print(n1.getPointer(i)));
                }


            }
        }

        return sb.toString();
    }

    public void append(Internal n)
    {

        // TODO Auto-generated method stub
        for (int i = 0; i < (n.getOrder() - 1); i++)
        {

            if (n.getKey(i) != UNDEFINED_KEY)
            {
                int j = 0;

                while (n.getKey(i) > this.getKey(j))
                {
                    j++;
                }

                this.shiftRight(j);

                this.setKey(j, n.getKey(i));
                this.setLeftPointer(j, n.getLeftPointer(i));
                this.setRightPointer(j, n.getRightPointer(i));

                if (this.getLeftPointer(j) != null)
                {
                    this.getLeftPointer(j).setParent(this);
                }

                if (this.getRightPointer(j) != null)
                {
                    this.getRightPointer(j).setParent(this);
                }

            }

        }
    }

    public void append(Node node)
    {
        Internal internal = (Internal) node;

        for (int i = 0; i < (internal.getOrder() - 1); i++)
        {

            if (node.getKey(i) != UNDEFINED_KEY)
            {

                // copy the key
                int index = this.addKey(internal.getKey(i));

                // copy the left pointer
                this.setLeftPointer(index, internal.getLeftPointer(i));

                // copy the right pointer
                this.setRightPointer(index, internal.getRightPointer(i));

                // relink the left pointer to this parent
                if (this.getLeftPointer(i) != null)
                {
                    this.getLeftPointer(i).setParent(this);
                }

                // relink the right pointer to this parent
                if (this.getRightPointer(i) != null)
                {
                    this.getRightPointer(i).setParent(this);
                }


            }
        }
    }

    public void copy(Internal n)
    {

        for (int i = 0; i < (n.getOrder() - 1); i++)
        {
            this.setKey(i, n.getKey(i));
            this.setLeftPointer(i, n.getLeftPointer(i));
            this.setRightPointer(i, n.getRightPointer(i));

            if (this.getLeftPointer(i) != null)
            {
                this.getLeftPointer(i).setParent(this);
            }

            if (this.getRightPointer(i) != null)
            {
                this.getRightPointer(i).setParent(this);
            }
        }
    }

    public Node getLeftPointer(int i)
    {
        return pointers[i];
    }

    public Node getPointer(int i)
    {
        return pointers[i];
    }

    public Node getRightPointer(int i)
    {
        return pointers[i + 1];
    }

    @Override public void remove(int value)
    {

        for (int i = 0; i < (this.getOrder() - 1); i++)
        {

            if (this.getKey(i) == value)
            {
                this.setKey(i, Node.UNDEFINED_KEY);
                this.setLeftPointer(i, null);
                this.setRightPointer(i, null);

                break;
            }
        }

    }


    public void setLeftPointer(int i, Node p)
    {

        if ((i < 0) || (i >= this.getOrder()))
        {
            return;
        }

        pointers[i] = p;
    }

    public void setRightPointer(int i, Node p)
    {
        pointers[i + 1] = p;
    }

    public Internal split()
    {

        // allocate child internal nodes
        Internal[] trees = new Internal[2];

        for (int i = 0; i < trees.length; i++)
        {
            trees[i] = new Internal(Internal.ORDER);
        }

        // split the keys through two leaves
        int j0 = 0;
        int j1 = 0;

        for (int i = 0; i < (this.getOrder() - 1); i++)
        {

            if (i < (Internal.ORDER / 2))
            {
                trees[0].setKey(j0, this.getKey(i));
                trees[0].setLeftPointer(j0, this.getLeftPointer(i));
                trees[0].setRightPointer(j0, this.getRightPointer(i));

                if (trees[0].getLeftPointer(j0) != null)
                {
                    trees[0].getLeftPointer(j0).setParent(trees[0]);
                }

                if (trees[0].getRightPointer(j0) != null)
                {
                    trees[0].getRightPointer(j0).setParent(trees[0]);
                }

                j0++;
            }
            else
            {
                trees[1].setKey(j1, this.getKey(i));

                // avoid the shared pointer between trees[0] and trees[1]
                if (i > (Internal.ORDER / 2))
                {
                    trees[1].setLeftPointer(j1, this.getLeftPointer(i));
                }
                else { trees[1].setLeftPointer(j1, null); }

                trees[1].setRightPointer(j1, this.getRightPointer(i));

                if (trees[1].getLeftPointer(j1) != null)
                {
                    trees[1].getLeftPointer(j1).setParent(trees[1]);
                }

                if (trees[1].getRightPointer(j1) != null)
                {
                    trees[1].getRightPointer(j1).setParent(trees[1]);
                }

                j1++;
            }
        }

        // allocate parent tree node
        Internal parent = new Internal(Internal.ORDER);

        parent.setKey(0, this.getMiddleKey());
        parent.setLeftPointer(0, trees[0]);
        parent.setRightPointer(0, trees[1]);

        trees[0].setParent(parent);
        trees[1].setParent(parent);

        return parent;
    }

    @Override public String toString()
    {
        StringBuffer sb = new StringBuffer();

        sb.append("internal(" + Integer.toHexString(this.hashCode()));

        if (getParent() == null)
        {
            sb.append(" parent(null)");
        }
        else
        {
            sb.append(" parent(" + Integer.toHexString(getParent().hashCode()) +
                ")");
        }

        // keys & pointers
        sb.append(" keys(");

        for (int i = 0; i < (this.getOrder() - 1); i++)
        {

            if (this.getKey(i) != Node.UNDEFINED_KEY)
            {
                sb.append("," + (char) this.getKey(i));
            }
            else { sb.append(",-1"); }
        }

        sb.append(")");

        // pointers
        sb.append(" pointers(");

        for (int i = 0; i < this.getOrder(); i++)
        {

            if (this.getPointer(i) == null)
            {
                sb.append(",n");
            }
            else
            {
                sb.append("," +
                    Integer.toHexString(this.getPointer(i).hashCode()));
            }
        }

        sb.append(")");

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

        for (int i = this.getOrder() - 1; i >= (index + 1); i--)
        {
            pointers[i] = pointers[i - 1];
            pointers[i - 1] = null;
        }

        setKey(index, UNDEFINED_KEY);
        setLeftPointer(index, null);
        setRightPointer(index, null);
    }

    @Override protected void sort()
    {

        for (int i = this.getOrder() - 2; i >= 0; i--)
        {

            for (int j = 0; j < i; j++)
            {

                if (this.getKey(j) > this.getKey(i))
                {

                    // swap keys
                    int temp = this.getKey(i);
                    this.setKey(i, this.getKey(j));
                    this.setKey(j, temp);

                    // swap left pointers
                    Node tempNode = this.getLeftPointer(i);
                    this.setLeftPointer(i, this.getLeftPointer(j));
                    this.setLeftPointer(j, tempNode);

                    // swap right pointers
                    tempNode = this.getRightPointer(i);
                    this.setRightPointer(i, this.getRightPointer(j));
                    this.setRightPointer(j, tempNode);
                }
            }
        }
    }

    private static Node delete(Node parent, Node child)
    {
        return null;
    }

    private static Node insert(Internal parent, Internal child)
    {

        if (parent == null)
        {
            parent = child;

            return parent;
        }
        else
        {

            if (parent.isFull() == true)
            {
                Internal merged = new Internal(parent.getOrder() + 1);

                merged.copy(parent);
                merged.append(child);

                //Internal.merge(parent, child);

                Internal newChild = merged.split();

                return Internal.insert((Internal) parent.getParent(), newChild);

            }
            else
            {

                //TODO problem to be fixed and tested
                if (child.getMaxKey() < parent.getMinKey())
                {
                    parent.shiftRight(0);
                    parent.setKey(0, child.getMaxKey());
                    parent.setLeftPointer(0, child.getLeftPointer(0));
                    parent.setRightPointer(0, child.getRightPointer(0));

                    if (child.getLeftPointer(0) != null)
                    {
                        child.getLeftPointer(0).setParent(parent);
                    }

                    if (child.getRightPointer(0) != null)
                    {
                        child.getRightPointer(0).setParent(parent);
                    }

                }
                else
                {

                    for (int i = 0; i < (parent.getOrder() - 1); i++)
                    {

                        if (child.getMaxKey() > parent.getKey(i))
                        {
                            parent.shiftRight(i);

                            parent.setKey(i, child.getMaxKey());
                            parent.setLeftPointer(i, child.getLeftPointer(i));
                            parent.setRightPointer(i, child.getRightPointer(i));

                            if (child.getLeftPointer(i) != null)
                            {
                                child.getLeftPointer(i).setParent(parent);
                            }

                            if (child.getRightPointer(i) != null)
                            {
                                child.getRightPointer(i).setParent(parent);
                            }

                            break;
                        }
                    }
                }

                /*
                int index = parent.addKey(child.getMaxKey());

                parent.setLeftPointer(index, null );
                parent.setRightPointer(index, child );

                parent.sort();
                */
                return parent;
            }
        }

    }

    private List<Node> getChildren()
    {
        LinkedList<Node> children = new LinkedList<Node>();

        for (int i = 0; i < this.pointers.length; i++)
        {

            if (this.pointers[i] != null)
            {
                children.add(this.pointers[i]);
            }
        }

        return children;
    }

    private int getKey(Node p1, Node p2)
    {

        for (int i = 0; i < (this.getOrder() - 1); i++)
        {

            if ((this.getLeftPointer(i) == p1) &&
                    (this.getRightPointer(i) == p2))
            {
                return this.getKey(i);
            }
        }

        return UNDEFINED_KEY;
    }

    private Node getNext(Node node)
    {

        for (int i = 0; i < this.getOrder(); i++)
        {

            if (this.getPointer(i) == node)
            {

                if ((i + 1) <= (this.getOrder() - 1))
                {
                    return this.getPointer(i + 1);
                }
                else { return null; }
            }
        }

        return null;
    }

    private Node getPrevious(Node node)
    {

        for (int i = 0; i < this.getOrder(); i++)
        {

            if (this.getPointer(i) == node)
            {

                if ((i - 1) >= 0)
                {
                    return this.getPointer(i - 1);
                }
                else { return null; }
            }
        }

        return null;
    }


    /*
    public String toString()
    {
            StringBuffer sb=new StringBuffer();

            sb.append("node(");
            for(int i=0; i<this.getOrder()-1;i++)
            {
                    if (i>0) sb.append(",");

                    if (this.getKey(i)!=Node.UNDEFINED_KEY)
                    {
                            sb.append("[");
                            if (this.getLeftPointer(i)==null)
                             sb.append("n,");
                            else sb.append(this.getLeftPointer(i).toString()+",");

                            if (BPlusTree.PRINT_CHAR==true) sb.append((char)this.getKey(i));
                            else sb.append((char)this.getKey(i));

                            if (this.getRightPointer(i)==null)
                             sb.append(",n");
                            else sb.append(","+this.getRightPointer(i).toString());

                            sb.append("]");
                    }

            }
            sb.append(")");


            return sb.toString();



    }
    */

}
