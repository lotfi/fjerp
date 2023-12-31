package org.fjerp.serveur;

import static io.undertow.Handlers.path;
import static io.undertow.Handlers.resource;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.undertow.Handlers;
import io.undertow.Undertow;
import io.undertow.io.IoCallback;
import io.undertow.security.api.AuthenticationMechanism;
import io.undertow.security.api.AuthenticationMode;
import io.undertow.security.api.SecurityContext;
import io.undertow.security.handlers.AuthenticationCallHandler;
import io.undertow.security.handlers.AuthenticationConstraintHandler;
import io.undertow.security.handlers.AuthenticationMechanismsHandler;
import io.undertow.security.handlers.SecurityInitialHandler;
import io.undertow.security.idm.IdentityManager;
import io.undertow.security.impl.BasicAuthenticationMechanism;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.server.handlers.PathHandler;
import io.undertow.server.handlers.resource.ClassPathResourceManager;
import io.undertow.servlet.Servlets;
import io.undertow.servlet.api.DeploymentInfo;
import io.undertow.servlet.api.DeploymentManager;
import io.undertow.util.Headers;

public class UndertowServer
{
    private static final Logger log = LogManager.getLogger(UndertowServer.class);

    public static void minimalServer()
    {
        log.debug("minimalServer");

        Undertow server = Undertow.builder().addHttpListener(8080, "localhost").setHandler(exchange -> {
            exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/plain");

            exchange.getResponseSender().send("Hello there");
        }).build();

        server.start();
    }

    public static void simpleServer1()
    {
        log.debug("simpleServer1");

        Undertow server = Undertow.builder().addHttpListener(8080, "localhost").setHandler(exchange -> {
            exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/plain");

            exchange.getResponseSender().send("Salut Fjerp !");
        }).build();

        server.start();
    }

    public static void simpleServer2()
    {
        log.debug("simpleServer2");

        Undertow server = Undertow.builder().addHttpListener(8080, "localhost").setHandler(new HttpHandler()
        {
            @Override
            public void handleRequest(HttpServerExchange exchange) throws Exception
            {
                exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/plain");

                exchange.getResponseSender().send("Hello Baeldung");
            }
        }).build();

        server.start();
    }

    private static HttpHandler addSecurity(HttpHandler toWrap, IdentityManager identityManager)
    {
        log.debug("addSecurity");

        HttpHandler handler = toWrap;

        handler = new AuthenticationCallHandler(handler);

        handler = new AuthenticationConstraintHandler(handler);

        List<AuthenticationMechanism> mechanisms = Collections
                .singletonList(new BasicAuthenticationMechanism("Baeldung_Realm"));

        handler = new AuthenticationMechanismsHandler(handler, mechanisms);

        handler = new SecurityInitialHandler(AuthenticationMode.PRO_ACTIVE, identityManager, handler);

        return handler;
    }

    private static void setExchange(HttpServerExchange exchange)
    {
        log.debug("setExchange");

        SecurityContext context = exchange.getSecurityContext();

        exchange.getResponseSender().send("Hello " + context.getAuthenticatedAccount().getPrincipal().getName(),
                IoCallback.END_EXCHANGE);
    }

    public static void simpleServer3()
    {
        log.debug("simpleServer3");

        Map<String, char[]> users = new HashMap<>(2);

        users.put("root", "password".toCharArray());

        users.put("admin", "password".toCharArray());

        IdentityManager idm = new CustomIdentityManager(users);

        Undertow server = Undertow.builder().addHttpListener(8080, "localhost")
                .setHandler(addSecurity(e -> setExchange(e), idm)).build();

        server.start();
    }

    public static void simpleServer4()
    {
        log.debug("simpleServer4");

        try
        {
            DeploymentInfo servletBuilder = Servlets.deployment().setClassLoader(UndertowServer.class.getClassLoader())
                    .setDeploymentName("fjerp").setContextPath("/fjerp")

                    .addServlets(Servlets.servlet("servlet1", new HttpServlet()
                    {
                        @Override
                        protected void doGet(HttpServletRequest request, HttpServletResponse response)
                                throws ServletException, IOException
                        {
                            response.getWriter().write("Servlet1");
                        }
                    }.getClass()).addMapping("/servlet1"));

            DeploymentManager manager = Servlets.defaultContainer().addDeployment(servletBuilder);

            manager.deploy();

            PathHandler path = Handlers.path(Handlers.redirect("/fjerp")).addPrefixPath("/fjerp", manager.start());

            Undertow server = Undertow.builder().addHttpListener(8888, "localhost").setHandler(path).build();

            server.start();
        }

        catch (Exception e)
        {
            log.error(e);
        }
    }

    public static void contenuStatique()
    {
        log.debug("contenuStatique");

        Undertow server = Undertow.builder().addHttpListener(8080, "localhost")
                .setHandler(path().addPrefixPath("/",
                        resource(new ClassPathResourceManager(WelcomePage.class.getClassLoader()))
                                .addWelcomeFiles("static/main.html")))
                .build();

        server.start();
    }

    public static void serveurParam()
    {
        log.debug("serveurParam");

        Undertow server = Undertow.builder().addHttpListener(8080, "0.0.0.0")
                .setHandler(path().addPrefixPath("/racine", new MyHttpHandler())).build();

        server.start();
    }

    public static void main(String[] args)
    {
        log.debug("main");

        serveurParam();
    }
}