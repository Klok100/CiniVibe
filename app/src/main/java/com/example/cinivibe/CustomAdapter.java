package com.example.cinivibe;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder> implements Filterable {
    /** recyclerView: https://developer.android.com/guide/topics/ui/layout/recyclerview,
     *  https://hacksmile.com/how-to-create-android-horizontal-scrolling-recyclerview/
     *
     *  recyclerView onClickListener: https://www.youtube.com/watch?v=69C1ljfDvl0
     **/

    private Context context;
    private ArrayList<MovieRecyclerView> items;
    private ArrayList<MovieRecyclerView> searchItems;
    private OnMovieListener mOnMovieListener;
    ImageView tempImage;

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
        return new CustomViewHolder(LayoutInflater.from(context).inflate(R.layout.movies_item, parent, false), mOnMovieListener);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {

        holder.itemTitle.setText(items.get(position).getTitle());
        //Picasso.with(context).load(items.get(position).getImage()).into(tempImage);

        //Uri uri =  Uri.parse(items.get(position).getImage());
        //holder.itemImage.setImageResource(tempImage);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    // each individual CustomViewHolder (each recyclerView essentially)
    // each movie will have an OnMovieListener interface attached
    // View.OnClickListener detects click inside CustomViewHolder class
    public class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

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

    @Override
    public Filter getFilter() {
        return filter;
    }

    public Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            ArrayList<MovieRecyclerView> filteredList = new ArrayList<>();

            if (charSequence == null || charSequence.length() == 0){
                filteredList.addAll(searchItems);
            }

            else {
                String filterPattern = charSequence.toString().toLowerCase().trim();

                for (int i = 0; i < searchItems.size(); i++){
                    if (searchItems.get(i).getTitle().toLowerCase().contains(filterPattern)){
                        filteredList.add(searchItems.get(i));
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            items.clear();
            items.addAll((ArrayList) filterResults.values);
            notifyDataSetChanged();
        }
    };

}
