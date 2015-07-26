package com.hack.start.fragment;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;

import com.hack.start.R;
import com.hack.start.activity.AboutActivity;
import com.hack.start.preference.PreferenceManager;

/**
 * Created by NCherian on 15-Apr-15.
 */
public class SettingsFragment extends PreferenceFragment {

    PreferenceManager manager;
    boolean shoutOn,messageOn;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        manager = new PreferenceManager(getActivity());
        shoutOn = manager.getBoolean("shout");
        messageOn = manager.getBoolean("message");
        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.settings_preferences);

    }

    @Override
    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
        super.onPreferenceTreeClick(preferenceScreen, preference);

        // If the user has clicked on a preference screen, set up the action bar
        if (preference instanceof PreferenceScreen) {

        }  else if(preference.getKey().contentEquals(getString(R.string.pref_shout_note))) {
            manager.put("shout", !manager.getBoolean("shout"));
        } else if(preference.getKey().contentEquals(getString(R.string.pref_shout_note))) {
            manager.put("message",!manager.getBoolean("message"));
        }
        return false;
    }

    private void initiateEmail() {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto", "support@cookmaadi.com", null));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name) + " Feedback");
        try {
            emailIntent.putExtra(Intent.EXTRA_TEXT, "This information will help improve " + getResources().getString(R.string.app_name) + " app experience.\n " +
                    "\nDevice:" + android.os.Build.MODEL + "\nApp Version:" + getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(), 0).versionCode + " \nAndroid Version:" + Build.VERSION.SDK_INT);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        startActivity(Intent.createChooser(emailIntent, "Send Feeback"));
    }
}
