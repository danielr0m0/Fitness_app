package com.romodaniel.fitness;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.NavigationView;

import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.util.Log;


import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.romodaniel.fitness.data.DBHelper;

import com.romodaniel.fitness.data.UserDbUtils;

import static com.romodaniel.fitness.data.Contract.TABLE_USER.*;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private SQLiteDatabase db;
    private DBHelper helper;
    private TextView userName;
    private TextView greeting;

    private String TAG= "userTest";


    @Override
    protected void onStart() {
        super.onStart();


        // TODO - CREATE AN ADAPTER
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        helper = new DBHelper(this);
        db = helper.getWritableDatabase();

        View hView = navigationView.getHeaderView(0);
        userName = (TextView) hView.findViewById(R.id.user_name);
        greeting = (TextView) findViewById(R.id.greeting);


        Cursor userCursor = UserDbUtils.getAll(db);

        if(userCursor.moveToFirst() == false){
            findViewById(R.id.main).setVisibility(View.GONE);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.contentMain, new UserInputFragment())
                    .commit();

        }else{
            userCursor.moveToFirst();
            String firstName = userCursor.getString(userCursor.getColumnIndex(COLUMN_NAME_FIRST_NAME));
            String lastName = userCursor.getString(userCursor.getColumnIndex(COLUMN_NAME_LAST_NAME));
            String greets = greeting.getText().toString()+" " + firstName;
            greeting.setText(greets);
            userName.setText(firstName + " "+ lastName);
        }




    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (getFragmentManager().getBackStackEntryCount() == 0) {
                findViewById(R.id.main).setVisibility(View.VISIBLE);
                super.onBackPressed();
            } else {
                getFragmentManager().popBackStack();
            }
        }

    }


//   settings not yet on the list to do
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        findViewById(R.id.main).setVisibility(View.GONE);

//
//        if (id == R.id.nav_profile) {
//            getSupportFragmentManager()
//                    .beginTransaction()
//                    .replace(R.id.contentMain, new ProfileFragment())
//                    .addToBackStack(null)
//                    .commit();
//        }
         if (id == R.id.nav_tracker) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.contentMain, new TrackerFragment())
                    .addToBackStack(null)
                    .commit();

        } else if(id == R.id.nav_action){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.contentMain, new ActivityFragment())
                    .addToBackStack(null)
                    .commit();

        }

        //if we have time to do
//            if (id == R.id.nav_notes) {

//                iv.setImageResource(R.drawable.journal);
//            }
//        else } else if (id == R.id.nav_planner) {
//            iv.setImageResource(R.drawable.calendar);
//
//        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}



