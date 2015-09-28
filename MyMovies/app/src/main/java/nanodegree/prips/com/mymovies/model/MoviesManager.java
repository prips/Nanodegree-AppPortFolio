package nanodegree.prips.com.mymovies.model;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;

import de.greenrobot.event.EventBus;
import nanodegree.prips.com.mymovies.Events.MoviesEvent;
import nanodegree.prips.com.mymovies.R;
import nanodegree.prips.com.mymovies.common.AppHandles;

/**
 * Created by pseth on 9/6/15.
 */
public class MoviesManager {
    private final static String LOG_TAG = MoviesManager.class.getSimpleName();
    private final static int MIN_PAGE_NUMBER = 0;
    private final static int MAX_PAGE_NUMBER = 1000;
    private final static String SORT_BY = "sort_by";
    private final static String API_KEY = "api_key";
    private final static String PAGE = "page";

    private static Context mContext;
    private static Collection<Movie> mMovies = new ArrayList<>();
    private static int mPage = 1;

    public interface Sort {
        String POPULARITY_DESC = "popularity.desc";
        String TOP_RATED = "vote_count.desc";

    }

    public MoviesManager(Context context) {
        mContext = context;
    }

    public void fetchPopularMovies(int page) {
        mPage = page;
        loadMovies(Sort.POPULARITY_DESC);
    }

    public void fetchHighestRatedMovies(int page) {
        mPage = page;
        loadMovies(Sort.TOP_RATED);
    }

    public Collection<Movie> getMovies() {
        return mMovies;
    }

    private void loadMovies(String sortType) {
        if (mPage <= MIN_PAGE_NUMBER || mPage > MAX_PAGE_NUMBER) {
            return; //nothing to fetch
        }
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.serializeNulls();
        final Gson gson = gsonBuilder.create();

        //http://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc&api_key=0dc5ed834762f26ec707247e9e57f196&page=1
        final String BASE_URL = "http://api.themoviedb.org/3/discover/movie?";
        Uri uri = Uri.parse(BASE_URL).buildUpon()
                .appendQueryParameter(SORT_BY, sortType)
                .appendQueryParameter(API_KEY, mContext.getResources().getString(R.string.TMDB_API_KEY))
                .appendQueryParameter(PAGE, Integer.toString(mPage))
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
                //post success event
                EventBus.getDefault().post(new MoviesEvent());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d(LOG_TAG, "Error fetching movies:" + volleyError);
                EventBus.getDefault().post(new MoviesEvent(volleyError.getMessage()));
            }
        });
        AppHandles.getInstance(mContext).getRequestQueue().add(request);
    }
}
