package org.forma.view;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.forma.model.Codif;
import org.forma.service.CodifService;
import org.springframework.stereotype.Component;
import org.vlinder.core.common.FrontEnd;
import org.vlinder.core.model.Brick;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

@Component
public class CodifView extends FrontEnd implements EventHandler<ActionEvent>
{
    static final Logger log = LogManager.getLogger(CodifView.class);

    private CodifService codifService;

    private TextField codeField, labelField, categField, parentField, etatField;

    private ComboBox<String> stateCombo;

    private TableView<Codif> table;

    private Codif codif = new Codif();

    private List<Codif> codifDataSet = new LinkedList<Codif>();

    private Stack<Brick> notices = new Stack<Brick>();

    private Scene menuScene;

    private Scene authScene;

    private Stage primaryStage;

    public CodifView(Scene scene, Stage stage)
    {
        super(scene, stage);

    }

    public Codif getCodif()
    {
        return codif;
    }

    public void setCodif(Codif codif)
    {
        this.codif = codif;
    }

    public void setMenuScene(Scene scene)
    {
        this.menuScene = scene;
    }

    public void setAuthScene(Scene scene)
    {
        this.authScene = scene;
    }

    public void setPrimaryStage(Stage primaryStage)
    {
        this.primaryStage = primaryStage;
    }

    public void pushMessage(Throwable throwable)
    {
        log.debug("pushMessage");

        StringBuilder builder = new StringBuilder();

        StackTraceElement[] traces = throwable.getStackTrace();

        for (StackTraceElement traceElement : traces)
        {
            builder.append("\tat " + traceElement + "\n");
        }

        Brick msg = new Brick(builder.toString(), Brick.BRICK_EXCEPT);

        notices.push(msg);
    }

    public void pushMessage(String message, int type)
    {
        log.debug("pushMessage");

        Brick msg = new Brick(message, type);

        notices.push(msg);
    }

    public CodifService getCodifService()
    {
        return codifService;
    }

    public void setCodifService(CodifService codifService)
    {
        this.codifService = codifService;
    }

    @Override
    public void clearFront()
    {
        log.debug("clearFront");

        codif.setCode("");
        codif.setCategorie("");
        codif.setEtat("1");

        populateFront();
    }

    @Override
    public void populateFront()
    {
        log.debug("populateFront");

        // backend -> ui (record)

        // code
        codeField.setText("");

        if (
            codif.getCode() != null
        )
        {
            codeField.setText(codif.getCode());
        }

        // categorie
        categField.setText("");

        if (
            codif.getCategorie() != null
        )
        {
            categField.setText(codif.getCategorie());
        }

        // parent
        parentField.setText("");

        if (
            codif.getParent() != null && codif.getParent().getCode() != null
        )
        {
            parentField.setText(codif.getParent().getCode());
        }

        // etat
        etatField.setText("");

        if (
            codif.getEtat() != null
        )
        {
            etatField.setText(codif.getEtat());
        }

        // console
        console.setText("");

        // backend -> ui (table)
        populateTable();
    }

    @Override
    public void populateBack()
    {
        log.debug("populateBack");

        codif.setCode("");
        codif.setCategorie("");
        codif.setParent(null);

        codif.setEtat("1");

        // code
        if (
            codeField.getText() != null
        )
        {
            codif.setCode(codeField.getText());
        }

        // categorie
        if (
            categField.getText() != null
        )
        {
            codif.setCategorie(categField.getText());
        }

        // parent id
        String value = parentField.getText();

        if (
            value != null && value.isEmpty() == false
        )
        {
            Codif parent = new Codif();

            parent.setCode(parentField.getText());

            codif.setParent(parent);
        }

        // etat
        if (
            etatField.getText() != null
        )
        {
            codif.setEtat(etatField.getText());
        }
    }

