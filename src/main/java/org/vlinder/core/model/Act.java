package org.vlinder.core.model;

import java.util.Date;

public class Act
{
    public static final int ACT_NONE = 0;

    public static final int ACT_STORE = 1;

    public static final int ACT_MODIFY = 2;

    public static final int ACT_DELETE = 3;

    public static final int ACT_LOAD = 4;

    public static final int ACT_LOGIN = 5;

    public static final int ACT_LOGOUT = 6;

    public static final int ACT_SEARCH = 7;

    public static final int ACT_EXPORT = 8;

    public static final int ACT_IMPORT = 9;

    private int id;

    private Date startTime;

    private Date endTime;

    private byte timeUnit;

    private int duration;

    private String code = "";

    private byte classId;

    private int nextId;

    private int prevId;

    private String uid = "";

    public Act()
    {
        this.code = "";
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getCode()
    {
        return code;
    }

    public void setCode(String code)
    {
        this.code = code;
    }

    public byte getClassId()
    {
        return classId;
    }

    public void setClassId(byte classId)
    {
        this.classId = classId;
    }

    public Date getStartTime()
    {
        return startTime;
    }

    public void setStartTime(Date startTime)
    {
        this.startTime = startTime;
    }

    public Date getEndTime()
    {
        return endTime;
    }

    public void setEndTime(Date endTime)
    {
        this.endTime = endTime;
    }

    public byte getTimeUnit()
    {
        return timeUnit;
    }

    public void setTimeUnit(byte timeUnit)
    {
        this.timeUnit = timeUnit;
    }

    public int getDuration()
    {
        return duration;
    }

    public void setDuration(int duration)
    {
        this.duration = duration;
    }

    public int getNextId()
    {
        return nextId;
    }

    public void setNextId(int nextId)
    {
        this.nextId = nextId;
    }

    public int getPrevId()
    {
        return prevId;
    }

    public void setPrevId(int prevId)
    {
        this.prevId = prevId;
    }

    public String getUid()
    {
        return uid;
    }

    public void setUid(String uid)
    {
        this.uid = uid;
    }

}
