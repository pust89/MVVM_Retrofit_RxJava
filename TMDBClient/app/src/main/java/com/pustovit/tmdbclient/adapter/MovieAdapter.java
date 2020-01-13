package com.pustovit.tmdbclient.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pustovit.tmdbclient.R;
import com.pustovit.tmdbclient.model.Movie;
import com.pustovit.tmdbclient.view.MovieActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Pustovit Vladimir on 08.01.2020.
 * vovapust1989@gmail.com
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieVH> {
    private Context mContext;
    private List<Movie> mMovies;


    public MovieAdapter(Context context, List<Movie> movies) {
        mContext = context;
        mMovies = movies;
    }

    @NonNull
    @Override
    public MovieVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_list_item, parent, false);
        return new MovieVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieVH holder, int position) {
        Movie movie = mMovies.get(position);
        holder.tvRating.setText(String.valueOf(movie.getPopularity()));
        holder.tvTitle.setText(movie.getTitle());
        Picasso.get()
                .load( "https://image.tmdb.org/t/p/w500/"+movie.getPosterPath())
                .error(R.drawable.ic_placeholder_24dp)
                .into(holder.ivMovie);
    }

    @Override
    public int getItemCount() {
        return mMovies.size();
    }


     class MovieVH extends RecyclerView.ViewHolder{

       final ImageView ivMovie;
       final TextView tvTitle;
       final TextView tvRating;

         MovieVH(@NonNull View itemView) {
            super(itemView);
            ivMovie = itemView.findViewById(R.id.ivMovie);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvRating = itemView.findViewById(R.id.tvRating);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(position!= RecyclerView.NO_POSITION){
                        Movie movie = mMovies.get(position);
                        Intent intent = new Intent(mContext, MovieActivity.class);
                        intent.putExtra(Movie.class.getSimpleName(), movie);
                        mContext.startActivity(intent);
                    }
                }
            });
        }

    }


}
