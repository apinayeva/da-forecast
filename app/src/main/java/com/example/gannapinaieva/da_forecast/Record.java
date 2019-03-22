package com.example.gannapinaieva.da_forecast;

public class Record {

    int id;
    String name;
    String temperature;
    boolean box;

    public Record(String name, String temperature) {
        this.name = name;
        this.temperature = temperature;
        this.box = box;
    }

    public Record(int id, String name, String temperature) {
        this.id = id;
        this.name = name;
        this.temperature = temperature;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }


    @Override
    public String toString() {
        return name + ' ' + temperature ;
    }
}
