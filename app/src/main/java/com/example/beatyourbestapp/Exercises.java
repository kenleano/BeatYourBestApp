package com.example.beatyourbestapp;

public class Exercises {
    private String exerciseName;
    private String bodyPart;
    private String equipment;
    private String gifUrl;
    private String exerciseTarget;
    private int id;

    public Exercises(String exerciseName, String bodyPart, String equipment, String gifUrl, String exerciseTarget, int id) {
        this.exerciseName = exerciseName;
        this.bodyPart = bodyPart;
        this.equipment = equipment;
        this.gifUrl = gifUrl;
        this.exerciseTarget = exerciseTarget;
        this.id = id;
    }

    public Exercises(String exerciseName, String gifUrl, String equipment, String bodyPart) {
        this.exerciseName = exerciseName;
        this.gifUrl = gifUrl;
        this.equipment = equipment;
        this.bodyPart = bodyPart;
    }

    public Exercises(String name, String equipment, String bodyPart) {
    }

    public String getExerciseName() {
        return exerciseName;
    }

    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
    }

    public String getBodyPart() {
        return bodyPart;
    }

    public void setBodyPart(String bodyPart) {
        this.bodyPart = bodyPart;
    }

    public String getEquipment() {
        return equipment;
    }

    public void setEquipment(String equipment) {
        this.equipment = equipment;
    }

    public String getGifUrl() {
        return gifUrl;
    }

    public void setGifUrl(String gifUrl) {
        this.gifUrl = gifUrl;
    }

    public String getExerciseTarget() {
        return exerciseTarget;
    }

    public void setExerciseTarget(String exerciseTarget) {
        this.exerciseTarget = exerciseTarget;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
