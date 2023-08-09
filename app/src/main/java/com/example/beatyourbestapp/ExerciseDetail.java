package com.example.beatyourbestapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class ExerciseDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_detail);

        ImageView image = findViewById(R.id.detail_exercise_image);
        TextView target = findViewById(R.id.detail_exercise_target);
        TextView name = findViewById(R.id.detail_exercise_name);
        TextView equipment = findViewById(R.id.detail_exercise_equipment);

        Bundle bundle = getIntent().getExtras();

        String dName = bundle.getString("name");
        String dTarget = bundle.getString("target");
        String dEquipment = bundle.getString("equipment");
        String dGifUrl = bundle.getString("gif");

        Picasso.get().load(dGifUrl).into(image);
        equipment.setText(dEquipment);
        target.setText(dTarget);
        name.setText(dName);

    }

    public void addExercise(View view) {
        Intent intent = new Intent(this, ExercisesLayout.class);
        startActivity(intent);
    }
}