package com.hack.start.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toolbar;

import com.hack.start.adapter.PhraseAdapter;
import com.hack.start.fragment.HomeFragment;
import com.hack.start.fragment.PhraseFragment;
import com.hack.start.fragment.SafetyFragment;
import com.hack.start.model.NavDrawerItem;
import com.hack.start.adapter.NavDrawerListAdapter;
import com.hack.start.R;

import java.util.ArrayList;

public class HomeActivity extends BaseActivity {

    private Toolbar toolbar;

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;

    // Navigation drawer title
    private ArrayList<NavDrawerItem> navDrawerItems;
    private NavDrawerListAdapter adapter;
    private String[] navMenuTitles;
    private TypedArray navMenuIcons;
    private Bundle bundle;
    private HomeFragment homeFragment;
    private SafetyFragment safetyFragment;
    private PhraseFragment phraseFragment;
    private String location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        location = getIntent().getStringExtra("location");
        if (!location.contentEquals("bangalore") &&
                !location.contentEquals("delhi") &&
                !location.contentEquals("mumbai")&&
        !location.contentEquals("goa") )
            location = "bangalore";
        getSupportActionBar().setTitle(location.toUpperCase());
        bundle = new Bundle();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.list_slidermenu);
        mDrawerLayout.getRootView().setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        // load slide menu items
        navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);

        // nav drawer icons from resources
        navMenuIcons = getResources()
                .obtainTypedArray(R.array.nav_drawer_icons);

        navDrawerItems = new ArrayList<NavDrawerItem>();
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[0], navMenuIcons.getResourceId(0, -1)));
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[1], navMenuIcons.getResourceId(1, -1)));
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[2], navMenuIcons.getResourceId(2, -1)));
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[3], navMenuIcons.getResourceId(3, -1)));
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[4], navMenuIcons.getResourceId(4, -1)));
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[5], navMenuIcons.getResourceId(5, -1)));
        // Recycle the typed array
        navMenuIcons.recycle();


        mDrawerList.setOnItemClickListener(new SlideMenuClickListener());

        // Setting the nav drawer list adapter
        adapter = new NavDrawerListAdapter(getApplicationContext(),
                navDrawerItems);
        mDrawerList.setAdapter(adapter);
        // Enabling action bar brazilianrecipes icon and behaving it as toggle button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.app_name, R.string.app_name) {
            public void onDrawerClosed(View view) {
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                invalidateOptionsMenu();
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        if (savedInstanceState == null) {
            // on first time display view for first nav item
            handleNavMenuClicks(0);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    /**
     * Navigation drawer menu item click listener
     */
    private class SlideMenuClickListener implements
            ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            handleNavMenuClicks(position);
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(Gravity.START | Gravity.LEFT)) {
            mDrawerLayout.closeDrawers();
            return;
        }
        startActivity(new Intent(this, MainActivity.class));
        overridePendingTransition(R.anim.activity_open_translate, R.anim.activity_close_scale);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_home;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //  getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void handleNavMenuClicks(int position) {
        switch (position) {
            case 0:
                homeFragment = HomeFragment.newInstance(location);
                replaceFragment(homeFragment, 0, 0, 0, 0);
                break;
            case 1:
                safetyFragment = SafetyFragment.newInstance(location);
                replaceFragment(safetyFragment, 0, 0, 0, 0);
                break;
            case 2:
                phraseFragment = PhraseFragment.newInstance(location);
                replaceFragment(phraseFragment, 0, 0, 0, 0);
                break;
            case 3:
                startActivity(new Intent(HomeActivity.this, MessageActivity.class));
                overridePendingTransition(R.anim.activity_open_translate, R.anim.activity_close_scale);
                break;
            case 4:
                shareApp();
                break;
            case 5:
                Intent intent = new Intent(HomeActivity.this, SettingsActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.activity_push_up_in, R.anim.activity_push_up_out);
                break;
            default:
                break;
        }
        mDrawerList.setItemChecked(position, true);
        mDrawerList.setSelection(position);
        mDrawerLayout.closeDrawer(mDrawerList);
    }

    private void replaceFragment(Fragment frag, int enter, int exit, int popEnter,
                                 int popExit) {
        FragmentTransaction ft = this.getSupportFragmentManager().beginTransaction();

        if (enter != 0 || exit != 0 || popEnter != 0 || popExit != 0)
            ft.setCustomAnimations(enter, exit, popEnter, popExit);

        ft.replace(R.id.frame_container, frag)
                .commit();
    }


    private void shareApp() {
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String shareBody = "Try this brazilianrecipes - " + getResources().getString(R.string.app_name)
                + " http://play.google.com/store/apps/details?id="
                + getPackageName();
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, R.string.app_name);
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }


}
