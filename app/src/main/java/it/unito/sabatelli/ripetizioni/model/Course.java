package it.unito.sabatelli.ripetizioni.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Course implements Serializable {
    String code;
    String name;

    List<Teacher> teachers = new ArrayList<>();


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Teacher> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<Teacher> teachers) {
        this.teachers = teachers;
    }
}
