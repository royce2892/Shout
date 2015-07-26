package com.hack.start.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hack.start.R;

/**
 * Created by Rraju on 7/25/2015.
 */
public class SafetyAdapter extends BaseAdapter {

    private String[] safetyList;
    private LayoutInflater mLayoutInflater;
    private Context mContext;

    public SafetyAdapter(String safety,Context context) {
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        safetyList = safety.split("\n");
        mContext = context;
    }

    @Override
    public int getCount() {
        return safetyList.length;
    }

    @Override
    public Object getItem(int position) {
        return safetyList[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null)
            convertView = mLayoutInflater.inflate(R.layout.row_safe,parent,false);

        TextView mSafe = (TextView) convertView.findViewById(R.id.safe);
        mSafe.setText(safetyList[position]);
        return convertView;
    }
}
