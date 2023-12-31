package org.vlinder.client;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.vlinder.core.RefDataBackEnd;
import org.vlinder.core.RefdataFrontEnd;
import org.vlinder.core.service.Persistance;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class FxApp extends Application implements EventHandler<ActionEvent>
{
    private static final Logger log = LogManager.getLogger(FxApp.class);

    private RefdataFrontEnd refdataFrontEnd;

    private RefDataBackEnd refdataBackEnd;

    private Scene authScene;

    private Scene menuScene;

    private Scene refdataScene;

    public static void main(String[] args)
    {
        launch(args);
    }

    private void prepareAuthScene(Stage primaryStage, Pane pane)
    {
        log.debug("prepareAuthScene");

        GridPane gridPane = new GridPane();

        gridPane.setMinSize(400, 200);

        gridPane.setPadding(new Insets(10, 10, 10, 10));

        gridPane.setVgap(5);

        gridPane.setHgap(5);

        // composants

        Label label1 = new Label("uid");

        gridPane.add(label1, 0, 0);

        TextField field1 = new TextField();

        gridPane.add(field1, 1, 0);

        Label label2 = new Label("pwd");

        gridPane.add(label2, 0, 1);

        PasswordField field2 = new PasswordField();

        gridPane.add(field2, 1, 1);

        Button button = new Button("Login");

        gridPane.add(button, 0, 2);

        // rootNode.getChildren().addAll(label1,uidField,label2,pwdField,loginButton);

        authScene = new Scene(gridPane);

        primaryStage.setScene(authScene);

        primaryStage.show();

        // gestionnaire d'evenement
        button.setOnAction(new EventHandler<ActionEvent>()
        {
            public void handle(ActionEvent ae)
            {
                log.debug("handle");

                if (
                    field1.getText().equals("a") && field2.getText().equals("a")
                )
                {
                    primaryStage.setScene(refdataScene);

                    // primaryStage.show();
                }
            }
        });

    }

    private void prepareMenuScene(Pane pane)
    {
        menuScene = new Scene(pane);

    }

    private void prepareRefdataScene(Stage stage)
    {
        log.debug("prepareRefdataScene");

        GridPane gridPane = new GridPane();

        gridPane.setAlignment(Pos.CENTER);

        gridPane.setHgap(20);

        gridPane.setVgap(10);

        refdataScene = new Scene(gridPane, 700, 900);

        Persistance.setup();

        /*
         * DaoImpl daoImpl= new DaoImpl();
         * 
         * daoImpl.setDataSource(Persistance.getDataSource());
         * 
         * Persistance.daoImpl = daoImpl;
         */

        refdataBackEnd = new RefDataBackEnd();

        refdataFrontEnd = new RefdataFrontEnd(refdataBackEnd, refdataScene, stage);

        refdataFrontEnd.prepareFront(gridPane);

        /*
         * 
         * Label label1 = new Label("Label for test purposes only");
         * 
         * Button closeButton = new Button("Close");
         * 
         * closeButton.setOnAction(new EventHandler<ActionEvent>() { public void
         * handle(ActionEvent ae) { log.debug("handle");
         * 
         * primaryStage.setScene(authScene);
         * 
         * } });
         * 
         * rootNode.getChildren().addAll(label1,closeButton);
         */
    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        // TODO Auto-generated method stub
        log.debug("start");

        FlowPane flowPane = new FlowPane(10, 10);

        flowPane.setAlignment(Pos.CENTER);

        prepareAuthScene(primaryStage, flowPane);

        prepareRefdataScene(primaryStage);

        primaryStage.setScene(authScene);

        primaryStage.show();

    }

    @Override
    public void handle(ActionEvent event)
    {
        // TODO Auto-generated method stub

    }

    // TODO Persistance.closeConnection() on exit

}
