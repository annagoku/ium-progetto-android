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
import it.unito.sabatelli.ripetizioni.httpclient.HttpClientSingleton;
import it.unito.sabatelli.ripetizioni.httpclient.StringRequest;
import it.unito.sabatelli.ripetizioni.model.Course;
import it.unito.sabatelli.ripetizioni.ui.adapters.CoursesListViewAdapter;
import it.unito.sabatelli.ripetizioni.ui.MainViewModel;

public class CourseFragment extends Fragment {

    View view;

    MainViewModel vModel = null;
    ListView listView;
    CoursesListViewAdapter adapter;


    public CourseFragment() {
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
        Context ctx = this.getContext();

        StringRequest request = new StringRequest(
                Request.Method.GET,
                getString(R.string.main_server_url)+"/public/courses?filter=home", null,
                new Response.Listener<String> () {

                    @Override
                    public void onResponse(String response) {


                        getActivity().runOnUiThread(() -> {
                                Gson gson = new Gson();
                                JsonArray array = new JsonParser().parse(response).getAsJsonArray();

                                vModel.courses.clear();
                                for(JsonElement el : array) {
                                    vModel.courses.add(gson.fromJson(el, Course.class));

                                }
                                adapter.reload(vModel.courses);

                        });


                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        getActivity().runOnUiThread(() -> {
                            if(!Utility.isSessionExpired(error, view)) {
                                Toast.makeText(getActivity(), "Received status "+error.networkResponse.statusCode, Toast.LENGTH_SHORT).show();

                            }

                        });

                    }
                });

        HttpClientSingleton.getInstance().addToRequestQueue(request);
    }
}