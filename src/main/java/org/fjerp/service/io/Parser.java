package org.fjerp.service.io;

public interface Parser 
{
	public void  open(String fileName) throws Exception;
	
	public String read() throws Exception;
		
	public void  close() throws Exception;
}
