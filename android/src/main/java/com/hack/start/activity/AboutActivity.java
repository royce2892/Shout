package com.hack.start.activity;

import android.content.pm.PackageManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.hack.start.R;

import java.util.Calendar;

public class AboutActivity extends ActionBarActivity {

    private String versionName;
    private TextView version, copyright;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        version = (TextView)findViewById(R.id.version);
        copyright= (TextView)findViewById(R.id.copyright);
        try {
            versionName = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        if(versionName!=null)
            version.setText("Version "+versionName);

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);

        copyright.setText(getString(R.string.copyright_symbol)+" "+getString(R.string.company_start_year)+"-"+year+" "+getString(R.string.company_name));
    }
}
