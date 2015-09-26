package nanodegree.prips.com.mymovies.fragments;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.DimenRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import nanodegree.prips.com.mymovies.R;
import nanodegree.prips.com.mymovies.adapters.MoviesAdapter;
import nanodegree.prips.com.mymovies.common.AppHandles;
import nanodegree.prips.com.mymovies.common.InfiniteScrollListener;
import nanodegree.prips.com.mymovies.common.PageConfig;
import nanodegree.prips.com.mymovies.common.ToolbarBuilder;
import nanodegree.prips.com.mymovies.model.Movie;

/**
 * A placeholder fragment containing a simple view.
 */
public class MoviesGridFragment extends Fragment implements InfiniteScrollListener.LoadMoreListener{
    private MoviesAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private PageConfig mPageConfig;

    public MoviesGridFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.menu_movies_grid, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.action_refresh) {
            AppHandles.getInstance(getActivity()).getMoviesManager().getPopularMovies(mPageConfig.mPageNumber);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View top = inflater.inflate(R.layout.fragment_movies_grid, container, false);
        mAdapter = new MoviesAdapter(R.layout.fragment_movies_grid_item );
        mAdapter.notifyDataSetChanged();

        return top;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mPageConfig = new PageConfig.Builder()
                .withPageNumber(1)
                .build();

        View view = getView();

        if (view != null) {
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.addOnScrollListener(new InfiniteScrollListener(mPageConfig, this));
            recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
            ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(getActivity(), R.dimen.item_offset);
            recyclerView.addItemDecoration(itemDecoration);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setHasFixedSize(true);
            fetchMovies(mPageConfig.mPageNumber);
            recyclerView.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void getNextPageOnScrolled(PageConfig pageConfig) {
        fetchMovies(pageConfig.mPageNumber++);
    }

    @Override
    public void isLoading(Boolean isLoading) {
        mPageConfig.mIsLoading = isLoading;
    }

    private void fetchMovies(int page) {
        List<Movie> movies = AppHandles.getInstance(getActivity()).getMoviesManager().getPopularMovies(page);
        mAdapter.swapData(movies);
        mAdapter.notifyDataSetChanged();
    }

    public class ItemOffsetDecoration extends RecyclerView.ItemDecoration {

        private int mItemOffset;

        public ItemOffsetDecoration(int itemOffset) {
            mItemOffset = itemOffset;
        }

        public ItemOffsetDecoration(@NonNull Context context, @DimenRes int itemOffsetId) {
            this(context.getResources().getDimensionPixelSize(itemOffsetId));
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                                   RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            outRect.set(mItemOffset, mItemOffset, mItemOffset, mItemOffset);
        }
    }


}

