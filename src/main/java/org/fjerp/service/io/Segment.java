package org.fjerp.service.io;

public class Segment
{
	private Object[] content={};
	private Format   format;
	private int      position;
	private int      size;
	private Segment  parent;
	
	public Object[] getContent()
	{
		return content;
	}
	
	public void setContent(Object[] content) 
	{
		this.content = content;
	}
	
	public Format getFormat() {
		return format;
	}
	
	public void setFormat(Format format)
	{
		this.format = format;
	}
	
	public int getPosition() {
		return position;
	}
	
	public void setPosition(int position) {
		this.position = position;
	}
	
	public int getSize()
	{
		return size;
	}
	
	public void setSize(int size) 
	{
		this.size = size;
	}
	
	public Segment getParent() 
	{
		return parent;
	}
	
	public void setParent(Segment parent)
	{
		this.parent = parent;
	}

}
