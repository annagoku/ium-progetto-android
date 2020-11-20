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
import it.unito.sabatelli.ripetizioni.model.Course;
import it.unito.sabatelli.ripetizioni.model.Teacher;
import it.unito.sabatelli.ripetizioni.model.TeacherCourse;
import it.unito.sabatelli.ripetizioni.ui.MainViewModel;
import it.unito.sabatelli.ripetizioni.ui.adapters.TeacherCourseExpandableListAdapter;
import it.unito.sabatelli.ripetizioni.ui.adapters.TeachersListViewAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TeacherCourseFragment extends Fragment {
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
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

            for (Teacher t: vModel.teachers){

                //System.out.println("Provo a vedere se c'è qualcosa -->" + listDataHeaderTeacher.get(0));
                listDataChildCourse.put(t.getFullName(),t.getCourseNameLinked());
                vModel.teacherCourse.add(new TeacherCourse(t.getFullName(), t.getCourseNameLinked()));
            }

            List<String> expandableListTitle = new ArrayList<String>(listDataChildCourse.keySet());
            listAdapter = new TeacherCourseExpandableListAdapter(this.getContext(),  expandableListTitle, listDataChildCourse);

            // setting list adapter
            expListView.setAdapter(listAdapter);
            expListView.setGroupIndicator(null);
            /*for(int i=0; i<teacherList.size(); i++) {
                expListView.expandGroup(i);
            }*/
            //expListView.expandGroup(0);


            expListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

                @Override
                public boolean onGroupClick(ExpandableListView parent, View v,
                                            int groupPosition, long id) {
                     Toast.makeText(getActivity().getApplicationContext(),
                    "Group Clicked " + expandableListTitle.get(groupPosition),
                     Toast.LENGTH_SHORT).show();
                    parent.expandGroup(groupPosition);
                    return true;
                }
            });





        }, (error) -> {
            if(!Utility.isSessionExpired(error, view)) {
                Toast.makeText(getActivity(), "Received status "+error.networkResponse.statusCode, Toast.LENGTH_SHORT).show();

            }
        });

    }




    //
}
