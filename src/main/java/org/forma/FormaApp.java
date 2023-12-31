package org.forma;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;

// @SpringBootApplication
public class FormaApp
{
    private static final Logger log = LogManager.getLogger(FormaApp.class);

    public static void main(String[] args)
    {
        log.debug("main");

        SpringApplication.run(FormaApp.class, args);
    }

}