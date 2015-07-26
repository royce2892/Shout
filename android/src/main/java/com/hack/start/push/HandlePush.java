
package com.hack.start.push;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;


import com.hack.start.R;
import com.hack.start.activity.MessageActivity;
import com.hack.start.preference.PreferenceManager;
import com.parse.ParsePushBroadcastReceiver;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by RRaju on 6/10/2015.
 */

public class HandlePush extends ParsePushBroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action.equals(ParsePushBroadcastReceiver.ACTION_PUSH_RECEIVE)) {
            try {
                JSONObject json = new JSONObject(intent.getExtras().getString("com.parse.Data"));
                analyzeJson(json, context);
            } catch (JSONException e) {
            }
        }
    }

    private void analyzeJson(final JSONObject json, final Context context) {
        try {
            // 0 - for update , 1 - for update local ds , 2- for random message
            String content = json.getString("content");
            String from = json.getString("from");
            PreferenceManager manager = new PreferenceManager(context);
            if (!manager.getBoolean("shout") && content.contentEquals("shout"))
                return;
            if (!manager.getBoolean("message") && !content.contentEquals("shout"))
                return;
            if (!from.contentEquals(manager.get("name")))
                generate_notification(context, from.concat(" : ").concat(content));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void generate_notification(Context context, String content) throws JSONException {
        Intent i = new Intent(context, MessageActivity.class);
        PendingIntent pintent = PendingIntent.getActivity(context, 0, i, 0);

        Notification notif = new NotificationCompat.Builder(context)
                .setContentTitle(context.getString(R.string.app_name))
                .setContentText(content)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pintent)
                .setDefaults(Notification.DEFAULT_ALL)
                .setAutoCancel(true)
                .build();

        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, notif);
    }
}

