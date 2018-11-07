package com.romodaniel.fitness.Utilities;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.romodaniel.fitness.R;
import com.romodaniel.fitness.TrackerFragment;
import com.romodaniel.fitness.data.CalDbUtils;
import com.romodaniel.fitness.data.Cal_Record;

import java.util.ArrayList;

/**
 * Created by Daniel on 7/27/2017.
 */

public class FoodItemAdapter extends RecyclerView.Adapter<FoodItemAdapter.ItemHolder>{

    private ArrayList<FoodItems> data;
    ItemClickListener listener;
    String date;
    //Context c;


    public FoodItemAdapter(ArrayList<FoodItems> data, String date, ItemClickListener listener){
        this.data = data;
        this.listener = listener;
        this.date = date;
    }

    public interface ItemClickListener {
        void onItemClick(int clickedItemIndex);
    }

    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(R.layout.calorieitem, parent, shouldAttachToParentImmediately);
        ItemHolder holder = new ItemHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(ItemHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView foodname;
        TextView foodcalorie;

        ItemHolder(View view){
            super(view);
            foodname = (TextView)view.findViewById(R.id.foodName);
            foodcalorie = (TextView)view.findViewById(R.id.foodCalorie);

            view.setOnClickListener(this);
        }

        public void bind(int pos){
            FoodItems repo = data.get(pos);
            foodname.setText(repo.getItem_name());
            foodcalorie.setText(Math.round(Double.parseDouble(repo.getCalories()))+" cal");
        }

        @Override
        public void onClick(View v) {
            int pos = getAdapterPosition();
            listener.onItemClick(pos);
            String delims = " ";
            String[] tokens = foodcalorie.getText().toString().split(delims);

            SQLiteDatabase db = TrackerFragment.getdb();
            Cal_Record cr = new Cal_Record(Integer.parseInt(tokens[0]),date, foodcalorie.getText().toString());
            CalDbUtils.InsertToDb(db,cr);

            Toast.makeText(v.getContext(), "Added " + tokens[0] + " calories from " + foodname.getText().toString(), Toast.LENGTH_SHORT).show();
        }
    }

}