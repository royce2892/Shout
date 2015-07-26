package com.hack.start.adapter;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hack.start.R;

import java.util.Locale;

/**
 * Created by Rraju on 7/25/2015.
 */
public class PhraseAdapter extends BaseAdapter {

    private String[] phraseList;
    private LayoutInflater mLayoutInflater;
    private TextToSpeech tts;

    public PhraseAdapter(String phrase, Context context) {
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        phraseList = phrase.split("\n");
        tts=new TextToSpeech(context, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    tts.setLanguage(Locale.UK);
                }
            }
        });
    }

    @Override
    public int getCount() {
        return phraseList.length;
    }

    @Override
    public Object getItem(int position) {
        return phraseList[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView == null)
            convertView = mLayoutInflater.inflate(R.layout.row_safe, parent, false);
        TextView mSafe = (TextView) convertView.findViewById(R.id.safe);
        mSafe.setText(phraseList[position].replaceAll("~", ":").replaceAll("|",""));
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tts.speak(phraseList[position].substring(phraseList[position].indexOf("~")+1),TextToSpeech.QUEUE_FLUSH,null);
            }
        });
        return convertView;
    }
}