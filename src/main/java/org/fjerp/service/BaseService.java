package org.fjerp.service;

public interface BaseService
{
	public Object store(Object in) throws Exception;

	public Object modify(Object in) throws Exception;

	public Object find(Object in, int position, int width) throws Exception;

	public int count(Object in) throws Exception;

	public Object load(Object in) throws Exception;

	public void remove(Object in) throws Exception;


}
