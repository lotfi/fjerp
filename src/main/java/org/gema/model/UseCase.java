package org.gema.model;

import lombok.Data;

@Data
public class UseCase extends Node
{
	private String processCode;
	
	private Data metaType;
    
	private String flow;
	
	private Actor  actor;
	
	private UseCase nextUseCase;
	
	private UseCase prevUseCase;
	
}
