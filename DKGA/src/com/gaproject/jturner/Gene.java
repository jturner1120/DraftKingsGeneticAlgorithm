package com.gaproject.jturner;

/**
 * Created by jefft on 12/28/2016.
 */
public class Gene {

    private String name;
    private String position;
    private float avgPoints;
    private int salary;

    public Gene(String n, String p, float avg, int sal){
        this.name = n;
        this.position = p;
        this.avgPoints = avg;
        this.salary = sal;
    }

    public String getName(){
        return this.name;
    }
    public void setName(String n){
        this.name = n;
    }

    public String getPosition(){
        return this.position;
    }
    public void setPosition(String p) {
        this.position = p;
    }

    public float getAvgPoints(){
        return this.avgPoints;
    }
    public void setAvgPoints(float avgp){
        this.avgPoints = avgp;
    }

    public int getSalary(){return this.salary;}
    public void setSalary(int sal){
        this.salary = sal;
    }

}
