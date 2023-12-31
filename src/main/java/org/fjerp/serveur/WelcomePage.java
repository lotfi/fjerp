package org.fjerp.serveur;

import io.undertow.Undertow;

public class WelcomePage
{

    public static void main(String[] args)
    {

        Undertow server = Undertow.builder().addHttpListener(8080, "localhost").build();

        server.start();
    }
}