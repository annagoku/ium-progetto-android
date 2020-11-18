package it.unito.sabatelli.ripetizioni.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import it.unito.sabatelli.ripetizioni.R;
import it.unito.sabatelli.ripetizioni.Utility;
import it.unito.sabatelli.ripetizioni.api.ApiFactory;
import it.unito.sabatelli.ripetizioni.api.RipetizioniApiManager;
import it.unito.sabatelli.ripetizioni.httpclient.HttpClientSingleton;
import it.unito.sabatelli.ripetizioni.httpclient.StringRequest;
import it.unito.sabatelli.ripetizioni.model.Course;
import it.unito.sabatelli.ripetizioni.ui.adapters.CoursesListViewAdapter;
import it.unito.sabatelli.ripetizioni.ui.MainViewModel;

public class CoursesFragment extends Fragment {

    View view;

    MainViewModel vModel = null;
    ListView listView;
    CoursesListViewAdapter adapter;


    public CoursesFragment() {
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
        view = inflater.inflate(R.layout.fragment_courses, container, false);
        listView = view.findViewById(R.id.courses_list_view);
        adapter = new CoursesListViewAdapter(this.getContext(), vModel.courses);
        listView.setAdapter(adapter);


        retrieveCourses();
        return view;
    }


    private void retrieveCourses() {
        RipetizioniApiManager apiManager = ApiFactory.getRipetizioniApiManager(getActivity());

        apiManager.getCourses((courseList) -> {
            vModel.courses.clear();
            vModel.courses.addAll(courseList);
            adapter.reload(vModel.courses);
        }, (error) -> {
            if(!Utility.isSessionExpired(error, view)) {
                Toast.makeText(getActivity(), "Received status "+error.networkResponse.statusCode, Toast.LENGTH_SHORT).show();

            }
        });

    }
}