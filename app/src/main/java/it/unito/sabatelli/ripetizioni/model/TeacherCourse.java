package it.unito.sabatelli.ripetizioni.model;

import java.util.ArrayList;
import java.util.List;

public class TeacherCourse {

        public String teacher;
        public  List<String> course = new ArrayList<String>();

        public TeacherCourse(String string, ArrayList<String> array) {
            this.teacher = string;
            this.course=array;
        }

    }

