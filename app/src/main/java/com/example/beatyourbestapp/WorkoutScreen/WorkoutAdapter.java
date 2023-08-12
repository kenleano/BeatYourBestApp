package com.example.beatyourbestapp.WorkoutScreen;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.beatyourbestapp.ExerciseDetail;
import com.example.beatyourbestapp.Exercises;
import com.example.beatyourbestapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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
        holder.workoutDay.setText(workoutItem.getWorkoutDay());

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

        holder.deleteWorkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String workoutId = workoutList.get(position).getWorkoutId();
                String workoutName = workoutList.get(position).getWorkoutName();

                workoutList.remove(position);
                notifyDataSetChanged();

                DatabaseReference workoutsRef = FirebaseDatabase.getInstance().getReference("Workouts");
                workoutsRef.child(workoutName).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            // Workout deleted successfully
                            Log.d(TAG, "Workout deleted successfully");
                        } else {
                            // Error occurred while deleting the workout
                            Log.e(TAG, "Error deleting workout: " + task.getException().getMessage());
                        }
                    }
                });

                    notifyDataSetChanged();


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
        public TextView workoutNameTextView, workoutDay;
        public View deleteWorkout;
        public ImageButton deleteWorkoutButton;

        public WorkoutViewHolder(@NonNull View itemView) {
            super(itemView);
            workoutNameTextView = itemView.findViewById(R.id.workout_title);
            workoutDay = itemView.findViewById(R.id.workout_day);
            deleteWorkoutButton = itemView.findViewById(R.id.deleteWorkout);

        }
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        clickListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}
