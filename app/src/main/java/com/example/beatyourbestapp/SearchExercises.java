package com.example.beatyourbestapp;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SearchExercises extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
   SearchExercisesAdapter searchExercisesAdapter;

    private SearchView searchView;
    TextView textView;
    private RequestQueue requestQueue;
    private ArrayList<Exercises> exercisesList;
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_exercises);
        searchView = findViewById(R.id.searchBarExercises);

        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filterList(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return false;
            }

        });
        recyclerView = findViewById(R.id.exercisesRecyclerView);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // Initialize the adapter with an empty list for now
        exercisesList = new ArrayList<>();
        searchExercisesAdapter = new SearchExercisesAdapter(this, exercisesList);
        recyclerView.setAdapter(searchExercisesAdapter);

        requestQueue = com.example.popularmovies.VolleySingleton.getInstance(this).getRequestQueue();
        fetchExercises();
    }

    private void fetchExercises() {
        String url = "https://exercisedb.p.rapidapi.com/exercises";

        JsonArrayRequest jsonArrayRequest  = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {

                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonObject = response.getJSONObject(i);
                                String target = jsonObject.getString("target");
                                String equipment = jsonObject.getString("equipment");
                                String imgUrl = jsonObject.getString("gifUrl");
                                String name = jsonObject.getString("name");
                                //String instructions = jsonObject.getString("instructions");

                                Exercises exercises = new Exercises(name, imgUrl, equipment, target);
                                exercisesList.add(exercises);

                            }
                            searchExercisesAdapter = new SearchExercisesAdapter(SearchExercises.this, exercisesList);
                            recyclerView.setAdapter(searchExercisesAdapter);

                            for (Exercises exercises : exercisesList) {
                                Log.d("Exercises List Size", "Size: " + exercises.toString());
                            }



                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("JSON Parsing Error", "Error parsing JSON: " + e.getMessage());
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                textView.setText(error.toString());
                String errorMessage = error.getMessage(); // Get the error message
                Log.e("Error", errorMessage);
                Toast.makeText(SearchExercises.this, "Error fetching data", Toast.LENGTH_LONG).show();
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

        requestQueue.add(jsonArrayRequest);
    }

    private void filterList(String text) {
        ArrayList<Exercises> filteredList = new ArrayList<>();
        for (Exercises exercise : exercisesList) {
            if (exercise.getExerciseName().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(exercise);
            }
        }

        if(filteredList.isEmpty()){
            Toast.makeText(this, "No exercises found", Toast.LENGTH_SHORT).show();
        } else {
            searchExercisesAdapter = new SearchExercisesAdapter(SearchExercises.this, filteredList);
            recyclerView.setAdapter(searchExercisesAdapter);
            //Toast.makeText(this, "Exercises found", Toast.LENGTH_SHORT).show();
        }


    }
}
