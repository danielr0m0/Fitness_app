package com.romodaniel.fitness.data;

/**
 * Created by drdan on 7/26/2017.
 */

public class Runs {
    private double cal;
    private double miles;
    private long steps;
    private int time;


    public Runs(double cal, double miles, long steps, int time) {
        this.cal = cal;
        this.miles = miles;
        this.steps = steps;
        this.time = time;
    }

    public double getCal() {
        return cal;
    }

    public void setCal(double cal) {
        this.cal = cal;
    }

    public double getMiles() {
        return miles;
    }

    public void setMiles(double miles) {
        this.miles = miles;
    }

    public long getSteps() {
        return steps;
    }

    public void setSteps(long steps) {
        this.steps = steps;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
}
