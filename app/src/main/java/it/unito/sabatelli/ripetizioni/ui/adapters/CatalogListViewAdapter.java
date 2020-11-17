package it.unito.sabatelli.ripetizioni.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import it.unito.sabatelli.ripetizioni.R;
import it.unito.sabatelli.ripetizioni.model.Lesson;

public class CatalogListViewAdapter extends BaseAdapter {
    Context context;
    ArrayList<Lesson> arrayList = new ArrayList<>();

    public CatalogListViewAdapter(Context ctx, ArrayList<Lesson> list) {
        this.arrayList.addAll(list);
        this.context = ctx;
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


        if (convertView == null) {
            convertView = LayoutInflater.from(context).
                    inflate(R.layout.catalog_item, parent, false);
        }

        Lesson l = (Lesson) getItem(position);
        //System.out.println("Creating view for lesson "+l);
        ((TextView) convertView.findViewById(R.id.text_lesson_item_daycontent)).setText(l.getDay().getDayname()+" "+l.getSlot().getStartHour());
        ((TextView) convertView.findViewById(R.id.text_lesson_item_course)).setText(l.getCourse().getName());
        ((TextView) convertView.findViewById(R.id.text_lesson_item_teacher)).setText(l.getTeacher().getFullName());

        return convertView;
    }
}
