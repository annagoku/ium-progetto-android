package it.unito.sabatelli.ripetizioni.model;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Lesson implements Serializable {
    int id;
    Teacher teacher;
    Day day;
    Slot slot;
    Course course;
    LessonState state;

    @NonNull
    @Override
    public String toString() {
        return day.getDayname()+" "+slot.getStartHour()+" "+course.getName()+ " "+teacher.getFullName();
    }

    public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
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

        public LessonState getState() {
            return state;
        }

        public void setState(LessonState state) {
            this.state = state;
        }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
}
