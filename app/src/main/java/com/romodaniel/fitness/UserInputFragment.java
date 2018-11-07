package com.romodaniel.fitness;


import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.romodaniel.fitness.data.DBHelper;
import com.romodaniel.fitness.data.User;
import com.romodaniel.fitness.data.UserDbUtils;

import static com.google.android.gms.wearable.DataMap.TAG;



public class UserInputFragment extends Fragment {

    private SQLiteDatabase db;
    private DBHelper helper;
    private Button submit;
    private EditText fName;
    private EditText lName;
    private EditText gender;
    private EditText foot;
    private EditText inch;
    private EditText weight;

    private  UserInputFragment userInputFragment;
    public UserInputFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userInputFragment = this;


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_user_input, container, false);

        submit = (Button)  view.findViewById(R.id.submit);
        fName = (EditText) view.findViewById(R.id.f_name);
        lName = (EditText) view.findViewById(R.id.l_name);
        gender= (EditText) view.findViewById(R.id.gender);
        weight= (EditText) view.findViewById(R.id.weight);
        foot=   (EditText) view.findViewById(R.id.foot);
        inch=   (EditText) view.findViewById(R.id.inches);


        helper = new DBHelper(getActivity());
        db = helper.getWritableDatabase();


        submit.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                Log.d("buttonPressed","pressed");
                Log.d("buttonPressed", fName.getText().toString());

                if(fName.getText().toString().trim().isEmpty()
                        ||lName.getText().toString().trim().isEmpty()
                        ||gender.getText().toString().trim().isEmpty()
                        ||weight.getText().toString().trim().isEmpty()
                        ||foot.getText().toString().trim().isEmpty()
                        ||inch.getText().toString().trim().isEmpty()){


                    String warning = "please fill out EVERYTHING";
                    Toast toast = Toast.makeText(getActivity(), warning, Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.BOTTOM|Gravity.CENTER, 0, 100);
                    toast.show();

                }
                else{
                    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putBoolean("isfirst", false);
                    editor.commit();

                    int inches = Integer.parseInt(foot.getText().toString().trim()) * 12 + Integer.parseInt(inch.getText().toString().trim());
                    int lbs = Integer.parseInt(weight.getText().toString().trim());

                    User user = new User(cap(fName.getText().toString().trim()),cap(lName.getText().toString().trim()),cap(gender.getText().toString()), inches, lbs);

                    Log.d(TAG, "userInfo: "+ user.toString());
                    UserDbUtils.InsertToDb(db,user);


                    Intent intent = new Intent(getActivity().getApplication(), MainActivity.class);
                    startActivity(intent);

                }



            }
        });
        return view;

    }

    public String cap(String s){

        if(s.length()>1){
            return s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
        }else {
            return s.toUpperCase();
        }

    }


}
