package it.unito.sabatelli.ripetizioni.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class LessonTreeItem implements Serializable {
    Day day;

    List<SlotTreeItem> slots= new ArrayList<>();

    public Day getDay() {
        return day;
    }

    public void setDay(Day day) {
        this.day = day;
    }

    public List<SlotTreeItem> getSlots() {
        return slots;
    }

    public void setSlots(List<SlotTreeItem> slots) {
        this.slots = slots;
    }
}
