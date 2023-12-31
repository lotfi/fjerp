package org.fjerp.serveur;

import java.io.File;

import org.apache.catalina.startup.Tomcat;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EmbeddedTomcatJSP
{
    private static final Logger log = LogManager.getLogger(EmbeddedTomcatJSP.class);

    public static void main(String[] args)
    {
        log.debug("main");

        try
        {
            String docBase = "src/main/webapp/";

            Tomcat tomcat = new Tomcat();

            tomcat.setPort(8080);

            tomcat.addWebapp("/", new File(docBase).getAbsolutePath());

            tomcat.start();

            tomcat.getServer().await();
        }

        catch (Exception e)
        {
            log.error(e);
        }
    }
}
