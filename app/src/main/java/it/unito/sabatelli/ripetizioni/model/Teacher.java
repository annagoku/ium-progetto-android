package it.unito.sabatelli.ripetizioni.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Teacher implements Serializable {
    String badge;
    String name;
    String surname;
    ArrayList<Course> courseTeached= new ArrayList<>();
    ArrayList<String> courseTeacherLinked= new ArrayList<>();

    public ArrayList<String> getCourseTeacherLinked() {

        for(Course c: courseTeached) {
            courseTeacherLinked.add(c.getName());
        }
        return courseTeacherLinked;
    }


    public ArrayList<Course> getCourseTeached() {
        return courseTeached;
    }



    public void setCourseTeached(ArrayList<Course> courseTeached) {
        this.courseTeached = courseTeached;
    }

    public String getFullName() {
        return getName()+" "+getSurname();
    }

    public String getBadge() {
        return badge;
    }

    public void setBadge(String badge) {
        this.badge = badge;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
}
