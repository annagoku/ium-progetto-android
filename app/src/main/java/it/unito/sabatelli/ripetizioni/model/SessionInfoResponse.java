package it.unito.sabatelli.ripetizioni.model;

public class SessionInfoResponse extends GenericResponse{
    User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
