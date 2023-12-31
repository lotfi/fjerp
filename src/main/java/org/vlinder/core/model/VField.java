package org.vlinder.core.model;

import java.lang.reflect.Field;

public class VField extends Brick
{
    private Field javaField;

    public VField(String name)
    {
        super(name, Brick.BRICK_FIELD);
    }

    public Field getJavaField()
    {
        return javaField;
    }

    public void setJavaField(Field javaField)
    {
        this.javaField = javaField;
    }

}
