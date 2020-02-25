package com.example.cinivibe;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder> {

    /** recyclerView: https://developer.android.com/guide/topics/ui/layout/recyclerview,
     *  https://hacksmile.com/how-to-create-android-horizontal-scrolling-recyclerview/
     *
     *  recyclerView onClickListener: https://www.youtube.com/watch?v=69C1ljfDvl0
     **/

        private Context context;
        private ArrayList<MovieRecyclerView> items;
        private OnMovieListener mOnMovieListener;

        public CustomAdapter(Context context, ArrayList<MovieRecyclerView> items, OnMovieListener mOnMovieListener) {
            this.context = context;
            this.items = items;
            this.mOnMovieListener = mOnMovieListener;
        }
        @NonNull
        @Override
        public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            // global mOnMovieListener (instantiated through constructor above) is passed to CustomViewHolder through constructor
            // inflates each individual movie
            return new CustomViewHolder(LayoutInflater.from(context).inflate
                    (R.layout.movies_item, parent, false), mOnMovieListener);
        }
        @Override
        public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
            holder.itemTitle.setText(items.get(position).getTitle());
            holder.itemImage.setImageResource(items.get(position).getImage());
        }
        @Override
        public int getItemCount() {
            return items.size();
        }

        // each individual CustomViewHolder (each recyclerView essentially)
        // each movie will have an OnMovieListener interface attached
        // View.OnClickListener detects click inside CustomViewHolder class
        public class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

            private ImageView itemImage;
            private TextView itemTitle;
            // adds OnMovieListener interface inside CustomViewHolder
            private OnMovieListener onMovieListener;

            // constructor of CustomViewHolder
            public CustomViewHolder(View view, OnMovieListener onMovieListener) {
                super(view);
                itemImage = view.findViewById(R.id.movie_image);
                itemTitle = view.findViewById(R.id.movie_title);
                this.onMovieListener = onMovieListener;

                // onClickListener attached to each view
                view.setOnClickListener(this);
            }

            // once view is clicked, onMovieListener interface calls the onMovieClick method and passes onMovie position
            @Override
            public void onClick(View v) {
                onMovieListener.onMovieClick(getAdapterPosition());
            }
        }

        // has single override method
        // used to detect and interpret click and use method in activity to send position of onClick item
        public interface OnMovieListener {
            void onMovieClick(int position);
        }

    }
