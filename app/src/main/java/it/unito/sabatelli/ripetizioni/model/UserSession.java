package it.unito.sabatelli.ripetizioni.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class UserSession implements Serializable {
    User user;
    List<Lesson> reservations = new ArrayList<>();
    String page = "lessons";

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Lesson> getReservations() {
        return reservations;
    }

    public void setReservations(List<Lesson> reservations) {
        this.reservations = reservations;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }
}
