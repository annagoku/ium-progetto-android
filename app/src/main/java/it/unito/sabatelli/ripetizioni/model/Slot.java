package it.unito.sabatelli.ripetizioni.model;

import java.io.Serializable;

public class Slot implements Serializable {
    int code;
    String startHour;
    String endHour;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
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
