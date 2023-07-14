package com.example.beatyourbestapp;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SearchExercises extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;

TextView textView;
    private RequestQueue requestQueue;
    private ArrayList<Exercises> exercisesList;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_exercises);
        textView = findViewById(R.id.test);
        recyclerView = findViewById(R.id.exercisesRecyclerView);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        requestQueue = com.example.popularmovies.VolleySingleton.getInstance(this).getRequestQueue();
        exercisesList = new ArrayList<>();
       fetchExercises();
    }

    private void fetchExercises() {
        String url = "https://exercisedb.p.rapidapi.com/exercises";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("results");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String name = jsonObject.getString("name");
                                String equipment = jsonObject.getString("equipment");
                                String bodyPart = jsonObject.getString("bodyPart");
                                String imgUrl = jsonObject.getString("gifUrl");

                                Exercises exercises = new Exercises(name, imgUrl, equipment, bodyPart);
                                exercisesList.add(exercises);
                            }

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (exercisesList.isEmpty()) {
                                        textView.setText("Empty");
                                        Toast.makeText(SearchExercises.this, "No data available", Toast.LENGTH_SHORT).show();
                                    } else {
                                        textView.setText("Data");
                                        SearchExercisesAdapter adapter = new SearchExercisesAdapter(SearchExercises.this, exercisesList);
                                        recyclerView.setAdapter(adapter);
                                    }
                                }
                            });
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                textView.setText("API Error: " + error.toString());
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("X-RapidAPI-Key", "171cde7c14mshd589d484a1b782ep142cafjsn37c1965fdf78");
                headers.put("X-RapidAPI-Host", "exercisedb.p.rapidapi.com");
                return headers;
            }
        };

        requestQueue.add(jsonObjectRequest);
    }

}
