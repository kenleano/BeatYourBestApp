package com.example.beatyourbestapp.ExercisesScreen;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ExercisesAdapter extends RecyclerView.Adapter<ExercisesAdapter.ViewHolder> {

    private static ArrayList<Exercises> exerciseList;

    private Context context;

    public ExercisesAdapter(Context context, ArrayList<Exercises> exerciseList) {
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
    public void onBindViewHolder(@NonNull ExercisesAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Exercises itemText = exerciseList.get(position);
        holder.exerciseName.setText(itemText.getExerciseName());
        holder.set_reps.setText(itemText.getSetReps());

        holder.deleteExerciseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Exercises exercise = exerciseList.get(position);
                String exerciseName = exercise.getExerciseName();

                SharedPreferences sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                String selectedWorkoutName = sharedPreferences.getString("selectedWorkoutName", "");


                exerciseList.remove(position);


                DatabaseReference workoutsRef = FirebaseDatabase.getInstance().getReference("Workouts");
                workoutsRef.child(selectedWorkoutName).child("Exercises").child(exerciseName).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
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
        holder.finishSetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Exercises exercise = exerciseList.get(position);
                String exerciseName = exercise.getExerciseName();
                String weight = holder.editWeight.getText().toString();
                String reps = holder.editReps.getText().toString();
                String set = holder.set.getText().toString();


                SharedPreferences sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                String selectedWorkoutName = sharedPreferences.getString("selectedWorkoutName", "");
                DatabaseReference workoutsRef = FirebaseDatabase.getInstance().getReference("Workouts");
                workoutsRef.child(selectedWorkoutName).child("Exercises").child(exerciseName).child("Sets").child("Weight").setValue(weight);
                workoutsRef.child(selectedWorkoutName).child("Exercises").child(exerciseName).child("Sets").child("Reps").setValue(reps);

                Toast.makeText(context, "Set " + set + " saved", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return exerciseList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView exerciseName;
        EditText editWeight, editReps;
        TextView set_reps;
        ImageButton deleteExerciseButton;
        ImageButton finishSetBtn;
        CardView cardView;
        TextView set;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            exerciseName = itemView.findViewById(R.id.exercise_name);
            deleteExerciseButton = itemView.findViewById(R.id.deleteExercise);
            cardView = itemView.findViewById(R.id.exercise_set_card);
            set = itemView.findViewById(R.id.set);
            finishSetBtn = itemView.findViewById(R.id.finishSetBtn);
            editWeight = itemView.findViewById(R.id.editWeight);
            editReps = itemView.findViewById(R.id.editReps);
            set_reps   = itemView.findViewById(R.id.set_reps);
            exerciseName.setSelected(true);
        }
    }

    public static void addExercise(Exercises exercise) {

        exerciseList.add(exercise);
//        Toast.makeText(null, "Added: " + exercise.getExerciseName() +"ID"+ exercise.getId(), Toast.LENGTH_SHORT).show();

        // notifyItemInserted(exerciseList.size() - 1);
    }
}
