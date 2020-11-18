package it.unito.sabatelli.ripetizioni.api;

import com.android.volley.Response;

import java.util.List;

import it.unito.sabatelli.ripetizioni.model.Course;
import it.unito.sabatelli.ripetizioni.model.Lesson;
import it.unito.sabatelli.ripetizioni.model.Teacher;
import it.unito.sabatelli.ripetizioni.model.User;

public interface  RipetizioniApiManager {
    public void login(String username, String password, SuccessListener<Void> listener, ErrorListener errorListener);
    public void logout(SuccessListener<Void> listener, ErrorListener errorListener);
    public void getUserInfo(SuccessListener<User> listener, ErrorListener errorListener);
    public void getReservations(SuccessListener<List<Lesson>> listener, ErrorListener errorListener);
    public void getCatalog(SuccessListener<List<Lesson>> listener, ErrorListener errorListener);
    public void getCourses(SuccessListener<List<Course>> listener, ErrorListener errorListener);
    public void getTeachers(SuccessListener<List<Teacher>> listener, ErrorListener errorListener);
    public void changeLessonState(Lesson lesson, int newStateCode, SuccessListener<Void> listener, ErrorListener errorListener);

}