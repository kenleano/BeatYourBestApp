package com.example.beatyourbestapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SearchExercisesAdapter extends RecyclerView.Adapter<SearchExercisesAdapter.ViewHolder> {
    public static ArrayList<Exercises> exercisesList;
    private ArrayList<Exercises> filteredList;
    private Context context;

    public SearchExercisesAdapter(Context context, ArrayList<Exercises> exercisesList) {
        this.context = context;
        this.exercisesList = exercisesList;
        this.filteredList = new ArrayList<>(); // Initialize filteredList
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.exercises_card, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Exercises exercises = exercisesList.get(position);
        holder.name.setText(exercises.getExerciseName() + " (" + exercises.getId() + ")");
        holder.equipment.setText(exercises.getEquipment());
        holder.target.setText(exercises.getExerciseTarget());

        Picasso.get().load(exercises.getGifUrl()).into(holder.image);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context, "Clicked", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, ExerciseDetail.class);
                Bundle bundle = new Bundle();
                bundle.putString("name", exercises.getExerciseName());
                bundle.putString("equipment", exercises.getEquipment());
                bundle.putString("target", exercises.getExerciseTarget());
                bundle.putString("gif", exercises.getGifUrl());
                bundle.putInt("id", exercises.getId());

                intent.putExtras(bundle);
                context.startActivity(intent);

            }
        });
    }
    @Override
    public int getItemCount() {
        return exercisesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView name, equipment, target;

        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.exercise_image);
            name = itemView.findViewById(R.id.exercise_name);
            equipment = itemView.findViewById(R.id.exercise_equipment);
            target = itemView.findViewById(R.id.exercise_target);
           cardView = itemView.findViewById(R.id.exercises_card);

        }
    }

    public void setFilteredList(ArrayList<Exercises> filteredList) {
        this.filteredList = filteredList;
        notifyDataSetChanged();
    }
}
