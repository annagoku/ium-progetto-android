package it.unito.sabatelli.ripetizioni.ui.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


import it.unito.sabatelli.ripetizioni.R;
import it.unito.sabatelli.ripetizioni.model.Course;
import it.unito.sabatelli.ripetizioni.model.Lesson;
import it.unito.sabatelli.ripetizioni.model.Teacher;

public class TeachersListViewAdapter extends BaseAdapter {
    Context context;
    List<Teacher> arrayList = new ArrayList<>();

    public TeachersListViewAdapter(Context ctx, List<Teacher> list) {
        this.arrayList.addAll(list);
        this.context = ctx;
    }

    public void reload(List<Teacher> data) {
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
        return arrayList.get(position).getBadge().hashCode();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(context).
                    inflate(R.layout.teacher_item, parent, false);
        }

        Teacher t = (Teacher) getItem(position);

        ((TextView) convertView.findViewById(R.id.text_teacher_name)).setText(t.getFullName());

        return convertView;
    }
}
