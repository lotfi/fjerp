package org.fjerp.serveur;

import java.io.File;
import java.io.IOException;
import java.io.Writer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MyTomcatTest
{
    private static final Logger log = LogManager.getLogger(MyTomcatTest.class);

    public static void main(String[] args)
    {
        log.debug("main");

        try
        {
            Tomcat tomcat = new Tomcat();

            tomcat.setPort(8080);

            Context ctx = tomcat.addContext("/", new File(".").getAbsolutePath());

            Tomcat.addServlet(ctx, "hello", new HttpServlet()
            {

                private static final long serialVersionUID = 3600060857537422698L;

                @Override
                protected void service(HttpServletRequest request, HttpServletResponse response)
                        throws ServletException, IOException
                {

                    response.setCharacterEncoding("UTF-8");

                    response.setContentType("text/plain");

                    try (Writer writer = response.getWriter())
                    {
                        writer.write("Hello, Embedded World from Blue Lotus Software!");

                        writer.flush();
                    }
                }
            });

            // ctx.addServletMappingDecoded("/*", "hello");

            tomcat.start();

            tomcat.getServer().await();
        }

        catch (Exception e)
        {
            log.error(e);

            e.printStackTrace();
        }
    }

}