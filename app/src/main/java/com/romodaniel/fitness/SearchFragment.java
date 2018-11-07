package com.romodaniel.fitness;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.romodaniel.fitness.Utilities.FoodItemAdapter;
import com.romodaniel.fitness.Utilities.FoodItems;
import com.romodaniel.fitness.Utilities.NetworkUtils;

import org.json.JSONException;

import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Daniel on 7/29/2017.
 */

public class SearchFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    Button search, done;
    EditText q;
    ProgressBar progress;
    String query;
    String date;
    private RecyclerView fooditemsrv;

    private final String TAG = "searchfragment";

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.cal_search, container, false);

        fooditemsrv = (RecyclerView) view.findViewById(R.id.fooditemrv);
        search = (Button) view.findViewById(R.id.searchButton);
        done = (Button) view.findViewById(R.id.doneButton);
        q = (EditText) view.findViewById(R.id.search);
        progress = (ProgressBar) view.findViewById(R.id.progressBar);

        // Set focus to search bar.
        q.requestFocus();

        // Setup for rv
        fooditemsrv.setLayoutManager(new LinearLayoutManager(this.getActivity()));

        // If search button clicked, then grab edittext's text and set to 'query'. Then call fetch async
        // task and search using 'query'.
        search.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                // get edit text and use it to search
                query= q.getText().toString();
                FetchSearchedItem fetch = new FetchSearchedItem();
                fetch.execute();

                // Hides keyboard
                InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        });

        // If done button clicked, go back to TrackerFragment.
        done.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // Go back to TrackerFragment
                Fragment fragment = new TrackerFragment();
                FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.contentMain, fragment);
                ft.commit();
            }
        });
        return view;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        parent.getItemAtPosition(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    // AsyncTask for calling network utils and parse on API JSON reponse. Then return data to
    // rv 'fooditemsrv'.
    private class FetchSearchedItem extends AsyncTask<URL, Void, ArrayList<FoodItems>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress.setVisibility(View.VISIBLE);
        }

        @Override
        protected ArrayList<FoodItems> doInBackground(URL... strings) {
            ArrayList<FoodItems> result = null;

            URL url = NetworkUtils.buildUrl(query);
            Log.d(TAG, "url: " + url.toString());

            try {
                String json =  NetworkUtils.getResponseFromHttpUrl(url);
                result = NetworkUtils.parseJSON(json);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e){
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(final ArrayList<FoodItems> newsData) {
            super.onPostExecute(newsData);
            progress.setVisibility(View.GONE);
            if(newsData!=null){
                FoodItemAdapter adapter = new FoodItemAdapter(newsData, date, new FoodItemAdapter.ItemClickListener() {
                    @Override
                    public void onItemClick(int clickedItemIndex) {
                    }
                });
                fooditemsrv.setAdapter(adapter);
            }
        }
    }
    public void setDate(String date){
        this.date = date;
    }
}