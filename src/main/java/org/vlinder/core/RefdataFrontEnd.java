package org.vlinder.core;

import java.util.LinkedList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.vlinder.core.common.BackEnd;
import org.vlinder.core.common.FrontEnd;
import org.vlinder.core.model.RefData;
import org.vlinder.core.service.Persistance;

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
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class RefdataFrontEnd extends FrontEnd implements EventHandler<ActionEvent>
{
    static final Logger log = LogManager.getLogger(RefdataFrontEnd.class);

    private TextField idField, codeField, parentField, stateField;

    private ComboBox<String> stateCombo;

    private TableView<RefData> table;

    private RefData refdata = new RefData();

    private List<RefData> refdataSet = new LinkedList<RefData>();

    public RefdataFrontEnd(BackEnd backEnd, Scene scene, Stage stage)
    {
        super(backEnd, scene, stage);

    }

    public RefData getRefdata()
    {
        return refdata;
    }

    public void setRefdata(RefData refdata)
    {
        this.refdata = refdata;
    }

    @Override
    public void clearFront()
    {
        log.debug("clearFront");

        refdata.setId(0);
        refdata.setCode("");
        refdata.setClassId(0);
        refdata.setParentId(0);
        refdata.setActId(0);

        populateFront();
    }

    @Override
    public void populateFront()
    {
        log.debug("populateFront");

        // backend -> ui (record)

        // id
        idField.setText("");

        if (
            refdata.getId() != 0
        )
        {
            idField.setText(Integer.toString(refdata.getId()));
        }

        // code
        codeField.setText("");

        if (
            refdata.getCode() != null
        )
        {
            codeField.setText(refdata.getCode());
        }

        // classid
        /*
         * classCombo.getSelectionModel().clearSelection();
         * 
         * if ( refdata.getClassId() != 0 ) { RefData selectedClass = null;
         * 
         * if ( selectedClass != null ) { classCombo.getSelectionModel().select((String)
         * selectedClass.getCode()); }
         * 
         * }
         */

        // parent id
        parentField.setText("");

        if (
            refdata.getParentId() != 0
        )
        {
            parentField.setText(Integer.toString(refdata.getParentId()));
        }

        // state code
        stateField.setText("");

        // RefDataHelper.findRefData(RefData.CLASSID_STATE, refdata.getStateCode());

        if (
            refdata.getStateCode() != null
        )
        {
            stateField.setText(refdata.getStateCode());
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

        refdata.setId(0);
        refdata.setCode("");
        refdata.setClassId(0);
        refdata.setParentId(0);

        refdata.setStateCode("");

        // id
        if (
            idField.getText() != null
        )
        {
            String value = idField.getText();

            value = Persistance.oterBlancs(value);

            if (
                value.equals("") == false
            )
            {
                refdata.setId(Integer.parseInt(value));
            }
        }

        // code
        if (
            codeField.getText() != null
        )
        {
            refdata.setCode(codeField.getText());
        }

        // classId
        /*
         * // TODO refdata.setClassId( Byte.parseByte(classIdField.getText()) ); String
         * meta = classCombo.getSelectionModel().getSelectedItem();
         * 
         * if ( meta != null ) { if ( meta.equals("Monnaie") ) { // TODO } else if (
         * meta.equals("Unité mesure") ) { // TODO } }
         */

        // parent id
        String value = parentField.getText();

        if (
            value != null && value.isEmpty() == false
        )
        {
            // TODO check conversion
            refdata.setParentId(Integer.parseInt(parentField.getText()));

        }

        // state code
        if (
            stateField.getText() != null
        )
        {
            refdata.setStateCode(stateField.getText());
        }
    }

    @Override
    public String[] convertRecordToRow(Object record)
    {
        log.debug("convertRecordToRowData");

        String[] rowData = new String[6];

        for (int i = 0; i < rowData.length; i++)
        {
            rowData[i] = new String("");
        }

        RefData refdata = (RefData) record;

        rowData[0] = Integer.toString(refdata.getId());
        rowData[1] = refdata.getCode();
        rowData[2] = "";

        // TODO implement byte to string for classid
        rowData[3] = Integer.toString(refdata.getClassId());

        rowData[4] = "";
        if (
            refdata.getParentId() != 0
        )
        {
            rowData[4] = Integer.toString(refdata.getParentId());
        }

        // TODO implement
        rowData[5] = "";
        if (
            refdata.getStateCode() != null
        )
        {
            rowData[5] = refdata.getStateCode();
        }

        return rowData;
    }

    private ColumnConstraints computeColumnConstraints(String type)
    {
        log.debug("computeColumnConstraints");

        ColumnConstraints colConstraint = new ColumnConstraints();

        if (
            type.equals("form")
        )
        {
            colConstraint.setMinWidth(30);
            colConstraint.setPrefWidth(50);
            colConstraint.setHgrow(Priority.ALWAYS);
        } else if (
            type.equals("actions")
        )
        {
            colConstraint.setMinWidth(30);
            colConstraint.setPrefWidth(50);
            colConstraint.setHgrow(Priority.ALWAYS);
        } else if (
            type.equals("table")
        )
        {
            colConstraint.setMinWidth(100);
            colConstraint.setPrefWidth(150);
            colConstraint.setHgrow(Priority.ALWAYS);
        } else if (
            type.equals("console")
        )
        {
            colConstraint.setMinWidth(50);
            colConstraint.setPrefWidth(40);
            colConstraint.setHgrow(Priority.ALWAYS);
        }

        return colConstraint;
    }

    private RowConstraints computeRowConstraints(String type)
    {
        log.debug("computeRowConstraints");

        RowConstraints rowConstraint = new RowConstraints();

        if (
            type.equals("form")
        )
        {
            rowConstraint.setMinHeight(20);
            rowConstraint.setPrefHeight(30);
            rowConstraint.setVgrow(Priority.ALWAYS);
        } else if (
            type.equals("actions")
        )
        {
            rowConstraint.setMinHeight(20);
            rowConstraint.setPrefHeight(30);
            rowConstraint.setVgrow(Priority.ALWAYS);
        } else if (
            type.equals("table")
        )
        {
            rowConstraint.setMinHeight(80);
            rowConstraint.setPrefHeight(90);
            rowConstraint.setVgrow(Priority.ALWAYS);
        } else if (
            type.equals("console")
        )
        {
            rowConstraint.setMinHeight(80);
            rowConstraint.setPrefHeight(90);
            rowConstraint.setVgrow(Priority.ALWAYS);

        }

        return rowConstraint;
    }

    @Override
    public void prepareFront(Pane mainPane)
    {
        log.debug("prepareFront");

        // store panel reference
        setPane(mainPane);

        GridPane gridPane = (GridPane) mainPane;

        // child grid pane1
        GridPane childGrid1 = new GridPane();

        childGrid1.setHgap(5);

        childGrid1.setVgap(5);

        double[] colWidths =
        { 25, 75 };

        ColumnConstraints[] grid1ColCtn = new ColumnConstraints[colWidths.length];

        for (int i = 0; i < grid1ColCtn.length; i++)
        {
            grid1ColCtn[i] = new ColumnConstraints();

            grid1ColCtn[i].setPercentWidth(colWidths[i]);

            childGrid1.getColumnConstraints().add(grid1ColCtn[i]);
        }

        RowConstraints grid1RowCtn = new RowConstraints();

        grid1RowCtn.setPercentHeight(12.5);

        childGrid1.getRowConstraints().add(grid1RowCtn);

        // form
        int fieldIndex = 0;

        int row = 0;

        int col = 0;

        String[] names =
        { "id", "code", "state", "parent" };

        Label[] labels = new Label[names.length];

        for (int i = 0; i < labels.length; i++)
        {
            labels[i] = new Label(names[i]);
        }

        // id field
        childGrid1.add(labels[fieldIndex++], col, row);

        idField = new TextField("");

        childGrid1.add(idField, col + 1, row);

        // code field
        row++;

        childGrid1.add(labels[fieldIndex++], col, row);

        codeField = new TextField("");

        childGrid1.add(codeField, col + 1, row);

        // state field
        row++;

        childGrid1.add(labels[fieldIndex++], col, row);

        stateField = new TextField("");

        childGrid1.add(stateField, col + 1, row);

        // parent field
        row++;

        childGrid1.add(labels[fieldIndex++], col, row);

        parentField = new TextField("");

        childGrid1.add(parentField, col + 1, row);

        // child grid pane2
        GridPane childGrid2 = new GridPane();

        ColumnConstraints grid2ColCtn = new ColumnConstraints();

        grid2ColCtn.setPercentWidth(20);

        childGrid2.getColumnConstraints().add(grid2ColCtn);

        // add childGrid1 to gridPane
        gridPane.add(childGrid1, 0, 0);

        // form buttons
        for (int i = 0; i < formButtons.length; i++)
        {
            childGrid2.add(formButtons[i], col++, row);
        }

        // add childGrid2 to gridPane
        gridPane.add(childGrid2, 0, 1);

        // table
        GridPane childGrid3 = new GridPane();

        table = new TableView<RefData>();

        table.setPrefSize(250, 250);

        // table column (id)
        TableColumn<RefData, Integer> idCol = new TableColumn<RefData, Integer>("id");

        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));

        table.getColumns().add(idCol);

        // table column (code)
        TableColumn<RefData, Integer> codeCol = new TableColumn<RefData, Integer>("code");

        codeCol.setCellValueFactory(new PropertyValueFactory<>("code"));

        table.getColumns().add(codeCol);

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

        childGrid3.add(vbox, 0, 0);

        // add childGrid3 to gridPane
        gridPane.add(childGrid3, 0, 2);

        // table actions
        GridPane childGrid4 = new GridPane();

        for (int i = 0; i < tableButtons.length; i++)
        {
            childGrid4.add(tableButtons[i], i, 0);
        }

        // add childGrid4 to gridPane
        gridPane.add(childGrid4, 0, 3);

        ColumnConstraints grid4ColCtn = new ColumnConstraints();

        grid4ColCtn.setPercentWidth((double) 100 / tableButtons.length);

        childGrid4.getColumnConstraints().add(grid4ColCtn);

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

        ObservableList<RefData> refdataList = FXCollections.observableArrayList(refdataSet);

        table.setItems(refdataList);

    }

    @Override
    public void handle(ActionEvent event)
    {
        log.debug("handle");

        log.debug("actionPerformed");

        RefDataBackEnd refdataBackEnd = (RefDataBackEnd) getBackEnd();

        try
        {

            if (
                event.getSource() instanceof Button
            )
            {
                Button bouton = (Button) event.getSource();

                if (
                    bouton.getText().equals("store")
                )
                {
                    populateBack();

                    refdataBackEnd.store(refdata);

                } else if (
                    bouton.getText().equals("modify")
                )
                {
                    populateBack();

                    refdataBackEnd.modify(refdata);

                } else if (
                    bouton.getText().equals("search")
                )
                {
                    populateBack();

                    refdataBackEnd.search(refdata);

                    populateTable();

                } else if (
                    bouton.getText().equals("delete")
                )
                {
                    populateBack();

                    refdataBackEnd.delete(refdata);

                    populateFront();
                } else if (
                    bouton.getText().equals("load")
                )
                {
                    populateBack();

                    refdataBackEnd.load(refdata);

                    refdataSet.clear();

                    refdataSet.add(refdata);

                    populateFront();

                } else if (
                    bouton.getText().equals("gofirst")
                )
                {
                    refdataBackEnd.goFirst();

                    populateTable();
                } else if (
                    bouton.getText().equals("gonext")
                )
                {
                    refdataBackEnd.goNext();

                    populateTable();
                } else if (
                    bouton.getText().equals("goprevious")
                )
                {
                    refdataBackEnd.goPrevious();

                    populateTable();
                } else if (
                    bouton.getText().equals("golast")
                )
                {
                    refdataBackEnd.goLast();

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