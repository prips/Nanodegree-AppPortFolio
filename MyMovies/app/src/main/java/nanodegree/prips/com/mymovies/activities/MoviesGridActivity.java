package nanodegree.prips.com.mymovies.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import nanodegree.prips.com.mymovies.R;

public class MoviesGridActivity extends AppCompatActivity {
    public final static String BUNDLE_EXTRA_MOVIE_ITEM_POSITION = "bundle_extra_movie_item_position";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies_grid);
    }
}
