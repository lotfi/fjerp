package org.vlinder.core.model;

import java.util.Date;

public class RefData extends Atom
{

    private String code = "";
    private int classId;
    private int parentId;
    private Date stateTime;
    private String stateCode = "";
    private int actId;

    public RefData()
    {

    }

    public RefData(String code, int classId)
    {
        this.code = code;
        this.classId = classId;
    }

    public RefData(String code)
    {
        this.code = code;
    }

    public String getCode()
    {
        return code;
    }

    public void setCode(String code)
    {
        this.code = code;
    }

    public int getParentId()
    {
        return parentId;
    }

    public void setParentId(int parentId)
    {
        this.parentId = parentId;
    }

    public int getClassId()
    {
        return classId;
    }

    public void setClassId(int classId)
    {
        this.classId = classId;
    }

    public String getStateCode()
    {
        return stateCode;
    }

    public void setStateCode(String stateCode)
    {
        this.stateCode = stateCode;
    }

    public Date getStateTime()
    {
        return stateTime;
    }

    public void setStateTime(Date storeTime)
    {
        this.stateTime = storeTime;
    }

    public int getActId()
    {
        return actId;
    }

    public void setActId(int actId)
    {
        this.actId = actId;
    }

}
