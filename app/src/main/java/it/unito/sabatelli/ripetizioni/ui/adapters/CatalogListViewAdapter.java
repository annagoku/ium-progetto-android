package it.unito.sabatelli.ripetizioni.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import it.unito.sabatelli.ripetizioni.R;
import it.unito.sabatelli.ripetizioni.model.Lesson;

public class CatalogListViewAdapter extends BaseAdapter implements Filterable {
    Context context;
    List<Lesson> originalList = new ArrayList<>();
    private ValueFilter valueFilter;
    private List<Lesson> lessonFilterList=new ArrayList<>();





    public CatalogListViewAdapter(Context ctx, List<Lesson> list) {
        this.originalList.addAll(list);
        this.lessonFilterList.addAll(list);

        this.context = ctx;
    }

    public void reload(List<Lesson> data) {
        this.originalList.clear();
        this.originalList.addAll(data);
        this.lessonFilterList.clear();
        this.lessonFilterList.addAll(data);
        this.notifyDataSetChanged(); // notifica i cambiamenti agli osservatori
    }

    @Override
    public int getCount() {
        return lessonFilterList.size();
    }

    @Override
    public Object getItem(int position) {
        return lessonFilterList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return lessonFilterList.get(position).getId();
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

    @Override
    public Filter getFilter() {
        if(valueFilter==null) {

            valueFilter=new ValueFilter();
        }
        return valueFilter;
    }
    private class ValueFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results=new FilterResults();
                if (constraint == null || constraint.length() == 0) {
                    //no constraint given, just return all the data. (no search)
                    results.count = originalList.size();
                    results.values = originalList;
                } else {//do the search
                    List<Lesson> resultsData = new ArrayList<>();
                    String searchStr = constraint.toString().toUpperCase();
                    for (Lesson o : originalList)
                        if (o.getCourse().getName().toUpperCase().startsWith(searchStr)) resultsData.add(o);
                    results.count = resultsData.size();
                    results.values = resultsData;
                }
                return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            lessonFilterList=(ArrayList<Lesson>) results.values;
            notifyDataSetChanged();

        }
    }
}
