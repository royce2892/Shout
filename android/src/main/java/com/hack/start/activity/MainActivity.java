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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.hack.start.R;
import com.hack.start.preference.PreferenceManager;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParsePush;
import com.parse.ParseQuery;
import com.parse.SaveCallback;
import com.parse.SendCallback;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Rraju on 7/25/2015.
 */
public class MainActivity extends BaseActivity {

    EditText mPlace;
    TextView mExplore, mShout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setHomeButtonEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        mPlace = (EditText) findViewById(R.id.plan_place);

        mExplore = (TextView) findViewById(R.id.place_explore);
        mShout = (TextView) findViewById(R.id.shout);

        mExplore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoHomeActivity();
            }
        });
        mShout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shout();
            }
        });
    }

    private void shout() {
        PreferenceManager manager = new PreferenceManager(this);
        if(manager.get("name").contentEquals("default"))
            askForNameDialog(manager);
        else
            shoutAndStartActivity(manager);

    }

    private void shoutAndStartActivity(PreferenceManager manager) {
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setTitle("Shouting");
        dialog.setMessage("Please wait we shout for you");
        dialog.setCancelable(false);
        dialog.show();

        JSONObject push = new JSONObject();
        try {
            push.put("content", "shout");
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

        ParseObject shout = ParseObject.create("Message");
        shout.put("content","shout");
        shout.put("from",manager.get("name"));
        shout.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if(e==null) {
                    dialog.cancel();
                    gotoMessageActivity();
                }
            }
        });
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
                                shoutAndStartActivity(manager);
                            }
                        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void gotoMessageActivity() {
        startActivity(new Intent(this, MessageActivity.class));
        overridePendingTransition(R.anim.activity_open_translate, R.anim.activity_close_scale);
    }

    private void gotoHomeActivity() {
        hideKeyBoard(this);
        startActivity(new Intent(this, HomeActivity.class).putExtra("location",mPlace.getText().toString()));
        overridePendingTransition(R.anim.activity_open_translate, R.anim.activity_close_scale);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_main;
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            overridePendingTransition(R.anim.activity_push_up_in, R.anim.activity_push_up_out);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);    }
}
