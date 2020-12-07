package it.unito.sabatelli.ripetizioni.ui.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import androidx.lifecycle.Observer;

import java.util.List;

import it.unito.sabatelli.ripetizioni.AbstractFragment;
import it.unito.sabatelli.ripetizioni.R;
import it.unito.sabatelli.ripetizioni.model.Course;
import it.unito.sabatelli.ripetizioni.ui.adapters.CoursesListViewAdapter;

public class CoursesFragment extends AbstractFragment {

    View view;

    ListView listView;
    CoursesListViewAdapter adapter;


    public CoursesFragment() {
        // Required empty public constructor
    }


// in tutte le classi fragment manca l'override del setonclicklistener per la navigazione tra fragment

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_courses, container, false);
        listView = view.findViewById(R.id.courses_list_view);
        Context ctx = this.getContext();
        adapter = new CoursesListViewAdapter(ctx, vModel.courses.getValue());
        listView.setAdapter(adapter);


        vModel.courses.observe(this.getViewLifecycleOwner(), new Observer<List<Course>>() {
            @Override
            public void onChanged(List<Course> courses) {
                adapter.reload(courses);
            }
        });


        retrieveCourses();
        return view;
    }


    private void retrieveCourses() {
        Activity act = getActivity();
        vModel.loading.postValue(Boolean.TRUE);

        apiManager.getCourses((courseList) -> {
            vModel.loading.postValue(Boolean.FALSE);
            vModel.courses.postValue(courseList);
        }, (error) -> {
            vModel.loading.postValue(Boolean.FALSE);

            if(!act.isFinishing()) {
                Toast.makeText(act, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}