package org.gema.model;

import lombok.Data;

@Data
public class UseCase extends Node
{
	private Process process;
	
	private Data metaType;
    
	private String flow;
	
	private Role  assignedRole;
		
}
