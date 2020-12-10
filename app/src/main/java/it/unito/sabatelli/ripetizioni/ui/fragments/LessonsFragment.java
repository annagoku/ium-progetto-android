package it.unito.sabatelli.ripetizioni.ui.fragments;

import android.app.Activity;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import it.unito.sabatelli.ripetizioni.AbstractFragment;
import it.unito.sabatelli.ripetizioni.R;
import it.unito.sabatelli.ripetizioni.model.Lesson;
import it.unito.sabatelli.ripetizioni.ui.MainViewModel;
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
        String welcome ="Benvenuto " + vModel.user.getUsername();
        if(vModel.count==0) {
            Toast.makeText(getActivity(), welcome, Toast.LENGTH_SHORT).show();
            vModel.count = 1;
        }


        return view;
    }


    private void retrieveLessons() {
        Activity act = getActivity();

        vModel.loading.postValue(Boolean.TRUE);
        apiManager.getReservations((listLessons) -> {
            if(listLessons.size()>0) {
                TextView tv = (TextView) view.findViewById(R.id.default_message);
                tv.setVisibility(View.GONE);
                vModel.reservations.postValue(listLessons);
                vModel.loading.postValue(Boolean.FALSE);
            } else if (listLessons.size()==0) {
                TextView tv = (TextView) view.findViewById(R.id.default_message);
                tv.setVisibility(View.VISIBLE);
                vModel.loading.postValue(Boolean.FALSE);
            }
        }, (error) -> {
            vModel.loading.postValue(Boolean.FALSE);
            if(!act.isFinishing()) {
                Toast.makeText(act, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}