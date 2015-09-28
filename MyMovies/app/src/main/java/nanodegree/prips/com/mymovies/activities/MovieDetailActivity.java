package nanodegree.prips.com.mymovies.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import nanodegree.prips.com.mymovies.R;
import nanodegree.prips.com.mymovies.fragments.MovieDetailFragment;

/**
 * Created by pseth on 9/27/15.
 */
public class MovieDetailActivity extends AppCompatActivity {
    private Bundle mBundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        if (savedInstanceState != null) {
            initFromBundle(savedInstanceState);
        } else {
            initFromIntent(getIntent());
            showFragment();
        }
    }

    private void initFromBundle(Bundle savedInstanceState) {
        mBundle = savedInstanceState;
    }

    private void initFromIntent(Intent intent) {
        if (intent != null) {
            mBundle = intent.getExtras();
        }
    }

    private void showFragment() {
        Fragment detailsFragment = new MovieDetailFragment();
        detailsFragment.setArguments(mBundle);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_frame, detailsFragment);
        fragmentTransaction.commit();
    }
}
