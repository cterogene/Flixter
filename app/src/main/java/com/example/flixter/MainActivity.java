package com.example.flixter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import android.os.Bundle;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;


import com.codepath.asynchttpclient.AsyncHttpClient;
import com.example.flixter.adapters.MovieAdapter;
import com.example.flixter.models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Headers;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";
    public static final String now_Playing_Url = "https://api.themoviedb.org/3/movie/now_playing?api_key=60a82fccdbb2f79a2d57ba56e2ad2bed";
    List<Movie> movies;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView rvmovies = findViewById(R.id.rvMovies);
         movies = new ArrayList<>();
        // Create the adapter
       final MovieAdapter movieAdapter = new MovieAdapter(this, movies);
        // Set the adapter on the recycler view
        rvmovies.setAdapter(movieAdapter);
        // Set a layout message on the recycler view
        rvmovies.setLayoutManager(new LinearLayoutManager(this));

        AsyncHttpClient client = new AsyncHttpClient();

        client.get(now_Playing_Url, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {

                Log.d(TAG, "onSuccess");
                JSONObject jsonObject = json.jsonObject;
                try{
                    JSONArray results = jsonObject.getJSONArray("results");
                    Log.i(TAG, "Results: " + results.toString());
                    movies.addAll(Movie.fromJsonArray(results));
                    movieAdapter.notifyDataSetChanged();
                    Log.i(TAG, "Movies: " + movies.size());
                }catch (JSONException e) {
                    Log.e(TAG,"Hit json exception",e);
                }
            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.d(TAG, "onFailure");
            }
        });


    }
}
