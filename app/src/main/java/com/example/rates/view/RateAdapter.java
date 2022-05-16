package com.example.rates.view;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.rates.R;
import com.example.rates.model.data.local.RateDAO;
import com.example.rates.model.entity.Rate;
import com.example.rates.viewmodel.RateVM;

import java.util.ArrayList;
import java.util.Locale;


public class RateAdapter extends RecyclerView.Adapter<RateAdapter.ViewHolder> {

    private ArrayList<Rate> list;


    public RateAdapter(ArrayList<Rate> list) {

        this.list = list;

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
        int id = context.getResources().getIdentifier((list.get(position).getId()).toLowerCase(Locale.ROOT).substring(0, 2), "drawable", context.getPackageName());
        holder.img_conty.setImageResource(id);

        holder.rate = list.get(position);
        holder.rate_code.setText(list.get(position).getId());
        holder.rate_name.setText(list.get(position).getName());
        holder.rate_value.setText(list.get(position).getValue());

        if(list.get(position).getFavourite() == true){

            holder.img_fav.setImageResource(R.drawable.ic_bookmark_ucc);
        }
        else {
            holder.img_fav.setImageResource(R.drawable.ic_bookmark);

        }

//        if(Double.valueOf(list.get(position).getValue()) >
//                Double.valueOf(list.get(position).getLast_value()) &&
//                Double.valueOf(list.get(position).getValue()) < Double.valueOf(list.get(position).getLast_value()) *2 ){
//
//            holder.rate_value.setTextColor((Color.parseColor("#388E3C")));
//        }
//         if (Double.valueOf(list.get(position).getValue()) < Double.valueOf(list.get(position).getLast_value())
//                 &&
//                 Double.valueOf(list.get(position).getValue()) < Double.valueOf(list.get(position).getLast_value()) *2){
//            holder.rate_value.setTextColor((Color.parseColor("#F44336")));
//        }


    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private RateVM rateVM;
        private final TextView rate_name;
        private final TextView rate_code;
        private final TextView rate_value;
        private final ImageView img_conty;
        private ImageView img_fav;
        Rate rate;
        public ViewHolder(View view) {
            super(view);
            rate_name = view.findViewById(R.id.rate_name);
            rate_code = view.findViewById(R.id.rate_code);
            rate_value = view.findViewById(R.id.rate_value);
            img_conty = view.findViewById(R.id.img);
            img_fav = itemView.findViewById(R.id.fav_btn);
            rateVM = new RateVM((MainActivity) view.getContext());

            itemView.findViewById(R.id.fav_btn).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(rate.getFavourite().equals(false)){
                        rate.setFavourite(true);
                        rateVM.updateRate(rate);
                        img_fav.setImageResource(R.drawable.ic_bookmark_ucc);
                    }
                    else {
                        rate.setFavourite(false);
                        rateVM.updateRate(rate);
                        img_fav.setImageResource(R.drawable.ic_bookmark);
                    }






                }
            });
        }


    }


    // Swap itemA with itemB
    public void swapItems(int itemAIndex, int itemBIndex) {
        //make sure to check if dataset is null and if itemA and itemB are valid indexes.
        Rate itemA = list.get(itemAIndex);
        Rate itemB = list.get(itemBIndex);
        list.set(itemAIndex, itemB);
        list.set(itemBIndex, itemA);

        notifyDataSetChanged(); //This will trigger onBindViewHolder method from the adapter.
    }


}
