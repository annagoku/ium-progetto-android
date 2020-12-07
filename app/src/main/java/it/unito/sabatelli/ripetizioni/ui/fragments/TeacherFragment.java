package it.unito.sabatelli.ripetizioni.ui.fragments;

import android.app.Activity;
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
import it.unito.sabatelli.ripetizioni.model.Teacher;
import it.unito.sabatelli.ripetizioni.ui.adapters.TeachersListViewAdapter;

public class TeacherFragment extends AbstractFragment {
    View view;

    ListView listView;
    TeachersListViewAdapter adapter;


    public TeacherFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_teacher, container, false);
        listView = view.findViewById(R.id.teachers_list_view);
        adapter = new TeachersListViewAdapter(this.getContext(), vModel.teachers.getValue());
        listView.setAdapter(adapter);
        vModel.teachers.observe(this.getViewLifecycleOwner(), new Observer<List<Teacher>>() {
            @Override
            public void onChanged(List<Teacher> teachers) {
                adapter.reload(teachers);
            }
        });


        retrieveTeacher();
        return view;
    }


    private void retrieveTeacher() {
        Activity act = getActivity();
        apiManager.getTeachers((teacherList) -> {
            vModel.teachers.postValue(teacherList);
        }, (error) -> {
            if(!act.isFinishing()) {
                Toast.makeText(act, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}

