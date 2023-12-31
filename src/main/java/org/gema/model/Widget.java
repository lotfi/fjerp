package org.gema.model;

public class Widget extends Node
{
    private static final int UI_NONE = 0;

    private static final int UI_MENU = 1;

    private static final int UI_ITEM = 2;

    private static final int UI_SCREEN = 3;

    private static final int UI_LABEL = 4;

    private static final int UI_FIELD = 5;

    private static final int UI_LIST = 6;

    private static final int UI_TABLE = 7;

    private static final int UI_TABLE_HEADER = 8;

    private static final int UI_TABLE_ROW = 9;

    private static final int UI_TABLE_CELL = 10;

    private static final int UI_ACTION = 11;

    private static final int UI_MESSAGE = 12;

    private static final int UI_AREA = 13;

    private static final int UI_PASSWORD = 14;

    private static final int UI_BUTTON = 15;

    private static final int UI_TREE = 16;

    private String format;
    private int UIType; /* field, pwd, list, checkbox, memo, area, file, image, menu, item, */
    private boolean mandatory;
    private boolean active;
    private boolean shown;
    private int valType;
    private String skin = "";
    private int x, y, height, width;
    private String layout = "";

    private int[] backColors =
    { 0, 0, 0 };

    private int[] frontColors =
    { 0, 0, 0 };

    private int widgetType;

    private boolean visible;

    private boolean enabled;

    public int getWidgetType()
    {
        return widgetType;
    }

    public void setWidgetType(int widgetType)
    {
        this.widgetType = widgetType;
    }

    public boolean isVisible()
    {
        return visible;
    }

    public void setVisible(boolean visible)
    {
        this.visible = visible;
    }

    public boolean isEnabled()
    {
        return enabled;
    }

    public void setEnabled(boolean enabled)
    {
        this.enabled = enabled;
    }

    public boolean isMandatory()
    {
        return mandatory;
    }

    public void setMandatory(boolean mandatory)
    {
        this.mandatory = mandatory;
    }

    public String getFormat()
    {
        return format;
    }

    public void setFormat(String format)
    {
        this.format = format;
    }

    public boolean isActive()
    {
        return active;
    }

    public void setActive(boolean active)
    {
        this.active = active;
    }

    public boolean isShown()
    {
        return shown;
    }

    public void setShown(boolean shown)
    {
        this.shown = shown;
    }

    public int getValType()
    {
        return valType;
    }

    public void setValType(int valType)
    {
        this.valType = valType;
    }

    public String getSkin()
    {
        return skin;
    }

    public void setSkin(String skin)
    {
        this.skin = skin;
    }

    public int getX()
    {
        return x;
    }

    public void setX(int x)
    {
        this.x = x;
    }

    public int getY()
    {
        return y;
    }

    public void setY(int y)
    {
        this.y = y;
    }

    public int getHeight()
    {
        return height;
    }

    public void setHeight(int height)
    {
        this.height = height;
    }

    public int getWidth()
    {
        return width;
    }

    public void setWidth(int width)
    {
        this.width = width;
    }

    public String getLayout()
    {
        return layout;
    }

    public void setLayout(String layout)
    {
        this.layout = layout;
    }

}
