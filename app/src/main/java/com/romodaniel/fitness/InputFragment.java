package com.romodaniel.fitness;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.romodaniel.fitness.data.CalDbUtils;
import com.romodaniel.fitness.data.Cal_Record;
import com.romodaniel.fitness.data.DBHelper;

import java.util.Calendar;

/**
 * Created by fy on 7/26/17.
 */

public class InputFragment extends Fragment {

    private SQLiteDatabase db;
    private DBHelper helper;
    ImageButton searchButton, backSpace;
    TextView numScreen, backButton;
    Button button0, button1, button2, button3, button4, button5, button6, button7, button8, button9, addButton;
    String date, description;

    int month, day, year;

    private String m_Text = "";

    public InputFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.cal_add, container, false);

        backButton = (TextView) view.findViewById(R.id.backButton);

        searchButton = (ImageButton) view.findViewById(R.id.searchButton);

        numScreen =(TextView) view.findViewById(R.id.numScreen);

        button0 = (Button) view.findViewById(R.id.Button0);
        button1 = (Button) view.findViewById(R.id.Button1);
        button2 = (Button) view.findViewById(R.id.Button2);
        button3 = (Button) view.findViewById(R.id.Button3);
        button4 = (Button) view.findViewById(R.id.Button4);
        button5 = (Button) view.findViewById(R.id.Button5);
        button6 = (Button) view.findViewById(R.id.Button6);
        button7 = (Button) view.findViewById(R.id.Button7);
        button8 = (Button) view.findViewById(R.id.Button8);
        button9 = (Button) view.findViewById(R.id.Button9);

        helper = new DBHelper(getActivity());
        db = helper.getWritableDatabase();

        backSpace = (ImageButton) view.findViewById(R.id.ButtonBS);

        addButton =(Button) view.findViewById(R.id.addButton);

        backButton.setOnClickListener( new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                fm.beginTransaction()
                        .replace(R.id.contentMain, new TrackerFragment())
                        .commit();
            }
        });



        button0.setOnClickListener( new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                if(checkZero(numScreen))
                    numScreen.setText("0");
                else if(checkDigit(numScreen))
                    numScreen.append("0");
            }
        });

        button1.setOnClickListener( new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                if(checkZero(numScreen))
                    numScreen.setText("1");
                else if(checkDigit(numScreen))
                    numScreen.append("1");
            }
        });

        button2.setOnClickListener( new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                if(checkZero(numScreen))
                    numScreen.setText("2");
                else if(checkDigit(numScreen))
                    numScreen.append("2");
            }
        });

        button3.setOnClickListener( new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                if(checkZero(numScreen))
                    numScreen.setText("3");
                else if(checkDigit(numScreen))
                    numScreen.append("3");
            }
        });

        button4.setOnClickListener( new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                if(checkZero(numScreen))
                    numScreen.setText("4");
                else if(checkDigit(numScreen))
                    numScreen.append("4");
            }
        });

        button5.setOnClickListener( new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                if(checkZero(numScreen))
                    numScreen.setText("5");
                else if(checkDigit(numScreen))
                    numScreen.append("5");
            }
        });

        button6.setOnClickListener( new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                if(checkZero(numScreen))
                    numScreen.setText("6");
                else if(checkDigit(numScreen))
                    numScreen.append("6");
            }
        });

        button7.setOnClickListener( new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                if(checkZero(numScreen))
                    numScreen.setText("7");
                else if(checkDigit(numScreen))
                    numScreen.append("7");
            }
        });

        button8.setOnClickListener( new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                if(checkZero(numScreen))
                    numScreen.setText("8");
                else if(checkDigit(numScreen))
                    numScreen.append("8");
            }
        });

        button9.setOnClickListener( new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                if(checkZero(numScreen))
                    numScreen.setText("9");
                else if(checkDigit(numScreen))
                    numScreen.append("9");
            }
        });

        backSpace.setOnClickListener( new View.OnClickListener(){
            String num;
            @Override
            public void onClick(View v) {

                if(!checkZero(numScreen)) {
                    num = numScreen.getText().toString();
                    num = num.substring(0, num.length()-1);
                    numScreen.setText(num);
                    if(num.isEmpty())
                        numScreen.setText("0");
                }
            }
        });


        searchButton.setOnClickListener( new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                SearchFragment sf = new SearchFragment();
                sf.setDate(date);
                FragmentManager fm = getFragmentManager();
                fm.beginTransaction()
                        .replace(R.id.contentMain, sf)
                        .addToBackStack(null)
                        .commit();
            }
        });




        description = "New Entry";


        addButton.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                int value = Integer.parseInt(numScreen.getText().toString().trim());

                Log.d("calvalue", value+"");

                Cal_Record newRecord = new Cal_Record(value,date,description);

                Log.d("calrecord", newRecord.getDate() + " " + newRecord.getValue() + " " + newRecord.getDesc());

                CalDbUtils.InsertToDb(db, newRecord);

                Toast.makeText(getContext(), numScreen.getText().toString().concat(" cal added into calories"), Toast.LENGTH_SHORT).show();

                FragmentManager fm = getFragmentManager();
                fm.beginTransaction()
                        .replace(R.id.contentMain, new TrackerFragment())
                        .commit();

            }
        });
        return view;
    }





    public boolean checkZero(TextView numScreen)
    {
        if(numScreen.getText().toString().equals("0"))
        {
            return true;
        }
        else
            return false;
    }

    public boolean checkDigit(TextView numScreen)
    {
        if(numScreen.getText().toString().length() < 10)
        {
            return true;
        }
        else
            return false;
    }

    public void setDate(String date){
        this.date = date;
    }
}