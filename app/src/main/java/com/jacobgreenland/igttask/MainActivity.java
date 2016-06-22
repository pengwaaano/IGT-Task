package com.jacobgreenland.igttask;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telecom.ConnectionService;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.jacobgreenland.igttask.data.GameRepository;
import com.jacobgreenland.igttask.model.Data;
import com.jacobgreenland.igttask.model.Games;
import com.jacobgreenland.igttask.observables.IGameAPI;

import java.util.Locale;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity{

    Toolbar toolbar;

    public static boolean isOnline = true;

    @Inject
    public static IGameAPI _api;

    BroadcastReceiver broadcastReceiver;

    public static GameRepository gameRepository;

    public static Data chosenGame;

    public static Snackbar admiral;

    public static CoordinatorLayout coordinatorLayout;

    public static Games loadedGame;

    ListFragment f;

    public static Locale deviceLocale;

    ImageView backButton;

    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        deviceLocale = Resources.getSystem().getConfiguration().locale;

        initialiseToolbar();

        f = new ListFragment();


        Intent intent = new Intent(this, ConnectionService.class);
        startService(intent);

        gameRepository = new GameRepository(getApplicationContext());

        ((MyApp) getApplication()).getApiComponent().inject(this);

        fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();

        ft.add(R.id.mainFragment, f, "tabs");
        ft.addToBackStack(null);
        ft.commit();

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.snackbarPosition);

        Snackbar snackbar = Snackbar
                .make(coordinatorLayout, "No Internet Connection", Snackbar.LENGTH_INDEFINITE);
        snackbar.show();

        installListener(snackbar);

        f.loadGames();

        backButton = (ImageView) findViewById(R.id.home);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "test", Toast.LENGTH_LONG).show();
                //What to do on back clicked
                //fragmentManager.popBackStack();
                onBackPressed();
            }
        });
        admiral.make(coordinatorLayout, "No Internet Connection",Snackbar.LENGTH_INDEFINITE);
        //initialiseFloatingButton();
    }
    public void initialiseToolbar()
    {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    public void onBackPressed() {

        //super.onBackPressed();
        Log.i("MainActivity", "" + fragmentManager.getBackStackEntryCount());
        if (fragmentManager.getBackStackEntryCount() > 0) {
            Log.i("MainActivity", "popping backstack");
            fragmentManager.popBackStack();
        } else {
            Log.i("MainActivity", "nothing on backstack, calling super");
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                FragmentManager fm = getSupportFragmentManager();
                if (fm.getBackStackEntryCount() > 0) {
                    fm.popBackStack();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void switchContent(int id, Fragment fragment, View view) {
        //Switch Fragments method
        Log.d("Test", "Fragment changed!");

        fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(id, fragment, fragment.toString());
        ft.addToBackStack(null);
        ft.commit();
    }

    @Override
    public void onStop()
    {
        super.onStop();
        unregisterReceiver(broadcastReceiver);
    }

    private void installListener(final Snackbar snack) {

        if (broadcastReceiver == null) {

            broadcastReceiver = new BroadcastReceiver() {

                @Override
                public void onReceive(Context context, Intent intent) {

                    Bundle extras = intent.getExtras();

                    NetworkInfo info = (NetworkInfo) extras
                            .getParcelable("networkInfo");

                    NetworkInfo.State state = info.getState();
                    //Log.d("InternalBroadcastReceiver", info.toString() + " " + state.toString());

                    if (state == NetworkInfo.State.CONNECTED) {

                        MainActivity.isOnline = true;
                        snack.dismiss();
                        Log.d("tag", "" + MainActivity.isOnline);

                    } else {

                        MainActivity.isOnline = false;
                        snack.show();
                        Log.d("tag", "" + MainActivity.isOnline);
                    }

                }
            };

            final IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
            registerReceiver(broadcastReceiver, intentFilter);
        }
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        deviceLocale = newConfig.locale;
    }
}
