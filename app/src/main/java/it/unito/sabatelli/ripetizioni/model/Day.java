package it.unito.sabatelli.ripetizioni.model;

import java.io.Serializable;

public class Day implements Serializable {
    int daycode;
    String dayname;

    public int getDaycode() {
        return daycode;
    }

    public void setDaycode(int daycode) {
        this.daycode = daycode;
    }

    public String getDayname() {
        return dayname;
    }

    public void setDayname(String dayname) {
        this.dayname = dayname;
    }
}
