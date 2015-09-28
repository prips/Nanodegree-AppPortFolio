package nanodegree.prips.com.mymovies.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
    private AdapterView.OnItemClickListener mOnItemClickListener;
    private int mItemLayout;

    public MoviesAdapter(int itemLayout, AdapterView.OnItemClickListener itemClickListener) {
        mItemLayout = itemLayout;
        mOnItemClickListener = itemClickListener;
        mMovies = new ArrayList<>();
    }

    @Override
    public MovieItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View item = LayoutInflater.from(viewGroup.getContext()).inflate(mItemLayout, viewGroup, false);
        return new MovieItemViewHolder(item, mOnItemClickListener, this);
    }

    @Override
    public void onBindViewHolder(MovieItemViewHolder viewHolder, int position) {
        Movie movie = mMovies.get(position);
        viewHolder.imageView.setImageBitmap(null);
        Picasso.with(viewHolder.imageView.getContext()).cancelRequest(viewHolder.imageView);
        if (movie != null) {
            String posterUrl =  movie.getPosterURL(Movie.PosterSize.W500);
            if (posterUrl == null) {
                //if no poster is available for movie then show default image
                viewHolder.imageView.setImageResource(R.drawable.error_icon);
            }
            Picasso.with(viewHolder.imageView.getContext()).load(posterUrl).into(viewHolder.imageView);
        }
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

    public List<Movie> getMovies() {
        return mMovies;
    }

    private void onItemHolderClick(AdapterView.OnItemClickListener onItemClickListener, MovieItemViewHolder movieItemViewHolder) {
        if (onItemClickListener != null) {
            onItemClickListener.onItemClick(null, movieItemViewHolder.itemView, movieItemViewHolder.getPosition(), movieItemViewHolder.getItemId());
        }
    }

    //-- ViewHolder which holds references to each grid item views.
    public static class MovieItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView imageView;
        private AdapterView.OnItemClickListener mOnItemClickListener;
        private MoviesAdapter mMoviesAdapter;

        public MovieItemViewHolder(View itemView, AdapterView.OnItemClickListener onItemClickListener, MoviesAdapter moviesAdapter) {
            super(itemView);
            mOnItemClickListener = onItemClickListener;
            mMoviesAdapter = moviesAdapter;
            imageView = (ImageView) itemView.findViewById(R.id.fragment_movies_grid_item_poster);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mMoviesAdapter.onItemHolderClick(mOnItemClickListener, this);
        }
    }
}
