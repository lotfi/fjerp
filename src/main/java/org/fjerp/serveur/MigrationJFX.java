package org.fjerp.serveur;

import java.io.File;

import org.apache.catalina.WebResourceRoot;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.webresources.DirResourceSet;
import org.apache.catalina.webresources.StandardRoot;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MigrationJFX extends Application implements EventHandler<ActionEvent>
{
    private Scene scene;

    private static final Logger log = LogManager.getLogger(MigrationJFX.class);

    private Tomcat tomcat = null;

    private Button boutonDemarrer = null;

    private Button boutonArreter = null;

    public static void main(String[] args)
    {
        log.debug("main");

        launch(args);
    }

    @Override
    public void handle(ActionEvent event)
    {
        log.debug("handle");

        if (
            event.getSource() == boutonDemarrer
        )
        {
            demarrerTomcat();

        } else if (
            event.getSource() == boutonArreter
        )
        {
            arreterTomcat();
        }
    }

    private void preparerScene(Pane pane)
    {
        GridPane gridPane = new GridPane();

        gridPane.setMinSize(400, 200);

        gridPane.setPadding(new Insets(10, 10, 10, 10));

        gridPane.setVgap(5);

        gridPane.setHgap(5);

        boutonDemarrer = new Button("Demarrer le serveur");

        boutonArreter = new Button("Arreter le serveur");

        gridPane.add(boutonDemarrer, 0, 0);

        gridPane.add(boutonArreter, 0, 1);

        boutonDemarrer.setOnAction(this);

        boutonArreter.setOnAction(this);

        scene = new Scene(gridPane);
    }

    private void arreterTomcat()
    {
        log.debug("arreterTomcat");

        try
        {
            if (
                tomcat != null
            )
            {
                tomcat.stop();

                tomcat.destroy();

                tomcat = null;
            }
        }

        catch (Exception e)
        {
            log.error(e);
        }
    }

    private void demarrerTomcat()
    {
        log.debug("demarrerTomcat");

        try
        {
            String webappDirLocation = "src/main/webapp/";

            tomcat = new Tomcat();

            tomcat.setBaseDir("temp");

            tomcat.setPort(8080);

            StandardContext ctx = (StandardContext) tomcat.addWebapp("", new File(webappDirLocation).getAbsolutePath());

            System.out.println("configuring app with basedir: " + new File("./" + webappDirLocation).getAbsolutePath());

            File additionWebInfClasses = new File("target/classes");

            WebResourceRoot resources = new StandardRoot(ctx);

            resources.addPreResources(new DirResourceSet(resources, "/WEB-INF/classes",

                    additionWebInfClasses.getAbsolutePath(), "/"));

            ctx.setResources(resources);

            tomcat.enableNaming();

            tomcat.getConnector();

            tomcat.start();

            tomcat.getServer().await();

        }

        catch (Exception e)
        {
            log.error(e);

            e.printStackTrace();
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        log.debug("start");

        FlowPane flowPane = new FlowPane(10, 10);

        flowPane.setAlignment(Pos.CENTER);

        preparerScene(flowPane);

        primaryStage.setScene(scene);

        primaryStage.show();
    }
}
