package it.unito.sabatelli.ripetizioni.ui.fragments;

import android.app.Activity;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import it.unito.sabatelli.ripetizioni.AbstractFragment;
import it.unito.sabatelli.ripetizioni.R;
import it.unito.sabatelli.ripetizioni.model.Lesson;
import it.unito.sabatelli.ripetizioni.ui.adapters.LessonListViewAdapter;

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

        listView = view.findViewById(R.id.lessons_list_view);
        vModel.reservations.setValue(new ArrayList<>());
        adapter = new LessonListViewAdapter(this.getContext(), vModel.reservations.getValue());
        listView.setAdapter(adapter);

        vModel.reservations.observe(this.getViewLifecycleOwner(), (list) -> {
            adapter.reload(list);
        } );

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Lesson lesson = (Lesson) parent.getAdapter().getItem(position);

                if(lesson.getState().getCode() == 1) { // se è prenotata
                    ChangeLessonStateDialog dialog = new ChangeLessonStateDialog(lesson, position);

                    dialog.show(getActivity().getSupportFragmentManager(), "ChangeLessonStateDialog");
                }

            }
        });

        retrieveLessons();
        return view;
    }


    private void retrieveLessons() {
        Activity act = getActivity();

        apiManager.getReservations((listLessons) -> {
            vModel.reservations.postValue(listLessons);
        }, (error) -> {
            if(!act.isFinishing()) {
                Toast.makeText(act, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}