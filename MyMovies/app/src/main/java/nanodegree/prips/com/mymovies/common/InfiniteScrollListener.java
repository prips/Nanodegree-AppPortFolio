package nanodegree.prips.com.mymovies.common;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by pseth on 9/8/15.
 * Inspired from StackOverflow answers
 */
public class InfiniteScrollListener extends RecyclerView.OnScrollListener {
    private PageConfig mPageConfig;
    private int mCurrentPage = 1;
    private int mCurrentTotalItems;
    private int mFirstItemPageIndex;
    private int mFirstVisibleItem = -1;
    private boolean mLoading;

    private LoadMoreListener mLoadMoreListener;

    public InfiniteScrollListener(PageConfig pageConfig, LoadMoreListener loadMoreListener) {
        mLoadMoreListener = loadMoreListener;
        mPageConfig = pageConfig;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        int visibleItemCount = recyclerView.getChildCount();
        int totalItemCount = recyclerView.getLayoutManager().getItemCount();
        mFirstVisibleItem = ((GridLayoutManager)recyclerView.getLayoutManager()).findFirstVisibleItemPosition();

        if (totalItemCount < mCurrentTotalItems) {
            mCurrentPage = mFirstItemPageIndex;
            mCurrentTotalItems = totalItemCount;
            if (totalItemCount == 0) {
                mLoading = true;
            }
        }

        if (mLoading) {
            if (totalItemCount >= mCurrentTotalItems) {
                disableLoading(totalItemCount);
            }
        } else {
            if ((totalItemCount - visibleItemCount) <= (mFirstVisibleItem)) {
                enableLoading(totalItemCount);
            }
        }

//        int totalItemCount = recyclerView.getLayoutManager().getItemCount();
//        int lastVisibleItem = ((GridLayoutManager)recyclerView.getLayoutManager()).findLastVisibleItemPosition();
//        if (!mLoading && totalItemCount <= (lastVisibleItem + mVisibleThreshold)) {
//            // End has been reached
//            // Do something
//            if(mLoadMoreListener!=null) {
//                //onLoadMoreListener.onLoadMore();
//                mLoadMoreListener.getNextPageOnScrolled(mPageConfig);
//                mLoadMoreListener.isLoading(true);
//            }
//            mLoading = true;
//        }
//        if (mLoading) {
//            if (totalItemCount >= mCurrentTotalItems) {
//                disableLoading(totalItemCount);
//            }
//        } else {
//            if ((totalItemCount - visibleItemCount) <= (mFirstVisibleItem)) {
//                enableLoading(totalItemCount);
//            }
//        }
    }

    private void disableLoading(int totalItemCount) {
        mLoading = false;
        mLoadMoreListener.isLoading(false);
        mCurrentTotalItems = totalItemCount;
        mCurrentPage++;
    }

    private void enableLoading(int totalItemCount) {
       // mPageConfig.mLastVisibleItem = totalItemCount - 1;
        mPageConfig.mPageNumber = mCurrentPage;
        mLoadMoreListener.getNextPageOnScrolled(mPageConfig);

        mLoading = true;
        mLoadMoreListener.isLoading(true);
    }

    public interface LoadMoreListener {
        void getNextPageOnScrolled(PageConfig pageConfig);

        void isLoading(Boolean isLoading);
    }
}
