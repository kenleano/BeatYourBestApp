package com.example.beatyourbestapp.WorkoutScreen;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.beatyourbestapp.R;

import java.util.List;

public class WorkoutAdapter extends RecyclerView.Adapter<WorkoutAdapter.WorkoutViewHolder> {
    private List<WorkoutItem> workoutList;
    private WorkoutFragment workoutFragment;
    private OnItemClickListener clickListener;

    public WorkoutAdapter(List<WorkoutItem> workoutList, WorkoutFragment workoutFragment) {
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
