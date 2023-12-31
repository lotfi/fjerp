package org.fjerp.serveur;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AppServer
{
    private static final Logger log = LogManager.getLogger(AppServer.class);

    public static void main(String[] args)
    {
        log.debug("main");

        try
        {
            Tomcat tomcat = new Tomcat();
            tomcat.setBaseDir("temp");
            tomcat.setPort(8080);

            String contextPath = "/";
            String docBase = new File(".").getAbsolutePath();

            Context context = tomcat.addContext(contextPath, docBase);

            HttpServlet servlet = new HttpServlet()
            {
                @Override
                protected void doGet(HttpServletRequest req, HttpServletResponse resp)
                        throws ServletException, IOException
                {
                    PrintWriter writer = resp.getWriter();

                    writer.println("<html><title>Welcome</title><body>");
                    writer.println("<h1>Have a Great Day!</h1>");
                    writer.println("</body></html>");
                }
            };

            String servletName = "Servlet1";
            String urlPattern = "/go";

            tomcat.addServlet(contextPath, servletName, servlet);

            // context.addServletMappingDecoded(urlPattern, servletName);

            tomcat.start();
            tomcat.getServer().await();

        }

        catch (Exception e)
        {
            log.error(e);
        }
    }
}