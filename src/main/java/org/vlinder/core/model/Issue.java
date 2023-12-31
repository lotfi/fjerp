package org.vlinder.core.model;

import java.util.Date;

public class Issue 
{
	private int     id;
	private String  jobCode;
	private String  stateCode;
	private String  assigneeId;
	private String  assignatorId;
	private String  ownerId;
	private Date    startTime;
	private Date    endTime;
	private byte    priority;
	private int     linkedId;
	private String  comment;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getJobCode() {
		return jobCode;
	}
	
	public void setJobCode(String jobCode) {
		this.jobCode = jobCode;
	}
	
	public String getStateCode() {
		return stateCode;
	}
	
	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}
	
	public String getAssigneeId() {
		return assigneeId;
	}
	
	public void setAssigneeId(String assigneeId) {
		this.assigneeId = assigneeId;
	}
	
	public String getAssignatorId() {
		return assignatorId;
	}
	
	public void setAssignatorId(String assignatorId) {
		this.assignatorId = assignatorId;
	}
	public String getOwnerId() {
		return ownerId;
	}
	
	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}
	
	public Date getStartTime() {
		return startTime;
	}
	
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	
	public Date getEndTime() {
		return endTime;
	}
	
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public byte getPriority() {
		return priority;
	}
	
	public void setPriority(byte priority) {
		this.priority = priority;
	}
	
	public int getLinkedId() {
		return linkedId;
	}
	
	public void setLinkedId(int linkedId) {
		this.linkedId = linkedId;
	}
	
	public String getComment() {
		return comment;
	}
	
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	
	
}
