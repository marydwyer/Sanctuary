package com.example.root.myapplication;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public enum TabView {
        home, messaging, journey, emergency
    }
    public enum SwipeDirection {
        right, left, up, down
    }

    private TabView currentTab = TabView.home;

    ////////Used for the swiping:
    private static float x1, x2, y1, y2;
    static final int MIN_DISTANCE = 150;
    public View.OnTouchListener touchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent event) {
            switch(event.getAction())
            {
                case MotionEvent.ACTION_DOWN:
                    Log.e("Ugh","Action Down");
                    x1 = event.getX();
                    y1 = event.getY();
                    break;
                case MotionEvent.ACTION_UP:
                    Log.e("Ugh","Action Up");
                    x2 = event.getX();
                    y2 = event.getY();

                    float deltaX = x2 - x1;
                    float deltaY = y2 - y1;

                    if(Math.abs(deltaX) >= Math.abs(deltaY)){
                        if(deltaX > MIN_DISTANCE)  changeTabs(SwipeDirection.right);
                        else if(deltaX < -1*MIN_DISTANCE) changeTabs(SwipeDirection.left);
                    }
                    else{
                        if(deltaY < -1*MIN_DISTANCE) changeTabs(SwipeDirection.down);
                        else if(deltaY > MIN_DISTANCE) changeTabs(SwipeDirection.up);
                    }

                    break;
            }
            return true;
        }
    };
    ////////
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //Set the first fragment to home:
        changeTabs(TabView.home);

        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.ic_logo);
        toolbar.setNavigationIcon(R.mipmap.ic_logo);
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
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the home/Up button, so long
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

        if (id == R.id.home) {
            changeTabs(TabView.home);
        } else if (id == R.id.messaging) {
            changeTabs(TabView.messaging);
        } else if (id == R.id.journey) {
            changeTabs(TabView.journey);
        } else if (id == R.id.emergency) {
            changeTabs(TabView.emergency);
        }
        return true;
    }

    //Overloaded function of the one below:
    public void changeTabs(SwipeDirection dir) {
        switch (currentTab) {
            case home:
                if (dir == SwipeDirection.right) changeTabs(TabView.messaging);
                else if (dir == SwipeDirection.left) changeTabs(TabView.journey);
                else if (dir == SwipeDirection.down) changeTabs(TabView.emergency);
                break;

            case journey:
                if (dir == SwipeDirection.right) changeTabs(TabView.home);
                break;

            case messaging:
                if (dir == SwipeDirection.left) changeTabs(TabView.home);
                break;

            case emergency:
                if (dir == SwipeDirection.up) changeTabs(TabView.home);
                break;
        }
    }

    public void changeTabs(TabView tv) {

        Fragment fragment = null;
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        switch (tv) {
            case home:
                fragment = new Home();
                currentTab = TabView.home;
                break;

            case messaging:
                fragment = new Messaging();
                currentTab = TabView.messaging;
                break;

            case journey:
                fragment = new Journey();
                currentTab = TabView.journey;
                break;

            case emergency:
                fragment = new Emergency();
                currentTab = TabView.emergency;
                break;

            default:
                return;
        }

        if (fragment != null) fragmentTransaction.replace(R.id.container, fragment).commit();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }
}


