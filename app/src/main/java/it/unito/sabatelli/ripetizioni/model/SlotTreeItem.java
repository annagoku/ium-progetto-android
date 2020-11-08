package it.unito.sabatelli.ripetizioni.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SlotTreeItem implements Serializable {
    Slot slot;

    List<Course> courses = new ArrayList<>();
    List<Lesson> lessons = new ArrayList<>();

    public Slot getSlot() {
        return slot;
    }

    public void setSlot(Slot slot) {
        this.slot = slot;
    }
}
