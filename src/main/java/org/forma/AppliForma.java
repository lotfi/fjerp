package org.forma;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.forma.service.CodifService;
import org.forma.view.CodifView;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

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

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan(basePackages =
{ "org.forma.service" })
@EnableTransactionManagement
@EnableJpaRepositories(basePackages =
{ "org.forma.repo" })
@EntityScan("org.forma.model")
public class AppliForma extends Application implements EventHandler<ActionEvent>
{
    private static final Logger log = LogManager.getLogger(AppliForma.class);

    private Scene authScene;

    private Scene menuScene;

    private Scene codifScene;

    private CodifView codifView;

    private static ApplicationContext applicationContext;

    public static void main(String[] args)
    {
        log.debug("main");

        applicationContext = SpringApplication.run(AppliForma.class, args);

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
                    primaryStage.setScene(codifScene);

                    // primaryStage.show();
                }
            }
        });

    }

    private void prepareMenuScene(Pane pane)
    {
        log.debug("prepareMenuScene");

        GridPane gridPane = new GridPane();

        gridPane.setMinSize(400, 200);

        gridPane.setPadding(new Insets(10, 10, 10, 10));

        gridPane.setVgap(5);

        gridPane.setHgap(5);

        Button button = new Button("Gérer Codifications");

        gridPane.add(button, 0, 0);

        menuScene = new Scene(pane);

    }

    private void prepareCodifScene(Stage stage)
    {
        log.debug("prepareCodifScene");

        GridPane gridPane = new GridPane();

        gridPane.setAlignment(Pos.CENTER);

        gridPane.setHgap(20);

        gridPane.setVgap(10);

        codifScene = new Scene(gridPane, 700, 900);

        codifView = new CodifView(codifScene, stage);

        codifView.prepareFront(gridPane);

        CodifService codifService = (CodifService) applicationContext.getBean("codifService");

        codifView.setCodifService(codifService);

        codifView.setMenuScene(menuScene);

        codifView.setPrimaryStage(stage);

        codifView.setAuthScene(authScene);

    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        // TODO Auto-generated method stub
        log.debug("start");

        FlowPane flowPane = new FlowPane(10, 10);

        flowPane.setAlignment(Pos.CENTER);

        prepareMenuScene(flowPane);

        prepareAuthScene(primaryStage, flowPane);

        prepareCodifScene(primaryStage);

        primaryStage.setScene(authScene);

        primaryStage.show();

    }

    @Override
    public void handle(ActionEvent event)
    {
        // TODO Auto-generated method stub

    }
}
