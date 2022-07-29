package com.albassami.logistics.ui.Adapter;

import java.util.HashMap;
import java.util.List;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.albassami.logistics.R;
import com.albassami.logistics.dto.response.Branches;

import static com.androidquery.util.AQUtility.getContext;

public class CustomExpandableListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<String> expandableListTitle;
    private OnChildItemClicked childItemClicked;
    private HashMap<String, List<Branches>> expandableListDetail;

    public CustomExpandableListAdapter(Context context, List<String> expandableListTitle,
                                       HashMap<String, List<Branches>> expandableListDetail,OnChildItemClicked onChildItemClicked) {
        this.context = context;
        this.childItemClicked = onChildItemClicked;
        this.expandableListTitle = expandableListTitle;
        this.expandableListDetail = expandableListDetail;
    }

    @Override
    public Object getChild(int listPosition, int expandedListPosition) {
        SharedPreferences sharedPreferences=context.getSharedPreferences("lang", Context.MODE_PRIVATE);
        if(sharedPreferences.getString("language","").equals("ar")){
            return this.expandableListDetail.get(this.expandableListTitle.get(listPosition))
                    .get(expandedListPosition).getName();
        }
       else{
            return this.expandableListDetail.get(this.expandableListTitle.get(listPosition))
                    .get(expandedListPosition).getName_en();
        }
    }
    public Object getChildID(int listPosition, int expandedListPosition) {
        return this.expandableListDetail.get(this.expandableListTitle.get(listPosition))
                .get(expandedListPosition).getId();
    }

    @Override
    public long getChildId(int listPosition, int expandedListPosition) {
        return expandedListPosition;
    }

    @Override
    public View getChildView(int listPosition, final int expandedListPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        final String expandedListText = (String) getChild(listPosition, expandedListPosition);
        final int expandedListID = (Integer) getChildID(listPosition, expandedListPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.region_branch_item, null);

        }
        LinearLayout mainLayout = (LinearLayout) convertView
                .findViewById(R.id.layoutMain);
        TextView expandedListTextView = (TextView) convertView
                .findViewById(R.id.tvTitle);
        expandedListTextView.setText(expandedListText);
        View finalConvertView = convertView;
        mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finalConvertView.setBackgroundColor(0x1D000000);
               childItemClicked.onItemClicked(expandableListTitle.get(listPosition),expandedListText,expandedListID);

            }
        });
        return convertView;
    }

    @Override
    public int getChildrenCount(int listPosition) {
        return this.expandableListDetail.get(this.expandableListTitle.get(listPosition))
                .size();
    }

    @Override
    public Object getGroup(int listPosition) {
        return this.expandableListTitle.get(listPosition);
    }

    @Override
    public int getGroupCount() {
        return this.expandableListTitle.size();
    }

    @Override
    public long getGroupId(int listPosition) {
        return listPosition;
    }

    @Override
    public View getGroupView(int listPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String listTitle = (String) getGroup(listPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.region_item, null);

        }
        TextView listTitleTextView = (TextView) convertView.findViewById(R.id.tvTitle);
        listTitleTextView.setText(listTitle);
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int listPosition, int expandedListPosition) {
        return true;
    }

        public interface OnChildItemClicked {
        void onItemClicked(String regionName,String name,Integer id);
    }
}
