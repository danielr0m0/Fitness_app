package com.romodaniel.fitness;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.romodaniel.fitness.data.Contract;
import com.romodaniel.fitness.data.DBHelper;
import com.romodaniel.fitness.data.RunsDatabaseUtils;
import com.romodaniel.fitness.data.history;

import java.util.Locale;


public class ActivityFragment extends Fragment {
    private ImageView startRun;
    private TextView totalRuns;
    private TextView totalMiles;
    private TextView totalTime;
    private TextView totalCal;
    private Spinner spinner;
    private View view;
    private SQLiteDatabase db;



    public ActivityFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_activity, container, false);

        startRun = (ImageView) view.findViewById(R.id.startRun);
        totalRuns =(TextView) view.findViewById(R.id.total_runs);
        totalCal = (TextView) view.findViewById(R.id.total_cal);
        totalMiles =(TextView) view.findViewById(R.id.total_miles);
        totalTime = (TextView) view.findViewById(R.id.total_time);
        spinner=(Spinner) view.findViewById(R.id.spinner);


        spinner.setAdapter(new ArrayAdapter<history>(this.getActivity(), R.layout.spinner_item, history.values()));


        DBHelper dbhelper = new DBHelper(this.getActivity());
        db = dbhelper.getWritableDatabase();
        Cursor cursor = RunsDatabaseUtils.getAll(db);

        double miles=0;
        double calories =0;
        int sec =0;

        //go in every entry add it to the last
        while(cursor.moveToNext()){
            miles += cursor.getDouble(cursor.getColumnIndex(Contract.TABLE_RUNS.COLUMN_NAME_MILES));
            calories += cursor.getDouble(cursor.getColumnIndex(Contract.TABLE_RUNS.COLUMN_NAME_CAL));
            sec += cursor.getDouble(cursor.getColumnIndex(Contract.TABLE_RUNS.COLUMN_NAME_TIME));
        }


        totalMiles.setText("" + miles);
        totalCal.setText(String.format(Locale.US, "%.2f", calories));
        totalTime.setText(String.format(Locale.US, "%02d:%02d:%02d", sec/36000, (sec/60)%60, sec %60));


        //total run is the id of the last run entered since user cant delete runs
        if(cursor.moveToLast()){
            totalRuns.setText(""+cursor.getInt(0));
        }else {
            totalRuns.setText("0");
        }

        final GoogleMapsFragment googleMapsFragment = new GoogleMapsFragment();
        googleMapsFragment.enableMyLocation(getActivity());

        startRun.setOnClickListener(new View.OnClickListener() {
            // once the image is clicked get the google maps fragment where the user can start run
            @Override
            public void onClick(View v) {
                //transition to map tracer
                view.findViewById(R.id.start).setVisibility(View.GONE);

                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_activity, googleMapsFragment)
                        .commit();

            }
        });



        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Cursor runCursor = RunsDatabaseUtils.getAll(db);
                if(runCursor.moveToFirst()==false){

                    totalRuns.setText("0");
                    totalMiles.setText("0");
                    totalCal.setText(String.format(Locale.US, "%.2f", 0.0));
                    totalTime.setText(String.format(Locale.US, "%02d:%02d:%02d", 0, 0, 0));
                }
                else if (spinner.getItemAtPosition(position).toString().equals("Total")){

                    double miles=0;
                    double calories =0;
                    int sec =0;
                    //go in every entry add it to the last
                    do{
                        miles += runCursor.getDouble(runCursor.getColumnIndex(Contract.TABLE_RUNS.COLUMN_NAME_MILES));
                        calories += runCursor.getDouble(runCursor.getColumnIndex(Contract.TABLE_RUNS.COLUMN_NAME_CAL));
                        sec += runCursor.getInt(runCursor.getColumnIndex(Contract.TABLE_RUNS.COLUMN_NAME_TIME));
                    }while(runCursor.moveToNext());

                    totalMiles.setText(String.format(Locale.US, "%.2f", miles));
                    totalCal.setText(String.format(Locale.US, "%.2f", calories));
                    totalTime.setText(String.format(Locale.US, "%02d:%02d:%02d", sec/36000, (sec/60)%60, sec %60));


                    if(runCursor.moveToLast()){
                        totalRuns.setText(""+runCursor.getInt(0));
                    }else {
                        totalRuns.setText("0");
                    }

                }else {
                    runCursor.moveToLast();

                    double miles;
                    double calories;
                    int sec;

                    miles = runCursor.getDouble(runCursor.getColumnIndex(Contract.TABLE_RUNS.COLUMN_NAME_MILES));
                    calories = runCursor.getDouble(runCursor.getColumnIndex(Contract.TABLE_RUNS.COLUMN_NAME_CAL));
                    sec = runCursor.getInt(runCursor.getColumnIndex(Contract.TABLE_RUNS.COLUMN_NAME_TIME));
                    totalRuns.setText(""+runCursor.getInt(0));
                    totalMiles.setText(String.format(Locale.US, "%.2f", miles));
                    totalCal.setText(String.format(Locale.US, "%.2f", calories));
                    totalTime.setText(String.format(Locale.US, "%02d:%02d:%02d", sec/36000, (sec/60)%60, sec %60));

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
