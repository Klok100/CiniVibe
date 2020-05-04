package com.example.cinivibe;

import android.content.Context;
import android.content.Intent;
import android.graphics.Movie;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainHomeScreenFragment extends Fragment implements CustomAdapter.OnMovieListener{

    private CustomAdapter now_playing_adapter;
    private CustomAdapter upcoming_adapter;
    private CustomAdapter action_adapter;
    private CustomAdapter romance_adapter;
    private CustomAdapter horror_adapter;
    private CustomAdapter comedy_adapter;
    private CustomAdapter sci_fi_adapter;

    private RequestQueue mQueue;
    private int start = 0;
    private int end = 32;

    private ArrayList<MovieRecyclerView> now_playing;
    private ArrayList<MovieRecyclerView> upcoming;
    private ArrayList<MovieRecyclerView> action;
    private ArrayList<MovieRecyclerView> romance;
    private ArrayList<MovieRecyclerView> horror;
    private ArrayList<MovieRecyclerView> comedy;
    private ArrayList<MovieRecyclerView> sci_fi;
    private ArrayList<MovieRecyclerView> individual_movie;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main_home_screen, container, false);

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        now_playing = new ArrayList<>();
        upcoming = new ArrayList<>();
        action = new ArrayList<>();
        romance = new ArrayList<>();
        horror = new ArrayList<>();
        comedy = new ArrayList<>();
        sci_fi = new ArrayList<>();
        individual_movie = new ArrayList<>();

        // passing onMovieListener interface to constructor of CustomAdapter
        now_playing_adapter = new CustomAdapter(this.getContext(), now_playing, this);
        upcoming_adapter = new CustomAdapter(this.getContext(), upcoming, this);
        action_adapter = new CustomAdapter(this.getContext(), action, this);
        romance_adapter = new CustomAdapter(this.getContext(), romance, this);
        horror_adapter = new CustomAdapter(this.getContext(), horror, this);
        comedy_adapter = new CustomAdapter(this.getContext(), comedy, this);
        sci_fi_adapter = new CustomAdapter(this.getContext(), sci_fi, this);

        RecyclerView nowShowingRecyclerView = view.findViewById(R.id.nowShowingRecyclerView);
        RecyclerView comingSoonRecyclerView = view.findViewById(R.id.comingSoonRecyclerView);

        mQueue = Volley.newRequestQueue(this.getContext());

        jsonParse();

        //for (int i = 0; i < 10; i++) {
        //    items.add(new MovieRecyclerView(R.drawable.cinivibe_logo, "Parasite"));
        //    adapter.notifyDataSetChanged();
        //}

        nowShowingRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext(),LinearLayoutManager.HORIZONTAL, false));
        nowShowingRecyclerView.setAdapter(now_playing_adapter);
        comingSoonRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext(),LinearLayoutManager.HORIZONTAL, false));
        comingSoonRecyclerView.setAdapter(upcoming_adapter);

        // OnClickListener for the list of genres
        AdapterView.OnItemClickListener itemClickListener =
                new AdapterView.OnItemClickListener(){
                    public void onItemClick(AdapterView<?> listView,
                                            View itemView, int position, long id){
                        if (position == 0) {
                            Bundle bundle = new Bundle();
                            bundle.putParcelableArrayList("action", action);

                            GridViewFragment nextFrag = new GridViewFragment();
                            nextFrag.setArguments(bundle);
                            getActivity().getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.fragment_container, nextFrag, "findThisFragment")
                                    .addToBackStack(null)
                                    .commit();
                        }

                        if (position == 1) {
                            Bundle bundle = new Bundle();
                            bundle.putParcelableArrayList("romance", romance);

                            GridViewFragment nextFrag = new GridViewFragment();
                            nextFrag.setArguments(bundle);
                            getActivity().getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.fragment_container, nextFrag, "findThisFragment")
                                    .addToBackStack(null)
                                    .commit();
                        }

                        if (position == 2) {
                            Bundle bundle = new Bundle();
                            bundle.putParcelableArrayList("horror", horror);

                            GridViewFragment nextFrag = new GridViewFragment();
                            nextFrag.setArguments(bundle);
                            getActivity().getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.fragment_container, nextFrag, "findThisFragment")
                                    .addToBackStack(null)
                                    .commit();
                        }

                        if (position == 3) {
                            Bundle bundle = new Bundle();
                            bundle.putParcelableArrayList("comedy", comedy);

                            GridViewFragment nextFrag = new GridViewFragment();
                            nextFrag.setArguments(bundle);
                            getActivity().getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.fragment_container, nextFrag, "findThisFragment")
                                    .addToBackStack(null)
                                    .commit();
                        }

                        if (position == 4) {
                            Bundle bundle = new Bundle();
                            bundle.putParcelableArrayList("sci_fi", sci_fi);

                            GridViewFragment nextFrag = new GridViewFragment();
                            nextFrag.setArguments(bundle);
                            getActivity().getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.fragment_container, nextFrag, "findThisFragment")
                                    .addToBackStack(null)
                                    .commit();
                        }

                    }
                };
        //Add the listener to the list View
        ListView listView = (ListView) getView().findViewById(R.id.listViewGenres);
        listView.setOnItemClickListener(itemClickListener);

        TextView seeAllPassData = (TextView) view.findViewById(R.id.seeAllNowShowing);

        seeAllPassData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("now_playing", now_playing);

                GridViewFragment nextFrag = new GridViewFragment();
                nextFrag.setArguments(bundle);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, nextFrag, "findThisFragment")
                        .addToBackStack(null)
                        .commit();
            }

        });

        TextView upcomingPassData = (TextView) view.findViewById(R.id.seeAllComingSoon);

        upcomingPassData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("upcoming", upcoming);

                GridViewFragment nextFrag = new GridViewFragment();
                nextFrag.setArguments(bundle);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, nextFrag, "findThisFragment")
                        .addToBackStack(null)
                        .commit();
            }

        });

    }


    // implemented method from class CustomAdapter to navigate to new activity
    // passes movie from position sent
    @Override
    public void onMovieClick(int position) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("movie", now_playing.get(position));

        IndividualMovieFragment nextFrag = new IndividualMovieFragment();
        nextFrag.setArguments(bundle);
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, nextFrag, "findThisFragment")
                .addToBackStack(null)
                .commit();
    }


    public void onHomeClick(){
        MainHomeScreenFragment nextFrag = new MainHomeScreenFragment();
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, nextFrag, "findThisFragment")
                .addToBackStack(null)
                .commit();
    }


    private void jsonParse() {

        jsonParseNowPlaying();

        jsonParseUpcoming();

        jsonParsePopular();
    }


    public void jsonParseNowPlaying(){

        String now_playing_url = "https://api.themoviedb.org/3/movie/now_playing?api_key=3e71659358fcc0e15a078ffbfd22b2fc&language=en-US&page=1";

        JsonObjectRequest now_playing_request = new JsonObjectRequest(Request.Method.GET, now_playing_url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("results");

                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject movie = jsonArray.getJSONObject(i);

                                Double popularity = movie.getDouble("popularity");
                                int vote_count = movie.getInt("vote_count");
                                Boolean video = movie.getBoolean("video");
                                String poster_path = movie.getString("poster_path");
                                int id = movie.getInt("id");
                                JSONArray genre_ids_JSON = movie.optJSONArray("genre_ids");
                                Boolean adult = movie.getBoolean("adult");
                                String backdrop_path = movie.getString("backdrop_path");
                                String original_language = movie.getString("original_language");
                                String original_title = movie.getString("original_title");
                                String title = movie.getString("title");
                                Double vote_average = movie.getDouble("vote_average");
                                String overview = movie.getString("overview");
                                String release_date = movie.getString("release_date");

                                now_playing.add(new MovieRecyclerView(poster_path, title, overview));
                                now_playing_adapter.notifyDataSetChanged();

                                int [] genre_ids = new int[genre_ids_JSON.length()];

                                for (int j = 0; j < genre_ids_JSON.length(); j++) {
                                    genre_ids[j] = genre_ids_JSON.optInt(j);
                                }

                                /** GENRES BY NUMBER (Genre List: https://api.themoviedb.org/3/genre/movie/list?api_key=3e71659358fcc0e15a078ffbfd22b2fc&language=en-US)
                                 *
                                 * Action - 28
                                 * Adventure - 12
                                 * Animation - 16
                                 * Comedy - 35
                                 * Crime - 80
                                 * Documentary - 99
                                 * Drama - 18
                                 * Family - 10751
                                 * Fantasy - 14
                                 * History - 36
                                 * Horror - 27
                                 * Music - 10402
                                 * Mystery - 9648
                                 * Romance - 10759
                                 * Science Fiction - 878
                                 * TY Movie - 10770
                                 * Thriller - 53
                                 * War - 10752
                                 * Western - 37
                                 *
                                 */


                                for (int j = 0; j < genre_ids_JSON.length(); j++){
                                    if (genre_ids[j] == 28){
                                        action.add(new MovieRecyclerView(poster_path, title, overview));
                                        action_adapter.notifyDataSetChanged();
                                    }

                                    if (genre_ids[j] == 10749){
                                        romance.add(new MovieRecyclerView(poster_path, title, overview));
                                        romance_adapter.notifyDataSetChanged();
                                    }

                                    if (genre_ids[j] == 27){
                                        horror.add(new MovieRecyclerView(poster_path, title, overview));
                                        horror_adapter.notifyDataSetChanged();
                                    }

                                    if (genre_ids[j] == 35){
                                        comedy.add(new MovieRecyclerView(poster_path, title, overview));
                                        comedy_adapter.notifyDataSetChanged();
                                    }

                                    if (genre_ids[j] == 878){
                                        sci_fi.add(new MovieRecyclerView(poster_path, title, overview));
                                        sci_fi_adapter.notifyDataSetChanged();
                                    }
                                }
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        mQueue.add(now_playing_request);
    }

    public void jsonParseUpcoming(){

        String upcoming_url = "https://api.themoviedb.org/3/movie/upcoming?api_key=3e71659358fcc0e15a078ffbfd22b2fc&language=en-US&page=1";

        JsonObjectRequest upcoming_request = new JsonObjectRequest(Request.Method.GET, upcoming_url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("results");

                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject movie = jsonArray.getJSONObject(i);

                                Double popularity = movie.getDouble("popularity");
                                int vote_count = movie.getInt("vote_count");
                                Boolean video = movie.getBoolean("video");
                                String poster_path = movie.getString("poster_path");
                                int id = movie.getInt("id");
                                JSONArray genre_ids_JSON = movie.optJSONArray("genre_ids");
                                Boolean adult = movie.getBoolean("adult");
                                String backdrop_path = movie.getString("backdrop_path");
                                String original_language = movie.getString("original_language");
                                String original_title = movie.getString("original_title");
                                String title = movie.getString("title");
                                Double vote_average = movie.getDouble("vote_average");
                                String overview = movie.getString("overview");
                                String release_date = movie.getString("release_date");

                                upcoming.add(new MovieRecyclerView(poster_path, title, overview));
                                upcoming_adapter.notifyDataSetChanged();

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        mQueue.add(upcoming_request);
    }

    public void jsonParsePopular(){

        for (int i = 1; i < 3; i++){

            String popular_url = "https://api.themoviedb.org/3/movie/popular?api_key=3e71659358fcc0e15a078ffbfd22b2fc&language=en-US&page=" + i;

            JsonObjectRequest popular_request = new JsonObjectRequest(Request.Method.GET, popular_url, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                JSONArray jsonArray = response.getJSONArray("results");

                                for (int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject movie = jsonArray.getJSONObject(i);

                                    Double popularity = movie.getDouble("popularity");
                                    int vote_count = movie.getInt("vote_count");
                                    Boolean video = movie.getBoolean("video");
                                    String poster_path = movie.getString("poster_path");
                                    int id = movie.getInt("id");
                                    JSONArray genre_ids_JSON = movie.optJSONArray("genre_ids");
                                    Boolean adult = movie.getBoolean("adult");
                                    String backdrop_path = movie.getString("backdrop_path");
                                    String original_language = movie.getString("original_language");
                                    String original_title = movie.getString("original_title");
                                    String title = movie.getString("title");
                                    Double vote_average = movie.getDouble("vote_average");
                                    String overview = movie.getString("overview");
                                    String release_date = movie.getString("release_date");

                                    int [] genre_ids = new int[genre_ids_JSON.length()];

                                    for (int j = 0; j < genre_ids_JSON.length(); j++) {
                                        genre_ids[j] = genre_ids_JSON.optInt(j);
                                    }

                                    for (int j = 0; j < genre_ids_JSON.length(); j++) {

                                        if (genre_ids[j] == 28) {
                                            for (int k = 0; k < action.size(); k++) {
                                                if (title.equals(action.get(k).getTitle())) {
                                                    action.remove(k);
                                                }
                                            }

                                            action.add(new MovieRecyclerView(poster_path, title, overview));
                                            action_adapter.notifyDataSetChanged();
                                        }

                                        if (genre_ids[j] == 10749){
                                            for (int k = 0; k < romance.size(); k++) {
                                                if (title.equals(romance.get(k).getTitle())) {
                                                    romance.remove(k);
                                                }
                                            }

                                            romance.add(new MovieRecyclerView(poster_path, title, overview));
                                            romance_adapter.notifyDataSetChanged();

                                        }

                                        if (genre_ids[j] == 27) {
                                            for (int k = 0; k < horror.size(); k++) {
                                                if (title.equals(horror.get(k).getTitle())) {
                                                    horror.remove(k);
                                                }
                                            }

                                            horror.add(new MovieRecyclerView(poster_path, title, overview));
                                            horror_adapter.notifyDataSetChanged();
                                        }

                                        if (genre_ids[j] == 35){
                                            for (int k = 0; k < comedy.size(); k++) {
                                                if (title.equals(comedy.get(k).getTitle())) {
                                                    comedy.remove(k);
                                                }
                                            }

                                            comedy.add(new MovieRecyclerView(poster_path, title, overview));
                                            comedy_adapter.notifyDataSetChanged();

                                        }

                                        if (genre_ids[j] == 878){
                                            for (int k = 0; k < sci_fi.size(); k++) {
                                               if (title.equals(sci_fi.get(k).getTitle())) {
                                                    sci_fi.remove(k);
                                                }
                                            }

                                            sci_fi.add(new MovieRecyclerView(poster_path, title, overview));
                                            sci_fi_adapter.notifyDataSetChanged();
                                        }
                                    }
                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                }
            });
            mQueue.add(popular_request);
        }
    }
}
