package org.gema.model;

import lombok.Data;

@Data
public class Actor extends Node
{
	private String uid;
	
	private String password;
	
	private Role   role;
	
	
	
}
