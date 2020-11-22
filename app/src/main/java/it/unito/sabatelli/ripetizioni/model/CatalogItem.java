package it.unito.sabatelli.ripetizioni.model;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class CatalogItem implements Serializable {
    Teacher teacher;
    Day day;
    Slot slot;
    Course course;
    boolean selected= false;

    public CatalogItem() {

    }

    public CatalogItem (Lesson l) {
        this.teacher = l.getTeacher();
        this.day = l.getDay();
        this.slot = l.getSlot();
        this.course = l.getCourse();
    }

    @Override
    public String toString() {
        return day.getDayname()+" "+slot.getStartHour()+" "+course.getName()+ " "+teacher.getFullName();
    }



    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public Day getDay() {
        return day;
    }

    public void setDay(Day day) {
        this.day = day;
    }

    public Slot getSlot() {
        return slot;
    }

    public void setSlot(Slot slot) {
        this.slot = slot;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
