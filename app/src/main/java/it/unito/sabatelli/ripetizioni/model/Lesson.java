package it.unito.sabatelli.ripetizioni.model;

import java.io.Serializable;

public class Lesson implements Serializable {
    int id;
    Teacher teacher;
    Day day;
    Slot slot;
    LessonState state;


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
}