    @Override
    public String[] convertRecordToRow(Object record)
    {
        log.debug("convertRecordToRow");

        String[] rowData = new String[5];

        for (int i = 0; i < rowData.length; i++)
        {
            rowData[i] = new String("");
        }

        Codif codif = (Codif) record;

        rowData[0] = codif.getCode();

        rowData[1] = codif.getLabel();

        rowData[2] = codif.getCategorie();

        rowData[3] = codif.getEtat();

        rowData[4] = "";

        if (
            codif.getParent() != null && codif.getParent().getCode() != null
        )
        {
            rowData[4] = codif.getParent().getCode();
        }

        return rowData;
    }

    @Override
    public void prepareFront(Pane mainPane)
    {
        log.debug("prepareFront");

        // store panel reference
        setPane(mainPane);

        GridPane gridPane = (GridPane) mainPane;

        // grid pane0
        GridPane grid0 = new GridPane();

        // menu button
        Button menuButton = new Button("Go Menu");

        menuButton.setOnAction(this);

        // add menu button to grid0
        grid0.add(menuButton, 0, 0);

        // add grid0 to gridPane
        gridPane.add(grid0, 0, 0);

        // grid pane1
        GridPane grid1 = new GridPane();

        grid1.setHgap(5);

        grid1.setVgap(5);

        double[] widths =
        { 25, 75 };

        ColumnConstraints[] colConst1 = new ColumnConstraints[widths.length];

        for (int i = 0; i < colConst1.length; i++)
        {
            colConst1[i] = new ColumnConstraints();

            colConst1[i].setPercentWidth(widths[i]);

            grid1.getColumnConstraints().add(colConst1[i]);
        }

        RowConstraints rowConst1 = new RowConstraints();

        rowConst1.setPercentHeight(12.5);

        grid1.getRowConstraints().add(rowConst1);

        // form
        int fieldIndex = 0;

        int row = 0;

        int col = 0;

        String[] names =
        { "code", "categorie", "label", "etat", "parent" };

        Label[] labels = new Label[names.length];

        for (int i = 0; i < labels.length; i++)
        {
            labels[i] = new Label(names[i]);
        }

        // code field
        row++;

        grid1.add(labels[fieldIndex++], col, row);

        codeField = new TextField("");

        grid1.add(codeField, col + 1, row);

        // categorie field
        row++;

        grid1.add(labels[fieldIndex++], col, row);

        categField = new TextField("");

        grid1.add(categField, col + 1, row);

        // label field
        row++;

        grid1.add(labels[fieldIndex++], col, row);

        labelField = new TextField("");

        grid1.add(labelField, col + 1, row);

        // etat field
        row++;

        grid1.add(labels[fieldIndex++], col, row);

        etatField = new TextField("");

        grid1.add(etatField, col + 1, row);

        // parent field
        row++;

        grid1.add(labels[fieldIndex++], col, row);

        parentField = new TextField("");

        grid1.add(parentField, col + 1, row);

        // grid pane2
        GridPane grid2 = new GridPane();

        ColumnConstraints colConst2 = new ColumnConstraints();

        colConst2.setPercentWidth(20);

        grid2.getColumnConstraints().add(colConst2);

        // add grid1 to gridPane
        gridPane.add(grid1, 0, 1);

        // form buttons
        for (int i = 0; i < formButtons.length; i++)
        {
            grid2.add(formButtons[i], col++, row);
        }

        // add childGrid2 to gridPane
        gridPane.add(grid2, 0, 2);

        // table
        GridPane grid3 = new GridPane();

        table = new TableView<Codif>();

        table.setPrefSize(250, 250);

        // table column (code)
        TableColumn<Codif, String> codeCol = new TableColumn<Codif, String>("code");

        codeCol.setCellValueFactory(new PropertyValueFactory<>("code"));

        table.getColumns().add(codeCol);

        // table column (label)
        TableColumn<Codif, String> labelCol = new TableColumn<Codif, String>("label");

        labelCol.setCellValueFactory(new PropertyValueFactory<>("label"));

        table.getColumns().add(labelCol);

        // table column (classId)

        // table column (state)

        // table column (parent)

        // dataset: ObservableList<UserAccount> list =
        // FXCollections.observableArrayList(user1, user2, user3);

        // dataset: table.setItems(list);

        VBox vbox = new VBox();

        vbox.setSpacing(5);

        vbox.setPadding(new Insets(10, 0, 0, 10));

        vbox.getChildren().addAll(table);

        grid3.add(vbox, 0, 0);

        // add childGrid3 to gridPane
        gridPane.add(grid3, 0, 3);

        // table actions
        GridPane childGrid4 = new GridPane();

        for (int i = 0; i < tableButtons.length; i++)
        {
            childGrid4.add(tableButtons[i], i, 0);
        }

        // add childGrid4 to gridPane
        gridPane.add(childGrid4, 0, 3);

        ColumnConstraints colConst4 = new ColumnConstraints();

        colConst4.setPercentWidth((double) 100 / tableButtons.length);

        childGrid4.getColumnConstraints().add(colConst4);

        // console
        col = 0;

        row++;

        console = new TextArea();

        console.setPrefSize(150, 50);

        gridPane.add(console, col, row);

        // gridPane.getColumnConstraints().add( computeColumnConstraints("console") );

        // gridPane.getRowConstraints().add( computeRowConstraints("console") );

    }

