package com.example.beatyourbestapp.ExercisesScreen;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.beatyourbestapp.ExerciseDetail;
import com.example.beatyourbestapp.Exercises;
import com.example.beatyourbestapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ExercisesAdapter extends RecyclerView.Adapter<ExercisesAdapter.ViewHolder> {

    private static ArrayList<Exercises> exerciseList;

    private Context context;

    public ExercisesAdapter(ArrayList<Exercises> exerciseList) {
        this.context = context;
        this.exerciseList = exerciseList;
    }

    @NonNull
    @Override
    public ExercisesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.exercise_set_card, parent, false);
        ExercisesAdapter.ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ExercisesAdapter.ViewHolder holder, int position) {
        Exercises itemText = exerciseList.get(position);
        holder.exerciseName.setText(itemText.getExerciseName());

        holder.deleteExerciseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (exerciseList.size() >= 1) {
                    exerciseList.remove(position);
                    notifyDataSetChanged();
                } else {
                    exerciseList.clear();
                    notifyDataSetChanged();
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return exerciseList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView exerciseName;
        ImageButton deleteExerciseButton;
        CardView cardView;
        TextView set;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            exerciseName = itemView.findViewById(R.id.exercise_name);
            deleteExerciseButton = itemView.findViewById(R.id.deleteExercise);
            cardView = itemView.findViewById(R.id.exercise_set_card);
            set = itemView.findViewById(R.id.set);

        }
    }

    public static void addExercise(Exercises exercise) {
        exerciseList.add(exercise);
//        Toast.makeText(null, "Added: " + exercise.getExerciseName() +"ID"+ exercise.getId(), Toast.LENGTH_SHORT).show();

        // notifyItemInserted(exerciseList.size() - 1);
    }
}
