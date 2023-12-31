package org.gema.model;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import lombok.Data;


@Data
public class Job
{
    private int id;
    
	private Process process;
	
	private List<Activity>  history=new LinkedList<Activity>();
	
	private Actor  owner;
	
	private String state;
	
	private Date   startTime;
	
	private Date   endTime;
	
    private List<Data> book = new LinkedList<Data>();

}
