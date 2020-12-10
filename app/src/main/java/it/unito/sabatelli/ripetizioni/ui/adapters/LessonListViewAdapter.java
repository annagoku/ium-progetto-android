package it.unito.sabatelli.ripetizioni.ui.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import it.unito.sabatelli.ripetizioni.R;
import it.unito.sabatelli.ripetizioni.model.Lesson;

public class LessonListViewAdapter extends BaseAdapter {
    Context context;
    boolean isAdmin =false;
    ArrayList<Lesson> arrayList = new ArrayList<>();

    private static class ViewHolder {
        TextView daycontent;
        TextView course;
        TextView teacher;
        TextView state;
        TextView username;
    }


    public LessonListViewAdapter(Context ctx, List<Lesson> list, boolean isAdmin) {
        this.arrayList.addAll(list);
        this.context = ctx;
        this.isAdmin = isAdmin;
    }

    public void reload(List<Lesson> data) {
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
        return arrayList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder ;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).
                    inflate(R.layout.lesson_item, parent, false);
            viewHolder.daycontent = (TextView) convertView.findViewById(R.id.text_lesson_item_daycontent);
            viewHolder.course = (TextView) convertView.findViewById(R.id.text_lesson_item_course);
            viewHolder.teacher = (TextView) convertView.findViewById(R.id.text_lesson_item_teacher);
            viewHolder.state = (TextView) convertView.findViewById(R.id.text_lesson_item_state);
            viewHolder.username = (TextView) convertView.findViewById(R.id.text_lesson_item_username);
            // Cache the viewHolder object inside the fresh view
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Lesson l = (Lesson) getItem(position);

        viewHolder.daycontent.setText(l.getDay().getDayname()+" "+l.getSlot().getStartHour());
        viewHolder.course.setText(l.getCourse().getName());
        viewHolder.teacher.setText(l.getTeacher().getFullName());
        viewHolder.state.setText(l.getState().getName());
        if(isAdmin) {
            viewHolder.username.setText(l.getUser() != null ? l.getUser().getUsername() : "Utente mancante");
            viewHolder.username.setVisibility(View.VISIBLE);
        }

        return convertView;
    }
}
