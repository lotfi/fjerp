package org.gema.model;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import lombok.Data;

@Data
public abstract class Node
{
    public static final int NODE_NONE = 0;

    public static final int NODE_UI = 1;

    public static final int NODE_STYLE = 2;

    public static final int NODE_VALUE = 3;

    public static final int NODE_COMPONENT = 4;

    public static final int NODE_FORMAT = 5;

    public static final int NODE_WORKFLOW = 6;

    public static final int NODE_STATE = 7;

    public static final int NODE_FLOW = 8;

    private int id;

    private String name;

    private int type;

    private Node parent;

    private String state;

    private Date startDate;

    private Date endDate;
    
    private org.fjerp.modele.Data data;

    private List<Node> elements = new LinkedList<Node>();
    
    public Node()
    {
    	this.data = new org.fjerp.modele.Data( new String[] {""} );
    }
    
    public Node(String[] names)
    {
    	this.data = new org.fjerp.modele.Data( names );
    }

}
