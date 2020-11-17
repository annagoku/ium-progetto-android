package it.unito.sabatelli.ripetizioni.model;

import java.io.Serializable;

public class LessonState implements Serializable {
    int code;
    String name;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
