package com.hack.start.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.hack.start.R;
import com.hack.start.adapter.MessageListAdapter;
import com.hack.start.preference.PreferenceManager;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParsePush;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SendCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Rraju on 7/24/2015.
 */
public class MessageActivity extends BaseActivity {

    ListView mListView;
    MessageListAdapter messageListAdapter;
    EditText messageBox;
    ImageView messageSubmit;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_message;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setTitle("Shout");

        messageBox = (EditText) findViewById(R.id.message_box);
        messageSubmit = (ImageView) findViewById(R.id.message_send);
        mListView = (ListView) findViewById(R.id.message_list);
        setUpList();

        messageSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessageAndPush();
            }
        });
    }

    private void sendMessageAndPush() {

        PreferenceManager manager = new PreferenceManager(this);
        if (manager.get("name").contentEquals("default"))
            askForNameDialog(manager);
        else
            sendMessage(manager);
    }

    private void askForNameDialog(final PreferenceManager manager) {

        LayoutInflater inflater = LayoutInflater.from(this);
        View promptsView = inflater.inflate(R.layout.prompts, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);

        alertDialogBuilder.setView(promptsView);

        final EditText userInput = (EditText) promptsView
                .findViewById(R.id.name_input);

        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                manager.put("name", userInput.getText().toString());
                                sendMessage(manager);
                            }
                        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void sendMessage(PreferenceManager manager) {

        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setTitle("Sending");
        dialog.setMessage("Please wait");
        dialog.show();

        final ParseObject message = ParseObject.create("Message");
        message.put("content", messageBox.getText().toString());
        message.put("from", manager.get("name"));
        message.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                dialog.cancel();
                if (e == null) {
                    messageListAdapter.add(message);
                    messageListAdapter.notifyDataSetChanged();
                }
            }
        });
        JSONObject push = new JSONObject();
        try {
            push.put("content", messageBox.getText().toString());
            push.put("from", manager.get("name"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ParsePush parsePush = new ParsePush();
        ParseQuery pushQuery = ParseInstallation.getQuery();
        pushQuery.whereEqualTo("channels", "shout");
        parsePush.setData(push);
        parsePush.sendInBackground(new SendCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null)
                    Log.i("push", "done");
                else
                    Log.i("push", "not done");
            }
        });

        messageBox.setText("");
        hideKeyBoard(this);
    }

    public static void hideKeyBoard(Activity activity) {
        if (activity == null)
            return;

        InputMethodManager manager = (InputMethodManager) activity
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        View currentFocus = activity.getCurrentFocus();
        if (manager == null || currentFocus == null)
            return;

        manager.hideSoftInputFromWindow(currentFocus.getWindowToken(), 0);
    }


    private void setUpList() {
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setTitle("Loading");
        dialog.setMessage("Please wait while we load your shouts and responses");
        dialog.show();
        dialog.setCancelable(false);
        ParseQuery query = ParseQuery.getQuery("Message");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List list, ParseException e) {
                dialog.cancel();
                if (e == null) {
                    messageListAdapter = new MessageListAdapter(list, MessageActivity.this);
                    mListView.setAdapter(messageListAdapter);
                    mListView.setStackFromBottom(true);
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, MainActivity.class));
    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(R.anim.activity_open_scale, R.anim.activity_close_translate);
    }
}
