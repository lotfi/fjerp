package org.fjerp.service;

import java.util.List;

import javax.sql.DataSource;

import org.fjerp.modele.Data;

public interface ServiceCommun
{

	public DataSource getDataSource();

	public void setDataSource(DataSource dataSource);

	public Dao getDao();

	public void setDao(Dao dao);

	public int count(String sql) throws Exception;

	public List findDataSet(String hql, Object[] values, int position, int taille) throws Exception;

	public List findDataSet(String hql, Object[] values) throws Exception;

	public Object findUnique(String hql, Object[] values) throws Exception;

	public Object create(Object object) throws Exception;

	public void save(Object object) throws Exception;

	public void delete(Object object) throws Exception;

	public void update(Object object) throws Exception;

	public Object merge(Object object) throws Exception;

	public void refresh(Object object) throws Exception;

	public Object find(Class<?> classInstance, long id) throws Exception;

	public List<Data> read(String sql, Object[] params) throws Exception;

	public void write(String sql, Object[] params) throws Exception;

	public Object importData(Data data) throws Exception;

	public Data exportData(Object object) throws Exception;

}
