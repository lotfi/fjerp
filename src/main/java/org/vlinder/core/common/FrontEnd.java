package org.vlinder.core.common;

import java.util.Iterator;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.vlinder.core.model.Brick;
import org.vlinder.core.model.RefData;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public abstract class FrontEnd implements EventHandler<ActionEvent>
{
    static final Logger log = LogManager.getLogger(FrontEnd.class);

    private Scene scene;

    private Pane pane;

    protected Stage stage;

    private int position, size, page;

    private BackEnd backEnd;

    protected Button storeButton, modifyButton, searchButton, deleteButton, loadButton, closeButton;

    protected Button goFirstButton, goLastButton, goNextButton, goBackButton, goUpButton, goDownButton, refreshButton;

    protected Button[] formButtons;

    protected Button[] tableButtons;

    protected TextArea console;

    public int getPosition()
    {
        return position;
    }

    public void setPosition(int position)
    {
        this.position = position;
    }

    public int getSize()
    {
        return size;
    }

    public void setSize(int size)
    {
        this.size = size;
    }

    public int getPage()
    {
        return page;
    }

    public void setPage(int page)
    {
        this.page = page;
    }

    public abstract void populateFront();

    public abstract void populateBack();

    public abstract void clearFront();

    public abstract String[] convertRecordToRow(Object record);

    // public abstract void prepareFront(JPanel panel);
    public abstract void prepareFront(Pane pane);

    public BackEnd getBackEnd()
    {
        return this.backEnd;
    }

    private void buildButtons()
    {
        log.debug("buildButtons");

        storeButton = new Button("store");

        modifyButton = new Button("modify");

        searchButton = new Button("search");

        deleteButton = new Button("delete");

        loadButton = new Button("load");

        closeButton = new Button("close");

        goFirstButton = new Button("go_first");

        goLastButton = new Button("go_last");

        goNextButton = new Button("go_next");

        goBackButton = new Button("go_back");

        goUpButton = new Button("go_up");

        goDownButton = new Button("go_down");

        refreshButton = new Button("refresh");

        formButtons = new Button[]
        { storeButton, modifyButton, searchButton, deleteButton, loadButton };

        tableButtons = new Button[]
        { goFirstButton, goLastButton, goNextButton, goBackButton, goUpButton, goDownButton, refreshButton };

        for (int i = 0; i < formButtons.length; i++)
        {
            formButtons[i].setOnAction(this);
        }

        for (int i = 0; i < tableButtons.length; i++)
        {
            tableButtons[i].setOnAction(this);
        }

        closeButton.setOnAction(this);
    }

    public FrontEnd(BackEnd backEnd, Scene scene, Stage stage)
    {
        this.backEnd = backEnd;
        this.scene = scene;
        this.stage = stage;

        buildButtons();

        this.console = new TextArea();
    }

    public FrontEnd(Scene scene, Stage stage)
    {
        this.scene = scene;
        this.stage = stage;

        buildButtons();

        this.console = new TextArea();
    }

    public void populateComboBox(ComboBox<String> combo, List<RefData> items)
    {
        log.debug("populateComboBox");

        for (Iterator<RefData> codeIterator = items.iterator(); codeIterator.hasNext();)
        {
            RefData code = codeIterator.next();

            combo.getItems().add((String) code.getCode());
        }
    }

    abstract public void populateTable();

    public Scene getScene()
    {
        return this.scene;
    }

    public Node findComponent(String id)
    {
        log.debug("findComponent");

        for (int i = 0; i < pane.getChildren().size(); i++)
        {
            Node node = pane.getChildren().get(i);

            if (
                node.getId().equals(id) == true
            )
            {
                return node;
            }
        }

        return null;
    }

    public void printNotices()
    {
        log.debug("printNotices");

        this.console.setText("");

        StringBuffer content = new StringBuffer("");

        while (
            backEnd.notices.isEmpty() == false
        )
        {
            Brick notice = backEnd.notices.pop();

            content.append(notice.getName() + "\n");
        }

        this.console.setText(content.toString());

    }

    public Pane getPane()
    {
        return pane;
    }

    public void setPane(Pane pane)
    {
        this.pane = pane;
    }

}
