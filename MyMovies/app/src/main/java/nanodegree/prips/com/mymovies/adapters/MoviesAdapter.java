package nanodegree.prips.com.mymovies.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import nanodegree.prips.com.mymovies.R;
import nanodegree.prips.com.mymovies.model.Movie;

/**
 * Created by pseth on 9/7/15.
 */
public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieItemViewHolder> {
    private List<Movie> mMovies;
    private int mItemLayout;

    public MoviesAdapter(int itemLayout) {
        mItemLayout = itemLayout;
        mMovies = new ArrayList<>();
    }

    @Override
    public MovieItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View item = LayoutInflater.from(viewGroup.getContext()).inflate(mItemLayout, viewGroup, false);
        return new MovieItemViewHolder(item);
    }

    @Override
    public void onBindViewHolder(MovieItemViewHolder viewHolder, int position) {
        Movie movie = mMovies.get(position);
        viewHolder.imageView.setImageBitmap(null);
        Picasso.with(viewHolder.imageView.getContext()).cancelRequest(viewHolder.imageView);
        Picasso.with(viewHolder.imageView.getContext()).load(movie.getPosterURL(Movie.PosterSize.W500)).into(viewHolder.imageView);
    }

    @Override
    public int getItemCount() {
        return mMovies.size();
    }

    public void swapData(List<Movie> movies) {
        if (movies != null && !movies.isEmpty()) {
            mMovies = movies;
        }
        notifyDataSetChanged();
    }

    //-- ViewHolder which holds references to each grid item views.
    public static class MovieItemViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;

        public MovieItemViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.fragment_movies_grid_item_poster);
        }
    }
}
