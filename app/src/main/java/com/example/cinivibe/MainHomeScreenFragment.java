package com.example.cinivibe;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.widget.TextView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MainHomeScreenFragment extends Fragment implements CustomAdapter.OnMovieListener{

    // All variables declared here
    private CustomAdapter now_playing_adapter;
    private CustomAdapter upcoming_adapter;
    private CustomAdapter action_adapter;
    private CustomAdapter romance_adapter;
    private CustomAdapter horror_adapter;
    private CustomAdapter comedy_adapter;
    private CustomAdapter sci_fi_adapter;

    private RequestQueue mQueue;

    private ArrayList<MovieRecyclerView> now_playing;
    private ArrayList<MovieRecyclerView> upcoming;
    private ArrayList<MovieRecyclerView> action;
    private ArrayList<MovieRecyclerView> romance;
    private ArrayList<MovieRecyclerView> horror;
    private ArrayList<MovieRecyclerView> comedy;
    private ArrayList<MovieRecyclerView> sci_fi;

    private String poster_path;
    private JSONArray genre_ids_JSON;
    private String title;
    private Double vote_average;
    private String overview;
    private String release_date;
    private int[] genre_ids;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main_home_screen, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initializes all of the ArrayLists
        now_playing = MainHomeScreenActivity.now_playing;
        upcoming = new ArrayList<>();
        action = new ArrayList<>();
        romance = new ArrayList<>();
        horror = new ArrayList<>();
        comedy = new ArrayList<>();
        sci_fi = new ArrayList<>();

        // Initializes all of the adapters
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

        // This method actually scrapes the movies and puts them into their respective genre arrays
        jsonParse();

        nowShowingRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext(),LinearLayoutManager.HORIZONTAL, false));
        nowShowingRecyclerView.setAdapter(now_playing_adapter);
        comingSoonRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext(),LinearLayoutManager.HORIZONTAL, false));
        comingSoonRecyclerView.setAdapter(upcoming_adapter);

        // OnClickListener for the list of genres
        // Based on what is clicked, it will fill the GridViewFragment with that genre of movies
        AdapterView.OnItemClickListener itemClickListener =
                new AdapterView.OnItemClickListener(){
                    public void onItemClick(AdapterView<?> listView,
                                            View itemView, int position, long id){
                        if (position == 0) {
                            sendToGridView(action, "action");
                        }

                        if (position == 1) {
                            sendToGridView(romance, "romance");
                        }

                        if (position == 2) {
                            sendToGridView(horror, "horror");
                        }

                        if (position == 3) {
                            sendToGridView(comedy, "comedy");
                        }

                        if (position == 4) {
                            sendToGridView(sci_fi, "sci_fi");
                        }

                    }
                };

        //Add the listener to the list View
        ListView listView = (ListView) getView().findViewById(R.id.listViewGenres);
        listView.setOnItemClickListener(itemClickListener);

        TextView seeAllPassData = (TextView) view.findViewById(R.id.seeAllNowShowing);

        // Sets an onClickListener for whenever the user clicks on the See All button for Now Showing movies
        seeAllPassData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendToGridView(now_playing, "now_playing");
            }

        });

        TextView upcomingPassData = (TextView) view.findViewById(R.id.seeAllComingSoon);

        // Sets an onClickListener for whenever the user clicks on the See All button for Upcoming movies
        upcomingPassData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendToGridView(upcoming, "upcoming");
            }

        });

    }

    // implemented method from class CustomAdapter to navigate to new activity
    // Passes the movie based on the position it was in from the Now Playing ArrayList
    // https://www.youtube.com/watch?v=69C1ljfDvl0
    @Override
    public void onMovieClick(int position) {
        MainHomeScreenActivity.movie = position;
        Bundle bundle = new Bundle();
        bundle.putParcelable("movie", now_playing.get(position));

        IndividualMovieFragment nextFrag = new IndividualMovieFragment();
        nextFrag.setArguments(bundle);
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, nextFrag, "findThisFragment")
                .addToBackStack(null)
                .commit();
    }


    // Fully scrapes all movies including Now Showing, Upcoming, and all the genres
    private void jsonParse() {

        jsonParseNowPlaying();

        jsonParseUpcoming();

        jsonParsePopular();
    }

    // Scrapes all of the now playing movies and adds it to a Now Playing ArrayList
    // It also sorts the movies into the different genre ArrayLists
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

                                scrapeJSONData(movie);

                                MovieRecyclerView moviePlaceHolder = new MovieRecyclerView(poster_path, title, overview, genre_ids, vote_average, release_date, getDateValue(release_date));

                                checkDupes(now_playing, now_playing_adapter, title, moviePlaceHolder);

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

                                // Sorts the movies into the different genre ArrayLists
                                for (int j = 0; j < genre_ids_JSON.length(); j++){
                                    if (genre_ids[j] == 28){
                                        checkDupes(action, action_adapter, title, moviePlaceHolder);
                                    }

                                    if (genre_ids[j] == 10749){
                                        checkDupes(romance, romance_adapter, title, moviePlaceHolder);
                                    }

                                    if (genre_ids[j] == 27){
                                        checkDupes(horror, horror_adapter, title, moviePlaceHolder);
                                    }

                                    if (genre_ids[j] == 35){
                                        checkDupes(comedy, comedy_adapter, title, moviePlaceHolder);
                                    }

                                    if (genre_ids[j] == 878){
                                        checkDupes(sci_fi, sci_fi_adapter, title, moviePlaceHolder);
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

    // Scrapes all of the upcoming movies and adds it to a Upcoming ArrayList
    public void jsonParseUpcoming() {

        for (int i = 1; i < 3; i++){

            String upcoming_url = "https://api.themoviedb.org/3/movie/upcoming?api_key=3e71659358fcc0e15a078ffbfd22b2fc&language=en-US&page=" + i;

            JsonObjectRequest upcoming_request = new JsonObjectRequest(Request.Method.GET, upcoming_url, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                JSONArray jsonArray = response.getJSONArray("results");

                                for (int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject movie = jsonArray.getJSONObject(i);

                                    scrapeJSONData(movie);

                                    String dateToday = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

                                    if (getDateValue(dateToday) < getDateValue(release_date)) {

                                        MovieRecyclerView moviePlaceHolder = new MovieRecyclerView(poster_path, title, overview, genre_ids, vote_average, release_date, getDateValue(release_date));

                                        checkDupes(upcoming, upcoming_adapter, title, moviePlaceHolder);
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

            mQueue.add(upcoming_request);

        }
    }

    // Scrapes all of the Popular movies and sorts them into the different genre ArrayLists
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

                                    scrapeJSONData(movie);

                                    MovieRecyclerView moviePlaceHolder = new MovieRecyclerView(poster_path, title, overview, genre_ids, vote_average, release_date, getDateValue(release_date));

                                    // Sorts the movies into the different genre ArrayLists
                                    for (int j = 0; j < genre_ids_JSON.length(); j++) {

                                        if (genre_ids[j] == 28) {
                                            checkDupes(action, action_adapter, title, moviePlaceHolder);
                                        }

                                        if (genre_ids[j] == 10749){
                                            checkDupes(romance, romance_adapter, title, moviePlaceHolder);
                                        }

                                        if (genre_ids[j] == 27) {
                                            checkDupes(horror, horror_adapter, title, moviePlaceHolder);
                                        }

                                        if (genre_ids[j] == 35){
                                            checkDupes(comedy, comedy_adapter, title, moviePlaceHolder);
                                        }

                                        if (genre_ids[j] == 878){
                                            checkDupes(sci_fi, sci_fi_adapter, title, moviePlaceHolder);
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

    public int getDateValue(String release_date){
        int yearMovie = Integer.parseInt(release_date.substring(0, 4)) * 10000;
        int monthMovie = Integer.parseInt(release_date.substring(5, 7)) * 100;
        int dayMovie = Integer.parseInt(release_date.substring(8));
        return yearMovie + monthMovie + dayMovie;
    }

    public void checkDupes(ArrayList<MovieRecyclerView> list, CustomAdapter list_adapter, String title, MovieRecyclerView moviePlaceHolder){
        for (int k = 0; k < list.size(); k++) {
            if (title.equals(list.get(k).getTitle())) {
                list.remove(k);
            }
        }

        list.add(moviePlaceHolder);
        list_adapter.notifyDataSetChanged();
    }

    public void sendToGridView(ArrayList<MovieRecyclerView> list, String list_name){
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(list_name, list);

        GridViewFragment nextFrag = new GridViewFragment();
        nextFrag.setArguments(bundle);
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, nextFrag, "findThisFragment")
                .addToBackStack(null)
                .commit();
    }

    public void scrapeJSONData(JSONObject movie){
        try {
            poster_path = movie.getString("poster_path");
            genre_ids_JSON = movie.optJSONArray("genre_ids");
            title = movie.getString("title");
            vote_average = movie.getDouble("vote_average");
            overview = movie.getString("overview");
            release_date = movie.getString("release_date");


            genre_ids = new int[genre_ids_JSON.length()];

            for (int j = 0; j < genre_ids_JSON.length(); j++) {
                genre_ids[j] = genre_ids_JSON.optInt(j);
            }
        }

        catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
