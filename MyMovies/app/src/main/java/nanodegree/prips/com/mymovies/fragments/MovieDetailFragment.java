package nanodegree.prips.com.mymovies.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import nanodegree.prips.com.mymovies.R;
import nanodegree.prips.com.mymovies.activities.MoviesGridActivity;
import nanodegree.prips.com.mymovies.common.AppHandles;
import nanodegree.prips.com.mymovies.model.Movie;
import nanodegree.prips.com.mymovies.model.MoviesManager;

/**
 * Created by pseth on 9/27/15.
 */
public class MovieDetailFragment extends BaseFragment {
    private int mMovieIndex = -1;

    public MovieDetailFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View retVal = inflater.inflate(R.layout.fragment_movie_details, container, false);

        if (getArguments() != null) {
            Bundle bundle = getArguments();
            mMovieIndex = bundle.getInt(MoviesGridActivity.BUNDLE_EXTRA_MOVIE_ITEM_POSITION);
            if (mMovieIndex < 0) {
                throw new IllegalArgumentException("Invalid movie index");
            }
        }
        return retVal;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initToolBar(null, R.drawable.icon_back_arrow_white, true);
        bindView();
    }

    private void bindView() {
        //TODO: UI sucks right now..make it look better :)
        MoviesManager moviesManager = AppHandles.getInstance(getActivity()).getMoviesManager();
        if (!moviesManager.getMovies().isEmpty() && moviesManager.getMovies().size() > 0) {
            Movie movie = ((ArrayList<Movie>)moviesManager.getMovies()).get(mMovieIndex);
            View view = getView();
            if (movie != null && view != null) {
                //bind poster
                String posterUrl = movie.getPosterURL(Movie.PosterSize.W342);
                Picasso.with(getActivity()).load(posterUrl).into((ImageView)view.findViewById(R.id.fragment_movie_details_poster_image));
                //bind title
                TextView title = (TextView) view.findViewById(R.id.movie_title);
                title.setText(movie.getTitle());
                //bind release date
                TextView releaseDate = (TextView) view.findViewById(R.id.movie_release_date);
                releaseDate.setText(movie.getReleaseDate());
                //bind rating
                TextView rating = (TextView) view.findViewById(R.id.movie_average_rating);
                rating.setText(movie.getRating());
                //bind synopsis
                TextView synopsis = (TextView) view.findViewById(R.id.fragment_movie_details_synopsis);
                synopsis.setText(movie.getOverview());
            }
        }

    }
}
