package it.unito.sabatelli.ripetizioni.model;

import java.io.Serializable;

public class Slot implements Serializable {
    int id;
    String startHour;
    String endHour;

    public int getId() {
        return id;
    }

    public void setId(int code) {
        this.id = code;
    }

    public String getStartHour() {
        return startHour;
    }

    public void setStartHour(String startHour) {
        this.startHour = startHour;
    }

    public String getEndHour() {
        return endHour;
    }

    public void setEndHour(String endtHour) {
        this.endHour = endtHour;
    }
}
