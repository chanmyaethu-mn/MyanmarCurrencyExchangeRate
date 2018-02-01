package com.example.chan.myanmarcurrencyexchangerate.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.chan.myanmarcurrencyexchangerate.R;
import com.example.chan.myanmarcurrencyexchangerate.common.Constants;
import com.example.chan.myanmarcurrencyexchangerate.fragment.CurrencyInfoFragment;
import com.example.chan.myanmarcurrencyexchangerate.fragment.ExchangeListFragment;

public class MainDrawerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG_EXCHANGE_LIST = "EXCHANGE_LIST";
    private static final String TAG_CURRENCY_INFO = "CURRENCY_INFO";
    private static String CURRENT_TAG = TAG_EXCHANGE_LIST;

    private static int navItemIndex = 0;

    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_drawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().getItem(0).setChecked(true);

        mHandler = new Handler();

        if (savedInstanceState == null) {
            navItemIndex = 0;
            CURRENT_TAG = TAG_EXCHANGE_LIST;
            loadHomeFragment();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
            navItemIndex = 0;
            CURRENT_TAG = TAG_EXCHANGE_LIST;
            loadHomeFragment();
        } else if (id == R.id.nav_gallery) {
            navItemIndex = 1;
            CURRENT_TAG = TAG_CURRENCY_INFO;
            loadHomeFragment();
        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        closeDrawer();

        return true;
    }

    private Fragment getHomeFragment() {
        switch (navItemIndex) {
            case Constants.EXCHANGE_LIST_INDEX:
                ExchangeListFragment exchangeListFragment = ExchangeListFragment.newInstance(null, null);
                return exchangeListFragment;
            case Constants.CURRENCY_INFO_INDEX:
                CurrencyInfoFragment currencyInfoFragment = CurrencyInfoFragment.newInstance(null, null);
                return currencyInfoFragment;
            default:
                return null;
        }
    }

    private void loadHomeFragment() {

        // if user select the current navigation menu again, don't do anything
        // just close the navigation drawer
        if (getSupportFragmentManager().findFragmentByTag(CURRENT_TAG) != null) {
            closeDrawer();
            return;
        }

        Runnable mPendingRunnable = new Runnable() {
            @Override
            public void run() {
                Fragment fragment = getHomeFragment();

                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.content_main, fragment, CURRENT_TAG)
                        .commit();
            }
        };

        // If mPendingRunnable is not null, then add to the message queue
        if (mPendingRunnable != null) {
            mHandler.post(mPendingRunnable);
        }

    }

    private void closeDrawer() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }
}
