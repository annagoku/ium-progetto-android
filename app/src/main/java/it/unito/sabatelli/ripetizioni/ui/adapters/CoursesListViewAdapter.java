package it.unito.sabatelli.ripetizioni.ui.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


import it.unito.sabatelli.ripetizioni.R;
import it.unito.sabatelli.ripetizioni.model.Course;
import it.unito.sabatelli.ripetizioni.model.Lesson;

public class CoursesListViewAdapter extends BaseAdapter {
    Context context;
    ArrayList<Course> arrayList = new ArrayList<>();

    public CoursesListViewAdapter(Context ctx, ArrayList<Course> list) {
        this.arrayList.addAll(list);
        this.context = ctx;
    }

    public void reload(List<Course> data) {
        this.arrayList.clear();
        this.arrayList.addAll(data);
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {



        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return arrayList.get(position).getCode().hashCode();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        if (convertView == null) {
            convertView = LayoutInflater.from(context).
                    inflate(R.layout.course_item, parent, false);
        }

        Course l = (Course) getItem(position);
        //System.out.println("Creating view for lesson "+l);
        ((TextView) convertView.findViewById(R.id.text_course_name)).setText(l.getName());

        return convertView;
    }
}
