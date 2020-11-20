package it.unito.sabatelli.ripetizioni.ui;

import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import it.unito.sabatelli.ripetizioni.model.Course;
import it.unito.sabatelli.ripetizioni.model.Lesson;
import it.unito.sabatelli.ripetizioni.model.Teacher;
import it.unito.sabatelli.ripetizioni.model.TeacherCourse;
import it.unito.sabatelli.ripetizioni.model.User;

public class MainViewModel extends ViewModel {
    public User user;
    public ArrayList<Lesson> reservations = new ArrayList<>();
    public ArrayList<Lesson> catalogItems = new ArrayList<>();
    public ArrayList<Teacher> teachers = new ArrayList<>();
    public ArrayList<Course> courses = new ArrayList<>();
    public ArrayList<TeacherCourse> teacherCourse =new ArrayList<>();


}
