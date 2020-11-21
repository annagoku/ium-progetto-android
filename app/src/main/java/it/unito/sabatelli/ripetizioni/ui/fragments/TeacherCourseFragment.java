package it.unito.sabatelli.ripetizioni.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Toast;

import it.unito.sabatelli.ripetizioni.R;
import it.unito.sabatelli.ripetizioni.Utility;
import it.unito.sabatelli.ripetizioni.api.ApiFactory;
import it.unito.sabatelli.ripetizioni.api.RipetizioniApiManager;
import it.unito.sabatelli.ripetizioni.model.Teacher;
import it.unito.sabatelli.ripetizioni.ui.MainViewModel;
import it.unito.sabatelli.ripetizioni.ui.adapters.TeacherCourseExpandableListAdapter;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TeacherCourseFragment extends Fragment {
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    ArrayList<String> listDataHeaderTeacher=new ArrayList<>();
    HashMap<String, List<String>> listDataChildCourse=new HashMap<>();
    View view;

    MainViewModel vModel = null;


    public TeacherCourseFragment(){
        // Required empty public constructor

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vModel = ViewModelProviders.of(this).get(MainViewModel.class);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        view = inflater.inflate(R.layout.fragment_teachercourse, container, false);

        // get the listview
        expListView = (ExpandableListView) view.findViewById(R.id.lvExp);

        // preparing list data
         retrieveTeacherCourse();


        return view;
    }

    private void retrieveTeacherCourse() {
        RipetizioniApiManager apiManager = ApiFactory.getRipetizioniApiManager(getActivity());

        apiManager.getTeachers((teacherList) -> {
                    vModel.teachers.clear();
                    vModel.teachers.addAll(teacherList);

                    for (Teacher t : vModel.teachers) {

                        listDataChildCourse.put(t.getFullName(), t.getCourseTeacherLinked());
                    }
                    for (String key: listDataChildCourse.keySet()){
                        if (listDataChildCourse.get(key).size()==0){
                            listDataChildCourse.remove(key);
                        }
                    }

            listDataHeaderTeacher = new ArrayList<String>(listDataChildCourse.keySet());


            listAdapter = new TeacherCourseExpandableListAdapter(this.getContext(),  listDataHeaderTeacher, listDataChildCourse);


                    // setting list adapter
            expListView.setAdapter(listAdapter);



            expListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener()  {

                @Override
                public boolean onGroupClick(ExpandableListView parent, View v,
                                            int groupPosition, long id) {
                     Toast.makeText(getActivity().getApplicationContext(),
                    "Group Clicked " + listDataHeaderTeacher.get(groupPosition),
                     Toast.LENGTH_SHORT).show();

                    if (parent.isGroupExpanded(groupPosition)) {
                        parent.collapseGroup(groupPosition);
                    } else {
                        parent.expandGroup(groupPosition);
                    }

                    return true;
                }
            });

        }, (error) -> {
            if(!Utility.isSessionExpired(error, view)) {
                Toast.makeText(getActivity(), "Received status "+error.networkResponse.statusCode, Toast.LENGTH_SHORT).show();

            }
        });

        expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                Toast.makeText(getActivity().getApplicationContext(),
                        listDataHeaderTeacher.get(groupPosition) + " Expanded",
                        Toast.LENGTH_SHORT).show();
            }
        });

        // Listview Group collasped listener
        expListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
                Toast.makeText(getActivity().getApplicationContext(),
                        listDataHeaderTeacher.get(groupPosition) + " Collapsed",
                        Toast.LENGTH_SHORT).show();

            }
        });

        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                Toast.makeText(getActivity().getApplicationContext(),
                        "Group Clicked " + listDataChildCourse.get(groupPosition),
                        Toast.LENGTH_SHORT).show();

                return true;
            }
        });


    }




    //
}
