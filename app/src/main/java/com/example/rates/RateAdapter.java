package com.example.rates;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rates.model.Rate;

import java.util.ArrayList;


public class RateAdapter extends  RecyclerView.Adapter<RateAdapter.ViewHolder> {

    private ArrayList<Rate> list ;

    public RateAdapter(ArrayList<Rate> list){

        this.list= list;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rate, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

      //  Log.e("Main","-------------------------"+list.get(position));

        holder.rate_name.setText(list.get(position).getId());
        holder.rate_value.setText(list.get(position).getValue());
        if(Double.valueOf(list.get(position).getValue()) >
                Double.valueOf(list.get(position).getLast_value()) &&
                Double.valueOf(list.get(position).getValue()) < Double.valueOf(list.get(position).getLast_value()) *2 ){

            holder.rate_value.setTextColor((Color.parseColor("#388E3C")));
        }
         if (Double.valueOf(list.get(position).getValue()) < Double.valueOf(list.get(position).getLast_value())
                 &&
                 Double.valueOf(list.get(position).getValue()) > Double.valueOf(list.get(position).getLast_value()) *2){
            holder.rate_value.setTextColor((Color.parseColor("#F44336")));
        }

    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView rate_name, rate_value;
        public ViewHolder(View view){
            super(view);
            rate_name = view.findViewById(R.id.rate_name);
            rate_value = view.findViewById(R.id.rate_value);
        }
    }
}
