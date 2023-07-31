package com.example.beatyourbestapp.WorkoutScreen;

public class WorkoutItem {
    private String workoutName;
    private String workoutDay;

    public WorkoutItem(String workoutName) {
        this.workoutName = workoutName;
    }


    public WorkoutItem(String workoutName, String workoutDay) {
        this.workoutName = workoutName;
        this.workoutDay = workoutDay;
    }

    public String getWorkoutName() {
        return workoutName;
    }
}
