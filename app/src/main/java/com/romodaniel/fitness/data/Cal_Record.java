package com.romodaniel.fitness.data;

/**
 * Created by fy on 7/27/17.
 */

public class Cal_Record {
    private int value;
    private String date;
    private String desc;

    public Cal_Record(int value, String date, String desc)
    {
        this.value = value;
        this.date = date;
        this.desc = desc;
    }

    public int getValue()
    {
        return value;
    }

    public void setValue(int value)
    {
        this.value = value;
    }

    public String getDate()
    {
        return date;
    }

    public void setDate(String date)
    {
        this.date = date;
    }
    public String getDesc()
    {
        return desc;
    }

    public void setDesc(String desc)
    {
        this.desc = desc;
    }


}
