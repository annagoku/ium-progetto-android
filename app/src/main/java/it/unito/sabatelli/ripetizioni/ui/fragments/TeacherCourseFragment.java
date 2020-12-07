package it.unito.sabatelli.ripetizioni.ui.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Toast;

import it.unito.sabatelli.ripetizioni.AbstractFragment;
import it.unito.sabatelli.ripetizioni.R;
import it.unito.sabatelli.ripetizioni.model.Teacher;
import it.unito.sabatelli.ripetizioni.ui.adapters.TeacherCourseExpandableListAdapter;

import androidx.lifecycle.Observer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TeacherCourseFragment extends AbstractFragment {
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    ArrayList<String> listDataHeaderTeacher=null;
    HashMap<String, List<String>> listDataChildCourse=null;
    View view;



    public TeacherCourseFragment(){
        // Required empty public constructor

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        view = inflater.inflate(R.layout.fragment_teachercourse, container, false);

        // get the listview
        expListView = (ExpandableListView) view.findViewById(R.id.lvExp);
        Context context = this.getContext();

        vModel.teachers.observe(this.getViewLifecycleOwner(), new Observer<List<Teacher>>() {
            @Override
            public void onChanged(List<Teacher> teachers) {
                listDataHeaderTeacher=new ArrayList<>();
                listDataChildCourse=new HashMap<>();

                for (Teacher t : teachers) {

                    listDataChildCourse.put(t.getFullName(), t.getCourseTeacherLinked());
                }
                for (String key: listDataChildCourse.keySet()){
                    if (listDataChildCourse.get(key).size()==0){
                        listDataChildCourse.remove(key);
                    }
                }

                listDataHeaderTeacher = new ArrayList<String>(listDataChildCourse.keySet());

                listAdapter = new TeacherCourseExpandableListAdapter(context,  listDataHeaderTeacher, listDataChildCourse);


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

        // preparing list data
        retrieveTeacherCourse();

        return view;
    }

    private void retrieveTeacherCourse() {
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
