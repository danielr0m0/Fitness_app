package com.romodaniel.fitness;

import android.content.Context;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;

import java.util.Calendar;

import com.romodaniel.fitness.data.CalDbUtils;
import com.romodaniel.fitness.data.Contract;
import com.romodaniel.fitness.data.DBHelper;
import com.romodaniel.fitness.data.Cal_Record;

public class TrackerFragment extends Fragment {

    private static SQLiteDatabase db;
    private DBHelper helper;
    private static Context c;

    CalendarView calender;
    TextView cal_screen;


    Button addButton;

    int month, day, year, cal_value;

    String date;

    public TrackerFragment() {
        // Required empty public constructor
    }
    /*
    public static void getDate() {
        return date;
    }*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tracker, container, false);

        calender = (CalendarView) view.findViewById(R.id.calendar);

        cal_screen = (TextView) view.findViewById(R.id.cal_screen);

        addButton =(Button) view.findViewById(R.id.addButton);

        month = Calendar.getInstance().get(Calendar.MONTH) +1;

        day =  Calendar.getInstance().get(Calendar.DAY_OF_MONTH);

        year =  Calendar.getInstance().get(Calendar.YEAR);

        cal_screen.setText("0");
        cal_value = 0;

        date = "" + year +'-' + month + '-' + day;

        c = getContext();

        Log.d("cdate", date);


        helper = new DBHelper(getActivity());
        db = helper.getWritableDatabase();



        Cursor calCursor = CalDbUtils.getAll(db);
        if(calCursor.moveToFirst()) {
            Log.d("calCursor", calCursor.moveToFirst() + "");

            do {
                String selectDate = calCursor.getString(calCursor.getColumnIndex(Contract.TABLE_EATS.COLUMN_NAME_DATE));
                if (selectDate.equals(date))
                    cal_value += calCursor.getInt(calCursor.getColumnIndex(Contract.TABLE_EATS.COLUMN_NAME_VALUE));
            } while (calCursor.moveToNext());

            cal_screen.setText("" + cal_value);
        }



        calender.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month,
                                            int dayOfMonth) {
                date = "" + year +'-' + ++month + "-" +dayOfMonth;
                Cursor calCursor = CalDbUtils.getAll(db);
                if(calCursor.moveToFirst()) {

                    Log.d("calCursor", calCursor.moveToFirst() + "");
                    cal_value = 0;
                    do {
                        String selectDate = calCursor.getString(calCursor.getColumnIndex(Contract.TABLE_EATS.COLUMN_NAME_DATE));
                        if (selectDate.equals(date))
                            cal_value += calCursor.getInt(calCursor.getColumnIndex(Contract.TABLE_EATS.COLUMN_NAME_VALUE));
                    } while (calCursor.moveToNext());

                    cal_screen.setText("" + cal_value);
                }
            }
        });



        addButton.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                InputFragment inputFragment = new InputFragment();

                inputFragment.setDate(date);

                FragmentManager fm = getFragmentManager();
                fm.beginTransaction()
                        .replace(R.id.contentMain, inputFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }
    /*
    private Context getContext() {
        return c;
    }*/

    public static SQLiteDatabase getdb(){
        return db;
    }

}