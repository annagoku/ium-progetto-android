package it.unito.sabatelli.ripetizioni.ui.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import it.unito.sabatelli.ripetizioni.R;

import java.util.HashMap;
import java.util.List;

public class TeacherCourseExpandableListAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<String> listDataHeaderTeacher; // header titles
    // child data in format of header title, child title
    private HashMap<String, List<String>> listDataChildCourse;

    //costruttore
    public TeacherCourseExpandableListAdapter(Context context,  List<String> listDataHeader, HashMap<String, List<String>> listChildData) {
        this.context = context;
        this.listDataHeaderTeacher = listDataHeader;
        this.listDataChildCourse = listChildData;
    }

    @Override
    public int getGroupCount() {
        return this.listDataHeaderTeacher.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this.listDataChildCourse.get(this.listDataHeaderTeacher.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.listDataHeaderTeacher.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return this.listDataChildCourse.get(this.listDataHeaderTeacher.get(groupPosition))
                .get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        //String teacher = (String) getGroup(groupPosition);
        ViewHolder holder;

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = layoutInflater.inflate(R.layout.list_group_header_teacher, parent, false);
            holder = new ViewHolder();
            holder.text = (TextView) convertView.findViewById(R.id.lblListHeaderTeacher);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        String text = getGroup(groupPosition).toString();

        holder.text.setText(getGroup(groupPosition).toString());

      // ExpandableListView mExpandableListView = (ExpandableListView) parent;
        //mExpandableListView.expandGroup(groupPosition);


        System.out.println("Group "+groupPosition+" -> Text: "+text);


        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);


            convertView = layoutInflater.inflate(R.layout.list_item_course, parent, false);

            holder = new ViewHolder();
            holder.text = (TextView) convertView.findViewById(R.id.lblListItemCourse);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        String text = getChild(groupPosition, childPosition).toString();

         holder.text.setText(text);
        //( (TextView) convertView.findViewById(R.id.lblListItemCourse)).setText(text);

        System.out.println("Child group "+groupPosition+" position "+childPosition+" -> Text: "+text);
        return convertView;
    }



    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
    private static class ViewHolder {
        TextView text;
    }

}
