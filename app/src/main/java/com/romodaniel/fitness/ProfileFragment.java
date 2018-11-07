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


public class ProfileFragment extends Fragment  {

    private Spinner spinner;
    private SQLiteDatabase db;
    private DBHelper helper;

    private TextView totalRuns;
    private TextView totalMiles;
    private TextView totalTime;
    private TextView totalCal;


    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        spinner=(Spinner) view.findViewById(R.id.spinner);


        spinner.setAdapter(new ArrayAdapter<history>(this.getActivity(), R.layout.spinner_item, history.values()));

        helper = new DBHelper(this.getActivity());
        db = helper.getWritableDatabase();

        totalRuns =(TextView) view.findViewById(R.id.total_runs);
        totalCal = (TextView) view.findViewById(R.id.total_cal);
        totalMiles =(TextView) view.findViewById(R.id.total_miles);
        totalTime = (TextView) view.findViewById(R.id.total_time);

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
                    String warning = "please go Run";
                    Toast toast = Toast.makeText(getActivity(), warning, Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.BOTTOM|Gravity.CENTER, 0, 100);
                    toast.show();

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

                    totalMiles.setText("" + miles);
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
                    totalMiles.setText("" + miles);
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
