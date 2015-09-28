package nanodegree.prips.com.mymovies.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.DimenRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import java.util.List;

import de.greenrobot.event.EventBus;
import nanodegree.prips.com.mymovies.Events.MoviesEvent;
import nanodegree.prips.com.mymovies.R;
import nanodegree.prips.com.mymovies.activities.MovieDetailActivity;
import nanodegree.prips.com.mymovies.activities.MoviesGridActivity;
import nanodegree.prips.com.mymovies.adapters.MoviesAdapter;
import nanodegree.prips.com.mymovies.common.AppHandles;
import nanodegree.prips.com.mymovies.common.InfiniteScrollListener;
import nanodegree.prips.com.mymovies.common.PageConfig;
import nanodegree.prips.com.mymovies.model.Movie;
import nanodegree.prips.com.mymovies.model.MoviesManager;

/**
 * A placeholder fragment containing a simple view.
 */
public class MoviesGridFragment extends BaseFragment implements InfiniteScrollListener.LoadMoreListener {
    private MoviesAdapter mAdapter;
    private PageConfig mPageConfig;
    private String mSortType = MoviesManager.Sort.POPULARITY_DESC;

    public MoviesGridFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.menu_movies_grid, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_sort_highest_rated) {
            mSortType = MoviesManager.Sort.TOP_RATED;
        } else if (id == R.id.action_sort_popular) {
            mSortType = MoviesManager.Sort.POPULARITY_DESC;
        }
        AppHandles.getInstance(getActivity()).getMoviesManager().getMovies().clear();
        initPageConfig();
        fetchMovies(mPageConfig.mPageNumber);
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View top = inflater.inflate(R.layout.fragment_movies_grid, container, false);
        AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Movie movie = mAdapter.getMovies().get(position);
                if (movie != null) {
                    Intent detailsIntent = new Intent(getActivity(), MovieDetailActivity.class);
                    detailsIntent.putExtra(MoviesGridActivity.BUNDLE_EXTRA_MOVIE_ITEM_POSITION, position);
                    startActivity(detailsIntent);
                }
            }
        };
        mAdapter = new MoviesAdapter(R.layout.fragment_movies_grid_item, itemClickListener );
        return top;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initToolBar(getString(R.string.app_name), 0, false);
        showProgressIndicator(true);
        initPageConfig();
        View view = getView();

        if (view != null) {
            RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.fragment_movies_grid_recycler_view);
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

    public void onEventMainThread(MoviesEvent event) {
        if (event.mIsError) {
            showErrorMessage(true);
            showProgressIndicator(false);
        } else {
            getDataFromModel();
        }
    }

    @Override
    public void getNextPageOnScrolled(PageConfig pageConfig) {
        int currentPageNumber = pageConfig.mPageNumber ;
        int nextPageNumber = currentPageNumber + 1;
        PageConfig nextPageConfig = new PageConfig.Builder().withPageNumber(nextPageNumber).build();
        fetchMovies(nextPageConfig.mPageNumber);
    }

    @Override
    public void isLoading(Boolean isLoading) {
        mPageConfig.mIsLoading = isLoading;
    }

    private void getDataFromModel() {
        MoviesManager moviesManager = AppHandles.getInstance(getActivity()).getMoviesManager();
        List<Movie> movies = (List<Movie>) moviesManager.getMovies();
        onDataReceived(movies);
    }

    private void onDataReceived(List<Movie> movies) {
        showErrorMessage(false);
        showProgressIndicator(false);

        if (movies.isEmpty()) {
            showEmptyMessage(true);
        } else {
            mAdapter.swapData(movies);
            showEmptyMessage(false);
        }
    }

    private void initPageConfig() {
        mPageConfig = new PageConfig.Builder()
                .withPageNumber(1)
                .build();
    }

    private void showEmptyMessage(boolean show) {
        if (getView() != null) {
            View errorView = getView().findViewById(R.id.fragment_movies_grid_list_empty_message_container);
            if (errorView != null) {
                if (show) {
                    errorView.setVisibility(View.VISIBLE);
                } else {
                    errorView.setVisibility(View.GONE);
                }
            }
        }
    }
    private void showProgressIndicator(boolean show) {
        if (getView() != null) {
            View progressIndicator = getView().findViewById(R.id.fragment_movies_grid_progress_indicator_container);
            if (progressIndicator != null) {
                if (show) {
                    progressIndicator.setVisibility(View.VISIBLE);
                } else {
                    progressIndicator.setVisibility(View.GONE);
                }
            }
        }
    }

    private void showErrorMessage(boolean show) {
        if (getView() != null) {
            View errorView = getView().findViewById(R.id.fragment_movies_grid_list_error_message_container);
            if (errorView != null) {
                if (show) {
                    errorView.setVisibility(View.VISIBLE);
                } else {
                    errorView.setVisibility(View.GONE);
                }
            }
        }
    }

    private void fetchMovies(int page) {
        MoviesManager manager = AppHandles.getInstance(getActivity()).getMoviesManager();
        switch (mSortType) {
            case MoviesManager.Sort.POPULARITY_DESC:
                manager.fetchPopularMovies(page);
            case MoviesManager.Sort.TOP_RATED:
                manager.fetchHighestRatedMovies(page);
        }
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

