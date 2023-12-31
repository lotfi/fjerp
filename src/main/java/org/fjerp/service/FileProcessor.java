package org.fjerp.service;

public interface FileProcessor 
{
	public void start() throws Exception;
	
	public void finish() throws Exception;
	
	public void open() throws Exception;
	
	public void close() throws Exception;
	
	public void handleRowEnter() throws Exception;
	
	public void handleRowLeave() throws Exception;
	
	public void handleCellEnter() throws Exception;
	
	public void handleCellLeave() throws Exception;
}
