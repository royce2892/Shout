package com.hack.start.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.hack.start.R;

/**
 * A BaseActivity to Setup the Toolbar as ActionBar and set Back Button navigation
 *
 * Created by npc on 19/04/15.
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResource());
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
            setUpActionBar(getSupportActionBar());
            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        }
    }

    protected abstract int getLayoutResource();

    protected void setActionBarIcon(int iconRes) {
        mToolbar.setNavigationIcon(iconRes);
    }

    private void setUpActionBar(ActionBar actionBar) {
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(getTitle());
        actionBar.setDisplayHomeAsUpEnabled(true);
    }
}
