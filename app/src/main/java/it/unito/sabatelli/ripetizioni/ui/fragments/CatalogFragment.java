package it.unito.sabatelli.ripetizioni.ui.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.ArrayList;

import it.unito.sabatelli.ripetizioni.R;
import it.unito.sabatelli.ripetizioni.Utility;
import it.unito.sabatelli.ripetizioni.httpclient.HttpClientSingleton;
import it.unito.sabatelli.ripetizioni.httpclient.StringRequest;
import it.unito.sabatelli.ripetizioni.model.Lesson;
import it.unito.sabatelli.ripetizioni.ui.adapters.CatalogListViewAdapter;
import it.unito.sabatelli.ripetizioni.ui.MainViewModel;

public class CatalogFragment extends Fragment {

    View view;

    MainViewModel vModel = null;
    ListView listView;
    CatalogListViewAdapter adapter;
    SearchView courseFilter;
    ArrayAdapter <String> arrayAdapter;


    public CatalogFragment() {
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
        view = inflater.inflate(R.layout.fragment_catalog, container, false);
        System.out.println("HO CREATO IL LAYOUT LESSONS!!!!!!!!!!!!!!!!!");
        listView = view.findViewById(R.id.catalog_list_view);
        listView.setTextFilterEnabled(true);





        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
                Utility.hideKeyboard(getContext());

                Lesson lesson = (Lesson) parent.getAdapter().getItem(position);


                    NewReservationDialog dialog = new NewReservationDialog(lesson);
                    dialog.show(getActivity().getSupportFragmentManager(), "NewReservationDialog");

            }
        });

        courseFilter=(SearchView) view.findViewById(R.id.searchCourseFilter);

        courseFilter.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    Utility.hideKeyboard(getContext());
                }
            }
        });




        adapter = new CatalogListViewAdapter(this.getContext(), vModel.reservations);
        listView.setAdapter(adapter);

        courseFilter.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.getFilter().filter(query);
                adapter.notifyDataSetChanged();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                adapter.notifyDataSetChanged();
                return false;
            }
        });





        retrieveLessons();
        return view;
    }





    private void retrieveLessons() {
        Context ctx = this.getContext();

        StringRequest request = new StringRequest(
                Request.Method.GET,
                getString(R.string.main_server_url)+"/private/catalog", null,
                new Response.Listener<String> () {

                    @Override
                    public void onResponse(String response) {


                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Gson gson = new Gson();
                                JsonArray array = new JsonParser().parse(response).getAsJsonArray();

                                vModel.catalogItems.clear();
                                for(JsonElement el : array) {
                                    vModel.catalogItems.add(gson.fromJson(el, Lesson.class));

                                }
                                adapter.reload(vModel.catalogItems);
                            }
                        });


                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if(!Utility.isSessionExpired(error, view)) {
                                    Toast.makeText(getActivity(), "Received status "+error.networkResponse.statusCode, Toast.LENGTH_SHORT).show();

                                }
                            }
                        });

                    }
                });

        HttpClientSingleton.getInstance().addToRequestQueue(request);
    }


}