    public void populateTable()
    {
        log.debug("populateTable");

        ObservableList<Codif> codifList = FXCollections.observableArrayList(codifDataSet);

        table.setItems(codifList);

    }

    @Override
    public void printNotices()
    {
        log.debug("printNotices");

        this.console.setText("");

        StringBuffer content = new StringBuffer("");

        while (
            this.notices.isEmpty() == false
        )
        {
            Brick notice = this.notices.pop();

            content.append(notice.getName() + "\n");
        }

        this.console.setText(content.toString());

    }

    @Override
    public void handle(ActionEvent event)
    {
        log.debug("handle");

        log.debug("actionPerformed");

        try
        {

            if (
                event.getSource() instanceof Button
            )
            {
                Button bouton = (Button) event.getSource();

                if (
                    bouton.getText().equals("Go Menu")
                )
                {
                    primaryStage.setScene(authScene);

                    // primaryStage.show();

                } else if (
                    bouton.getText().equals("store")
                )
                {
                    populateBack();

                    codifService.store(codif);

                } else if (
                    bouton.getText().equals("modify")
                )
                {
                    populateBack();

                    codifService.modify(codif);

                } else if (
                    bouton.getText().equals("search")
                )
                {
                    populateBack();

                    codifService.search(codif);

                    populateTable();

                } else if (
                    bouton.getText().equals("delete")
                )
                {
                    populateBack();

                    codifService.delete(codif);

                    populateFront();
                } else if (
                    bouton.getText().equals("load")
                )
                {
                    populateBack();

                    codifService.load(codif);

                    codifDataSet.clear();

                    codifDataSet.add(codif);

                    populateFront();

                } else if (
                    bouton.getText().equals("gofirst")
                )
                {
                    codifService.goFirst();

                    populateTable();
                } else if (
                    bouton.getText().equals("gonext")
                )
                {
                    codifService.goNext();

                    populateTable();
                } else if (
                    bouton.getText().equals("goprevious")
                )
                {
                    codifService.goPrevious();

                    populateTable();
                } else if (
                    bouton.getText().equals("golast")
                )
                {
                    codifService.goLast();

                    populateTable();
                }

                // imprimer messages
                printNotices();
            }

        }
        // String[] noms1 = {"enregistrer","modifier","chercher","supprimer","raz"};

        catch (Exception e)
        {
            log.error(e);

            console.setText(e.toString());
        }

        finally
        {
            stage.show();
        }

    }

}