package org.fjerp.service.io;

public class Format 
{
	public static final int SEGMENT_NONE = 0;
	public static final int SEGMENT_FILE = 1;
	public static final int SEGMENT_RECORD = 2;
	public static final int SEGMENT_FIELD  = 3;
	
	public static final int TYPE_NONE     = 0;
	public static final int TYPE_NUMBER   = 1;
	public static final int TYPE_DATE     = 2;
	public static final int TYPE_HOUR     = 3;
	public static final int TYPE_FLOAT    = 4;
	public static final int TYPE_STRING   = 5;
	public static final int TYPE_DATETIME = 6;
	public static final int TYPE_CARDID   = 7;
	public static final int TYPE_DOUBLE   = 8;
	
	public static final int PADDING_NONE  = 0;
	public static final int PADDING_RIGHT = 1;
	public static final int PADDING_LEFT  = 2;
	
	
	private String   name="";
	private int      type;
	private int      segment;
	private int      length;
	private int      position;
	private boolean  mandatory;
	private Format   parent;
	private Format[] children={};
	private int      paddingType;
	private char     paddingChar;
	
	
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
	
	public int getLength() 
	{
		return length;
	}
	
	public void setLength(int length)
	{
		this.length = length;
	}
	
	public int getPosition() 
	{
		return position;
	}
	
	public void setPosition(int position) 
	{
		this.position = position;
	}
	
	public Format getParent() 
	{
		return parent;
	}
	
	public void setParent(Format parent) 
	{
		this.parent = parent;
	}

	public boolean isMandatory() 
	{
		return mandatory;
	}

	public void setMandatory(boolean mandatory) 
	{
		this.mandatory = mandatory;
	}

	public Format[] getChildren() 
	{
		return children;
	}

	public void setChildren(Format[] children)
	{
		this.children = children;
	}

	public int getSegment()
	{
		return segment;
	}

	public void setSegment(int segment)
	{
		this.segment = segment;
	}

	public int getPaddingType() 
	{
		return paddingType;
	}

	public void setPaddingType(int paddingType)
	{
		this.paddingType = paddingType;
	}

	public char getPaddingChar() 
	{
		return paddingChar;
	}

	public void setPaddingChar(char paddingChar)
	{
		this.paddingChar = paddingChar;
	}
	
	
	
	
}
