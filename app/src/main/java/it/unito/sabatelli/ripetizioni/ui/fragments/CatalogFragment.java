package it.unito.sabatelli.ripetizioni.ui.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.widget.SearchView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import it.unito.sabatelli.ripetizioni.AbstractFragment;
import it.unito.sabatelli.ripetizioni.R;
import it.unito.sabatelli.ripetizioni.model.Lesson;
import it.unito.sabatelli.ripetizioni.ui.adapters.CatalogListViewAdapter;

public class CatalogFragment extends AbstractFragment {

    View view;

    ListView listView;
    CatalogListViewAdapter adapter;
    SearchView courseFilter;


    public CatalogFragment() {
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
        view = inflater.inflate(R.layout.fragment_catalog, container, false);

        listView = view.findViewById(R.id.catalog_list_view);
        listView.setTextFilterEnabled(true);


        CatalogFragment fragment = this;

        //permetto di prenotare solo agli studenti
        if(vModel.user.getRole().equalsIgnoreCase("student")) {
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position,long id) {

                    Lesson lesson = (Lesson) parent.getAdapter().getItem(position);
                    NewReservationDialog dialog = new NewReservationDialog(lesson, fragment, position);
                    dialog.show(getActivity().getSupportFragmentManager(), "NewReservationDialog");

                }
            });

        }

        courseFilter=(SearchView) view.findViewById(R.id.searchCourseFilter);

        adapter = new CatalogListViewAdapter(this.getContext(), vModel.catalogItems.getValue());
        listView.setAdapter(adapter);

        vModel.catalogItems.observe(getViewLifecycleOwner(), (list) -> {
            adapter.reload(list);
        });


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

    protected void retrieveLessons() {
        Context ctx = this.getContext();

        Activity act = getActivity();

        vModel.loading.postValue(Boolean.TRUE);

        apiManager.getCatalog((list) -> {
            vModel.loading.postValue(Boolean.FALSE);

            vModel.catalogItems.postValue(list);
        }, (error) -> {
            vModel.loading.postValue(Boolean.FALSE);

            if(!act.isFinishing()) {
                Toast.makeText(act, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

}