package com.example.tmdbapi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<MovieModel> movieList;
    private MovieAdapterListener listener;

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView tvname, tvtahun, tvdate;
        public ImageView ivMoviePoster;

        public MyViewHolder(View view){
            super(view);
            tvname = view.findViewById(R.id.tvname);
            tvtahun = view.findViewById(R.id.tvtahun);
            tvdate = view.findViewById(R.id.tvgenre);
            ivMoviePoster = view.findViewById(R.id.ivMoviePoster);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onContactSelected(movieList.get(getAdapterPosition()));
                }
            });
            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    if (listener != null){
                        int pos = getAdapterPosition();

                        if (pos != RecyclerView.NO_POSITION){
                            listener.onItemLongClick(pos);
                        }
                    }




                    return true;
                }

            });

        }
    }

public MovieAdapter(Context context, List<MovieModel> movieList, MovieAdapterListener listener){
        this.context = context;
        this.movieList = movieList;
        this.listener = listener;
}

public MovieAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout,parent,false);
        return new MyViewHolder(itemView);
}

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MyViewHolder myViewHolder = (MyViewHolder) holder;
        final MovieModel movie = this.movieList.get(position);
        myViewHolder.tvname.setText(movie.getMovieName());
        myViewHolder.tvdate.setText(movie.getReleaseDate());
        Picasso.get().
                load("https://image.tmdb.org/t/p/w500" +movie.getPosterPath())
                .placeholder(R.drawable.ic_launcher_background).
        into(myViewHolder.ivMoviePoster);

    }

public int getItemCount(){return this.movieList.size();}
    public interface MovieAdapterListener {
        void onContactSelected(MovieModel contact);
        void onItemLongClick(int position);

    }
}
