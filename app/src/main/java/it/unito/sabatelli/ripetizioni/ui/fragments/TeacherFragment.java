package it.unito.sabatelli.ripetizioni.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import it.unito.sabatelli.ripetizioni.R;
import it.unito.sabatelli.ripetizioni.Utility;
import it.unito.sabatelli.ripetizioni.api.ApiFactory;
import it.unito.sabatelli.ripetizioni.api.RipetizioniApiManager;
import it.unito.sabatelli.ripetizioni.model.Teacher;
import it.unito.sabatelli.ripetizioni.ui.MainViewModel;
import it.unito.sabatelli.ripetizioni.ui.adapters.CoursesListViewAdapter;
import it.unito.sabatelli.ripetizioni.ui.adapters.TeachersListViewAdapter;

public class TeacherFragment extends Fragment {
    View view;

    MainViewModel vModel = null;
    ListView listView;
    TeachersListViewAdapter adapter;


    public TeacherFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vModel = ViewModelProviders.of(this).get(MainViewModel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_teacher, container, false);
        listView = view.findViewById(R.id.teachers_list_view);
        adapter = new TeachersListViewAdapter(this.getContext(), vModel.teachers);
        listView.setAdapter(adapter);


        retrieveTeacher();
        return view;
    }


    private void retrieveTeacher() {
        RipetizioniApiManager apiManager = ApiFactory.getRipetizioniApiManager(getActivity());

        apiManager.getTeachers((teacherList) -> {
            vModel.teachers.clear();
            vModel.teachers.addAll(teacherList);
            adapter.reload(vModel.teachers);
        }, (error) -> {
            if(!Utility.isSessionExpired(error, view)) {
                Toast.makeText(getActivity(), "Received status "+error.networkResponse.statusCode, Toast.LENGTH_SHORT).show();

            }
        });

    }
}

