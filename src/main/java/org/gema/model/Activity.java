package org.gema.model;

import lombok.Data;

@Data
public class Activity extends Node
{
    private int  id;
    
    private Job   job;
    
    private org.fjerp.modele.Data  record;
    
    private Data  execTime;
    
    private Actor actor;
    
    private UseCase useCase;
    
    private String   state;

}
