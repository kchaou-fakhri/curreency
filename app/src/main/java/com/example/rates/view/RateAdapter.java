package com.example.rates.view;

import android.content.Context;
import android.graphics.Color;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rates.R;
import com.example.rates.model.entity.Rate;

import java.util.ArrayList;
import java.util.Locale;


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
        Context context = holder.img_conty.getContext();
        int id = context.getResources().getIdentifier((list.get(position).getId()).toLowerCase(Locale.ROOT).substring(0,2), "drawable", context.getPackageName());
        holder.img_conty.setImageResource(id);

        holder.rate_code.setText(list.get(position).getId());
        holder.rate_name.setText(list.get(position).getName());
        holder.rate_value.setText(list.get(position).getValue());

        if(Double.valueOf(list.get(position).getValue()) >
                Double.valueOf(list.get(position).getLast_value()) &&
                Double.valueOf(list.get(position).getValue()) < Double.valueOf(list.get(position).getLast_value()) *2 ){

            holder.rate_value.setTextColor((Color.parseColor("#388E3C")));
        }
         if (Double.valueOf(list.get(position).getValue()) < Double.valueOf(list.get(position).getLast_value())
                 &&
                 Double.valueOf(list.get(position).getValue()) < Double.valueOf(list.get(position).getLast_value()) *2){
            holder.rate_value.setTextColor((Color.parseColor("#F44336")));
        }


    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView rate_name, rate_code, rate_value;
        private ImageView img_conty;
        public ViewHolder(View view){
            super(view);
            rate_name = view.findViewById(R.id.rate_name);
            rate_code = view.findViewById(R.id.rate_code);
            rate_value = view.findViewById(R.id.rate_value);
            img_conty = view.findViewById(R.id.img);
        }
    }



}
