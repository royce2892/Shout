package com.hack.start.application;

import android.app.Application;
import android.util.Log;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParsePush;
import com.parse.ParseUser;
import com.parse.SaveCallback;

/**
 * Created by RRaju on 7/22/2015.
 */
public class MyApplication extends Application {

    private static MyApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        Parse.enableLocalDatastore(this);

        Parse.initialize(this, "N4mJeYM2ky5SczHq2Wh4EhqjhCB2NAUfMJvcUc6k", "tYG3jgVJXwvQDRi0bVENAN0vOFNT6lhUf5lTbRuo");
        ParseInstallation installation = ParseInstallation.getCurrentInstallation();
        ParseUser.enableAutomaticUser();
        installation.put("user", ParseUser.getCurrentUser());
        installation.saveInBackground();
        ParsePush.subscribeInBackground("shout");
    }

    public static synchronized MyApplication getInstance() {
        return mInstance;
    }
}
