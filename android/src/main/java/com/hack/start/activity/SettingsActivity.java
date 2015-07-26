package com.hack.start.activity;

import android.os.Bundle;

import com.hack.start.R;
import com.hack.start.fragment.SettingsFragment;

public class SettingsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setTitle("Settings");
        getFragmentManager().beginTransaction()
                .replace(R.id.settings_fragment, new SettingsFragment())
                .commit();
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_settings;
    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(R.anim.activity_push_down_in, R.anim.activity_push_down_out);
    }
}
