package it.unito.sabatelli.ripetizioni.model;

import java.io.Serializable;

public class LessonState implements Serializable {
    int statecode;
    String statename;

    public int getStatecode() {
        return statecode;
    }

    public void setStatecode(int statecode) {
        this.statecode = statecode;
    }

    public String getStatename() {
        return statename;
    }

    public void setStatename(String statename) {
        this.statename = statename;
    }
}
