package com.example.beatyourbestapp;

import java.util.ArrayList;

public class WorkoutHistory {
    private String workoutName;
    private ArrayList<Exercises> exerciseName;
    private int sets;
    private int reps;
    private String dateTime;

    public WorkoutHistory() {

    }

    public String getWorkoutName() {
        return workoutName;
    }

    public void setWorkoutName(String workoutName) {
        this.workoutName = workoutName;
    }

    public ArrayList<Exercises> getExerciseName() {
        return exerciseName;
    }

    public void setExerciseName(ArrayList<Exercises> exerciseName) {
        this.exerciseName = exerciseName;
    }

    public int getSets() {
        return sets;
    }

    public void setSets(int sets) {
        this.sets = sets;
    }

    public int getReps() {
        return reps;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public WorkoutHistory(String workoutName, ArrayList exerciseName, int sets, int reps, String dateTime) {
        this.workoutName = workoutName;
        this.exerciseName = exerciseName;
        this.sets = sets;
        this.reps = reps;
        this.dateTime = dateTime;
    }
// Constructors, getters, and setters
    // ...
}
