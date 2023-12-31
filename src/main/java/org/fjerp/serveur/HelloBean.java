package org.fjerp.serveur;

import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.event.ActionEvent;

@ManagedBean
public class HelloBean
{
    private String world = "Hello World!";

    public String getWorld()
    {
        return world;
    }

    public void updateDate(final ActionEvent event)
    {
        final Date now = new Date();
        world = now.toString();
    }

}