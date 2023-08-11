package com.example.beatyourbestapp.WorkoutScreen;

public class WorkoutItem {
    private String workoutName;
    private String workoutDay;

    public WorkoutItem(String workoutName, String workoutDay, String workoutID) {
        this.workoutName = workoutName;
        this.workoutDay = workoutDay;
        this.workoutId = workoutID;
    }

    public String getWorkoutDay() {
        return workoutDay;
    }

    public String getWorkoutId() {
        return workoutId;
    }

    public void setWorkoutId(String workoutId) {
        this.workoutId = workoutId;
    }

    private String workoutId;

    public WorkoutItem(String workoutName) {
        this.workoutName = workoutName;
    }

    public void setWorkoutName(String workoutName) {
        this.workoutName = workoutName;
    }

    public void setWorkoutDay(String workoutDay) {
        this.workoutDay = workoutDay;
    }

    public WorkoutItem(String workoutName, String workoutDay) {
        this.workoutName = workoutName;
        this.workoutDay = workoutDay;
    }

    public String getWorkoutName() {
        return workoutName;
    }
}
