package com.romodaniel.fitness.data;

/**
 * Created by Welcome To Future on 7/25/2017.
 */

public class User
{
    String fName;
    String lName;
    String sex;
    int lbs;
    int height;

    public User(String fName, String lName, String sex, int lbs, int height) {
        this.fName = fName;
        this.lName = lName;
        this.sex = sex;
        this.lbs = lbs;
        this.height = height;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getLbs() {
        return lbs;
    }

    public void setLbs(int lbs) {
        this.lbs = lbs;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public String toString() {
        return "User{" +
                "fName='" + fName + '\'' +
                ", lName='" + lName + '\'' +
                ", sex='" + sex + '\'' +
                ", lbs=" + lbs +
                ", height=" + height +
                '}';
    }
}
