package it.unito.sabatelli.ripetizioni.ui.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import it.unito.sabatelli.ripetizioni.AbstractFragment;
import it.unito.sabatelli.ripetizioni.R;
import it.unito.sabatelli.ripetizioni.Utility;
import it.unito.sabatelli.ripetizioni.api.ApiFactory;
import it.unito.sabatelli.ripetizioni.api.RipetizioniApiManager;
import it.unito.sabatelli.ripetizioni.httpclient.HttpClientSingleton;
import it.unito.sabatelli.ripetizioni.httpclient.StringRequest;
import it.unito.sabatelli.ripetizioni.model.Lesson;
import it.unito.sabatelli.ripetizioni.ui.adapters.LessonListViewAdapter;
import it.unito.sabatelli.ripetizioni.ui.MainViewModel;

public class LessonsFragment extends AbstractFragment {
    View view;

    ListView listView;
    LessonListViewAdapter adapter;


    public LessonsFragment() {
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
        view = inflater.inflate(R.layout.fragment_lessons, container, false);
        System.out.println("HO CREATO IL LAYOUT LESSONS!!!!!!!!!!!!!!!!!");
        listView = view.findViewById(R.id.lessons_list_view);
        adapter = new LessonListViewAdapter(this.getContext(), vModel.reservations);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Lesson lesson = (Lesson) parent.getAdapter().getItem(position);

                if(lesson.getState().getCode() == 1) { // se è prenotata
                    ChangeLessonStateDialog dialog = new ChangeLessonStateDialog(lesson);

                    dialog.show(getActivity().getSupportFragmentManager(), "ChangeLessonStateDialog");
                }

            }
        });

        retrieveLessons();
        return view;
    }


    private void retrieveLessons() {


        apiManager.getReservations((listLessons) -> {
            vModel.reservations.clear();
            vModel.reservations.addAll(listLessons);
            adapter.reload(vModel.reservations);
        }, (error) -> {
            if(!Utility.isSessionExpired(error, view)) {
                Toast.makeText(getActivity(), "Received status "+error.networkResponse.statusCode, Toast.LENGTH_SHORT).show();

            }
        });
    }
}