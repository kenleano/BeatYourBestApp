package com.example.beatyourbestapp.WorkoutScreen;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.beatyourbestapp.ExerciseDetail;
import com.example.beatyourbestapp.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class WorkoutAdapter extends RecyclerView.Adapter<WorkoutAdapter.WorkoutViewHolder>  {
    private ArrayList<WorkoutItem> workoutList;
    private WorkoutFragment workoutFragment;
    private OnItemClickListener clickListener;

    public WorkoutAdapter(ArrayList<WorkoutItem> workoutList, WorkoutFragment workoutFragment) {
        this.workoutList = workoutList;
        this.workoutFragment = workoutFragment;
    }

    @NonNull
    @Override
    public WorkoutViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.workout_layout, parent, false);
        return new WorkoutViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WorkoutViewHolder holder, int position) {
        WorkoutItem workoutItem = workoutList.get(position);
        holder.workoutNameTextView.setText(workoutItem.getWorkoutName());
//        holder.cardView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //Toast.makeText(context, "Clicked", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(context, ExerciseDetail.class);
//                Bundle bundle = new Bundle();
//
//                bundle.putString("name", exercises.getExerciseName());
//                bundle.putString("equipment", exercises.getEquipment());
//                bundle.putString("target", exercises.getExerciseTarget());
//                bundle.putString("gif", exercises.getGifUrl());
//
//                intent.putExtras(bundle);
//                context.startActivity(intent);
//
//            }
//        });
        // Set click listener for the card view
        holder.itemView.setOnClickListener(v -> {
            if (clickListener != null) {
                clickListener.onItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return workoutList.size();
    }

    public void addWorkout(WorkoutItem newWorkoutItem) {
        workoutList.add(newWorkoutItem);
        notifyItemInserted(workoutList.size() - 1);

    }

    public static class WorkoutViewHolder extends RecyclerView.ViewHolder {
        public TextView workoutNameTextView;

        public WorkoutViewHolder(@NonNull View itemView) {
            super(itemView);
            workoutNameTextView = itemView.findViewById(R.id.workout_title);
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        clickListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}
