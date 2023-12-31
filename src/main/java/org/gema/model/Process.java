package org.gema.model;

import java.util.LinkedList;
import java.util.List;

import lombok.Data;

@Data
public class Process extends Node
{
	private String project;
	
	private String release;
	
	private UseCase firstUseCase;
	
	private List<UseCase>  useCases=new LinkedList<UseCase>();
	
}
