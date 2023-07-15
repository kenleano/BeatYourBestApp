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
import org.json.JSONException;
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
                            JSONArray jsonArray = new JSONArray(response); // Use the response directly as a JSON array

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);

                                String target = jsonObject.getString("target");
                                String equipment = jsonObject.getString("equipment");
                                String imgUrl = jsonObject.getString("gifUrl");
                                String name = jsonObject.getString("name");

                                Exercises exercises = new Exercises(name, imgUrl, equipment, target);
                                exercisesList.add(exercises);
                            }
                            SearchExercisesAdapter adapter = new SearchExercisesAdapter(SearchExercises.this, exercisesList);
                            recyclerView.setAdapter(adapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("JSON Parsing Error", "Error parsing JSON: " + e.getMessage());
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
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

        requestQueue.add(jsonObjectRequest);
    }

}
