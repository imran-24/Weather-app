package com.example.weather_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerWeatherAdapter  extends RecyclerView.Adapter<RecyclerWeatherAdapter.ViewHolder> {
    Context context;
    ArrayList<Weather> weatherArrayList;
    public RecyclerWeatherAdapter(Context context, ArrayList<Weather> weatherArrayList){
        this.context = context;
        this.weatherArrayList = weatherArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_layout,parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.temperature.setText(weatherArrayList.get(position).temp);
        holder.imageView.setImageResource(weatherArrayList.get(position).image);
        holder.weather.setText(weatherArrayList.get(position).weather);
        holder.Date.setText(weatherArrayList.get(position).date);
    }

    @Override
    public int getItemCount() {
        return weatherArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView Date, temperature,weather;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            weather = itemView.findViewById(R.id.weather);
            temperature = itemView.findViewById(R.id.temperature);
            imageView = itemView.findViewById(R.id.imageView);
            Date = itemView.findViewById(R.id.date);
        }
    }
}
