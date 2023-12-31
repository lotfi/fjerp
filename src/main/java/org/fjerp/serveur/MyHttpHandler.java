package org.fjerp.serveur;

import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;

public class MyHttpHandler implements HttpHandler
{

    @Override
    public void handleRequest(HttpServerExchange exchange)
    {

        exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/plain");

        Object value = exchange.getQueryParameters().get("nom");

        String msg;

        if (
            value == null
        )
        {
            msg = "Salut inconnu !";
        } else
        {

            msg = String.format("Salut %s", (String) value);
        }

        exchange.getResponseSender().send(msg);
    }
}
