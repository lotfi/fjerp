package org.vlinder.client;

import org.scilab.forge.jlatexmath.TeXFormula;

import javafx.application.Application;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class FXGraphicsDemo extends Application
{
    @Override
    public void start(Stage stage) throws Exception
    {
        Font.loadFont(getClass().getResourceAsStream("/org/scilab/forge/jlatexmath/fonts/base/jlm_cmmi10.ttf"), 1);
        Font.loadFont(getClass().getResourceAsStream("/org/scilab/forge/jlatexmath/fonts/maths/jlm_cmsy10.ttf"), 1);
        Font.loadFont(getClass().getResourceAsStream("/org/scilab/forge/jlatexmath/fonts/latin/jlm_cmr10.ttf"), 1);

        TeXFormula f = new TeXFormula("x=\\frac{-b \\pm \\sqrt {b^2-4ac}}{2a}");

        /*
         * FormulaCanvas canvas = new FormulaCanvas(f); StackPane stackPane = new
         * StackPane(); stackPane.getChildren().add(canvas);
         * 
         * // Bind canvas size to stack pane size.
         * canvas.widthProperty().bind(stackPane.widthProperty());
         * canvas.heightProperty().bind(stackPane.heightProperty()); stage.setScene(new
         * Scene(stackPane)); stage.setTitle("FXGraphics2DDemo.java");
         * stage.setWidth(300); stage.setHeight(100);
         * 
         * stage.show();
         */
    }

    public static void main(String[] args)
    {
        launch(args);
    }

}
