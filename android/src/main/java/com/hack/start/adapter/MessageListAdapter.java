package com.hack.start.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hack.start.R;
import com.hack.start.preference.PreferenceManager;
import com.parse.ParseObject;

import java.util.List;

/**
 * Created by Rraju on 7/24/2015.
 */
public class MessageListAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private List<ParseObject> messageList;
    private String selfName;

    public MessageListAdapter(List<ParseObject> messageList, Context context) {
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.messageList = messageList;
        PreferenceManager manager = new PreferenceManager(context);
        selfName = manager.get("name");
    }

    @Override
    public int getCount() {
        return messageList.size();
    }

    @Override
    public Object getItem(int position) {
        return messageList.get(position);
    }

    @Override
    public int getItemViewType(int position) {
        if (messageList.get(position).getString("from").contentEquals(selfName))
            return 1;
        else
            return 0;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ParseObject message = messageList.get(position);
        int direction = getItemViewType(position);
        if (convertView == null) {
            int res = 0;
            if (direction == 1)
                res = R.layout.row_sent;
            else
                res = R.layout.row_received;
            convertView = mInflater.inflate(res, parent, false);
        }

        TextView sender = (TextView) convertView.findViewById(R.id.txtView_sender_name);
        TextView time = (TextView) convertView.findViewById(R.id.txtView_message_timestamp);
        TextView content = (TextView) convertView.findViewById(R.id.txtView_message);
        if (direction == 0) {

            sender.setText(message.getString("from"));
            sender.setVisibility(View.VISIBLE);
        } else
            sender.setVisibility(View.GONE);
        content.setText(message.getString("content"));
        time.setText(message.getCreatedAt().toString().substring(10, 16));
        return convertView;
    }

    public void add(ParseObject message) {
        messageList.add(message);
        notifyDataSetChanged();
    }

}
