package org.gema.model;

import java.util.Date;

import lombok.Data;

@Data
public class Activity extends Node
{
    private int  id;
    
    private Job   job;
    
    private org.fjerp.modele.Data  message;
    
    private Date  execTime;
    
    private Actor actor;
    
    private UseCase useCase;
    
    private String   state;

}
