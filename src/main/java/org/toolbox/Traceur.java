package org.toolbox;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Path;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Traceur extends Application
{
    int gh = 5;

    int gl = 5;

    int dx = 30, dy = 30;

    int xmin = -300, xmax = 500;

    int ymin = -300, ymax = 500;

    String nomPolice = "IBM Plex Mono";

    private void tracerFonction(Group groupe)
    {

        Color couleur = Color.rgb(0, 142, 158);

        double xa = 0;

        double ya = 0;

        int rayon = 2;

        // TODO parametrer debut, fin et incrementation
        for (int x = xmin; x < xmax; x = x + 2)
        {
            // TODO verifier abs(xmin) <= x <= abs(xmax)

            // TODO a parametrer
            double y = (2 * x) + 3;

            // cas x
            if (
                x > 0
            )
            {
                xa = x + Math.abs(xmin);
            } else if (
                x <= 0
            )
            {
                xa = Math.abs(xmin) - Math.abs(x);
            }

            // cas y
            if (
                y > 0
            )
            {
                ya = Math.abs(ymax) - y;
            } else if (
                y <= 0
            )
            {
                ya = Math.abs(ymax) + Math.abs(y);
            }

            Circle point = new Circle(xa, ya, rayon);

            point.setStroke(couleur);

            groupe.getChildren().add(point);
        }
    }

    private void dessinerAbscisse(Group groupe, Font fonte)
    {
        Color couleur1 = Color.rgb(31, 83, 93);

        // abscisse
        Line ligne = new Line();

        ligne.setStroke(couleur1);

        ligne.setStartX(0);

        ligne.setStartY(ymax);

        ligne.setEndX(Math.abs(xmin) + xmax);

        ligne.setEndY(ymax);

        groupe.getChildren().add(ligne);

        // graduations
        for (int xi = xmin; xi <= xmax; xi = xi + dx)
        {
            Line grad = new Line();

            grad.setStroke(couleur1);

            grad.setStartX(xi);

            grad.setStartY(ymax - (gl / 2));

            grad.setEndX(xi);

            grad.setEndY(ymax + (gh / 2));

            groupe.getChildren().add(grad);

            Text label = new Text(Integer.toString(xi - Math.abs(xmin)));

            label.setStroke(couleur1);

            label.setX(xi);

            label.setY(ymax + (gh * 3));

            groupe.getChildren().add(label);
        }

    }

    private void dessinerOrdonnee(Group groupe, Font fonte)
    {
        Color couleur1 = Color.rgb(31, 83, 93);

        // abscisse
        Line ligne = new Line();

        ligne.setStroke(couleur1);

        ligne.setStartX(Math.abs(xmin));

        ligne.setStartY(0);

        ligne.setEndX(Math.abs(xmin));

        ligne.setEndY(Math.abs(ymin) + Math.abs(ymax));

        groupe.getChildren().add(ligne);

        // graduations
        for (int yi = 0; yi <= (Math.abs(ymin) + ymax); yi = yi + dy)
        {
            Line grad = new Line();

            grad.setStroke(couleur1);

            grad.setStartX(Math.abs(xmin) - (gl / 2));

            grad.setStartY(yi);

            grad.setEndX(Math.abs(xmin) + (gl / 2));

            grad.setEndY(yi);

            groupe.getChildren().add(grad);

            Text label = new Text(Integer.toString(ymax - yi));

            label.setStroke(couleur1);

            label.setX(Math.abs(xmin) - (gl * 5));

            label.setY(yi);

            groupe.getChildren().add(label);
        }

    }

    /*
     * private void dessinerOrdonnee(Group groupe) { // ordoneee Line ligne = new
     * Line();
     * 
     * ligne.setStartX(mx / 2);
     * 
     * ligne.setStartY(0);
     * 
     * ligne.setEndX(mx / 2);
     * 
     * ligne.setEndY(my);
     * 
     * groupe.getChildren().add(ligne);
     * 
     * // graduations (carrés) int y = dy;
     * 
     * while ( y < my ) { Rectangle rect = new Rectangle();
     * 
     * rect.setX((mx / 2) - gx);
     * 
     * rect.setY(y - gy);
     * 
     * rect.setWidth(gx);
     * 
     * rect.setHeight(gy);
     * 
     * groupe.getChildren().add(rect);
     * 
     * y = y + dy; } }
     */

    @Override
    public void start(Stage stage) throws Exception
    {
        Font font = Font.font(nomPolice, 10);

        // TODO Auto-generated method stub
        Path path = new Path();

        /*
         * MoveTo moveTo = new MoveTo(108, 71);
         * 
         * path.getElements().add(moveTo);
         * 
         * LineTo lineTo = new LineTo(221, 161);
         * 
         * path.getElements().add(lineTo);
         */

        Group groupe = new Group(path);

        dessinerAbscisse(groupe, font);

        dessinerOrdonnee(groupe, font);

        tracerFonction(groupe);

        Scene scene = new Scene(groupe, 800, 800);

        stage.setScene(scene);

        stage.show();
    }

    public static void main(String[] args)
    {
        launch(args);
    }

}
