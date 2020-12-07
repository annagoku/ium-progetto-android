package it.unito.sabatelli.ripetizioni.api;

import com.android.volley.Response;

import java.util.List;

import it.unito.sabatelli.ripetizioni.model.Course;
import it.unito.sabatelli.ripetizioni.model.Lesson;
import it.unito.sabatelli.ripetizioni.model.SessionInfoResponse;
import it.unito.sabatelli.ripetizioni.model.Teacher;
import it.unito.sabatelli.ripetizioni.model.User;

//Interfaccia con metodi di gestione della chiamata a server
public interface  RipetizioniApiManager {
    public void login(String username, String password, SuccessListener<Void> listener, ErrorListener errorListener);
    public void logout(SuccessListener<Void> listener, ErrorListener errorListener);
    public void getUserInfo(SuccessListener<SessionInfoResponse> listener, ErrorListener errorListener);
    public void getReservations(SuccessListener<List<Lesson>> listener, ErrorListener errorListener);
    public void getCatalog(SuccessListener<List<Lesson>> listener, ErrorListener errorListener);
    public void getCourses(SuccessListener<List<Course>> listener, ErrorListener errorListener);
    public void getTeachers(SuccessListener<List<Teacher>> listener, ErrorListener errorListener);
    public void changeLessonState(Lesson lesson, String action, int newStateCode, SuccessListener<Void> listener, ErrorListener errorListener);
    public void saveNewReservation(Lesson lesson, SuccessListener<User> listener, ErrorListener errorListener);
}
