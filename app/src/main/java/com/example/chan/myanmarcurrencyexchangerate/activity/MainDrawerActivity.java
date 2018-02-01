package com.example.chan.myanmarcurrencyexchangerate.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;

import com.example.chan.myanmarcurrencyexchangerate.R;
import com.example.chan.myanmarcurrencyexchangerate.common.Constants;
import com.example.chan.myanmarcurrencyexchangerate.common.helper.LocaleHelper;
import com.example.chan.myanmarcurrencyexchangerate.fragment.CurrencyInfoFragment;
import com.example.chan.myanmarcurrencyexchangerate.fragment.ExchangeListFragment;
import com.example.chan.myanmarcurrencyexchangerate.fragment.SettingFragment;

public class MainDrawerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG_EXCHANGE_LIST = "EXCHANGE_LIST";
    private static final String TAG_CURRENCY_LIST = "CURRENCY_CURRENCY_LIST";
    private static final String TAG_SETTING = "SETTING";
    private static String CURRENT_TAG = TAG_EXCHANGE_LIST;

    private static int navItemIndex = 0;

    private Handler mHandler;

    private SharedPreferences.OnSharedPreferenceChangeListener prefListener;

    private SharedPreferences prefs;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_drawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setTitle(getString(R.string.label_exchange_list));

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

        registerValues();

        registerEvents();

    }

    private void registerValues() {
        //get default preferences
        prefs = PreferenceManager.getDefaultSharedPreferences(this);

    }

    private void registerEvents() {

        // listen preference changes
        prefListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {

                if(Constants.SELECTED_LANGUAGE.equals(key)) {
                    refreshActivity();
                }
            }
        };

        prefs.registerOnSharedPreferenceChangeListener(prefListener);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_exchange_list) {
            // Handle the exchange list action
            navItemIndex = Constants.EXCHANGE_LIST_INDEX;
            CURRENT_TAG = TAG_EXCHANGE_LIST;
            setTitle(getString(R.string.label_exchange_list));
            loadHomeFragment();
        } else if (id == R.id.nav_currency_list) {
            navItemIndex = Constants.CURRENCY_LIST_INDEX;
            CURRENT_TAG = TAG_CURRENCY_LIST;
            setTitle(getString(R.string.label_currency_list));
            loadHomeFragment();
        } else if (id == R.id.nav_setting) {
            navItemIndex = Constants.SETTING_INDEX;
            CURRENT_TAG = TAG_SETTING;
            setTitle(getString(R.string.label_setting));
            loadHomeFragment();
        }

        closeDrawer();

        return true;
    }

    private Fragment getHomeFragment() {
        switch (navItemIndex) {
            case Constants.EXCHANGE_LIST_INDEX:
                ExchangeListFragment exchangeListFragment = ExchangeListFragment.newInstance(null, null);
                return exchangeListFragment;
            case Constants.CURRENCY_LIST_INDEX:
                CurrencyInfoFragment currencyInfoFragment = CurrencyInfoFragment.newInstance(null, null);
                return currencyInfoFragment;
            case Constants.SETTING_INDEX:
                SettingFragment settingFragment = SettingFragment.newInstance(null, null);
                return settingFragment;
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

        // Load home fragment with thread
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

    private void refreshActivity() {
        // Refresh the app
        Intent intent = new Intent(this, this.getClass());
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finishAffinity();
        /*startActivity(refresh);
        finish();*/
    }
}
