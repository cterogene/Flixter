package com.example.flixter.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.flixter.DetailActivity;
import com.example.flixter.R;
import com.example.flixter.models.Movie;

import org.parceler.Parcels;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder>{

    Context context;
    List<Movie> movies;

    public MovieAdapter(Context context, List<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }

    // Usually involves inflating a layout from XML and returning the holder

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View movieView =  LayoutInflater.from(context).inflate(R.layout.item_movie,parent, false);
        return  new ViewHolder(movieView);


    }

    //Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    // Get te movie at the passed position
        Movie movie = movies.get(position);

    //Bind the movie data into the vh
        holder.bind(movie);


    }
    // Return the total count of items in the List
    @Override
    public int getItemCount() {
       return movies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        RelativeLayout container;
        TextView tvTitle;
        TextView tvOverview;
        ImageView ivPoster;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvOverview = itemView.findViewById(R.id.tvOverview);
            ivPoster =  itemView.findViewById(R.id.ivPoster);
            container = itemView.findViewById(R.id.container);
        }

        public void bind(final Movie movie) {

            tvTitle.setText(movie.getTitle());
            tvOverview.setText(movie.getOverview());
            String imagUrl;
            // If phone is in landscape
            if(context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){

                //then imagUrl = back_drop_image
                imagUrl = movie.getBackdropPath();
            }
            else{
                // else imageUrl = poster_image
                 imagUrl = movie.getPosterPath();
            }



            Glide.with(context).load(imagUrl).into(ivPoster);

            //1. Register click Listener on the whole row



            container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //2. Navigate to a new activity on tap
                    Intent i = new Intent(context, DetailActivity.class);
                    i.putExtra("movie", Parcels.wrap(movie));
                    context.startActivity(i);
                }
            });

        }
    }
}
