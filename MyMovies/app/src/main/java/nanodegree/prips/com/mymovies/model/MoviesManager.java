package nanodegree.prips.com.mymovies.model;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import nanodegree.prips.com.mymovies.R;
import nanodegree.prips.com.mymovies.common.AppHandles;

/**
 * Created by pseth on 9/6/15.
 */
public class MoviesManager {
    private final static String LOG_TAG = MoviesManager.class.getSimpleName();
    private final static int MIN_PAGE_NUMBER = 0;
    private final static int MAX_PAGE_NUMBER = 1000;

    private static Context mContext;
    private static List<Movie> mMovies = new ArrayList<>();
    private static int mPage = 1;

    public MoviesManager(Context context) {
        mContext = context;
    }

    public static List<Movie> getPopularMovies(int page) {
        mPage = page;
        loadPopularMovies();
        return mMovies;
    }

    private static void loadPopularMovies() {
        if (mPage <= MIN_PAGE_NUMBER || mPage > MAX_PAGE_NUMBER) {
            Log.d(LOG_TAG, "Invalid page requested. Pages allowed is in between 1 and 1000");
            mPage = 1; //Show the first page?
        }
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.serializeNulls();
        final Gson gson = gsonBuilder.create();
        mMovies.clear();

        //http://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc&api_key=0dc5ed834762f26ec707247e9e57f196&page=1
        final String BASE_URL = "http://api.themoviedb.org/3/discover/movie?";
        Uri uri = Uri.parse(BASE_URL).buildUpon()
                .appendQueryParameter("sort_by", "popularity.desc")
                .appendQueryParameter("api_key", mContext.getResources().getString(R.string.TMDB_API_KEY))
                .appendQueryParameter("page", Integer.toString(mPage))
                .build();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, uri.toString(), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                JSONArray jsonArray = null;
                try {
                    jsonArray = jsonObject.getJSONArray("results");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JsonElement element = new JsonParser().parse(jsonArray.get(i).toString());
                        Movie movie = gson.fromJson(element, Movie.class);
                        if (movie != null) {
                            mMovies.add(movie);
                        }
                    }
                    Log.d(LOG_TAG, mMovies.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d(LOG_TAG, "Error fetching movies:" + volleyError);
            }
        });

        AppHandles.getInstance(mContext).getRequestQueue().add(request);
    }


}
