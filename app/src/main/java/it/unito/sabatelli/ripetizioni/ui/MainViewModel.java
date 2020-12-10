package it.unito.sabatelli.ripetizioni.ui;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import it.unito.sabatelli.ripetizioni.model.Course;
import it.unito.sabatelli.ripetizioni.model.Lesson;
import it.unito.sabatelli.ripetizioni.model.Teacher;
import it.unito.sabatelli.ripetizioni.model.User;

public class MainViewModel extends ViewModel {

    public User user;
    public int count;
    public  LessonsMutableLiveData reservations = new LessonsMutableLiveData();
    public  LessonsMutableLiveData catalogItems = new LessonsMutableLiveData();
    public MutableLiveData<List<Teacher>> teachers = new MutableLiveData<>();
    public MutableLiveData<List<Course>> courses = new MutableLiveData<>();
    public MutableLiveData<Boolean> loading = new MutableLiveData<>();

    public void init() {
        reservations.setValue(new ArrayList<>());
        catalogItems.setValue(new ArrayList<>());
        courses.setValue(new ArrayList<>());
        teachers.setValue(new ArrayList<>());
        loading.setValue(Boolean.FALSE);
        if(user==null){
            count=0;
        }
    }


}
