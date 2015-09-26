package nanodegree.prips.com.mymovies.common;

/**
 * Created by pseth on 9/9/15.
 */
public class PageConfig {

    public int mPageNumber;
    public int mLastVisibleItem;
    public int mNumberOfItems;
    public int mPageSize;
    public Boolean mIsLoading;

    public static class Builder extends AbstractBuilder<PageConfig> {

        public Builder withPageNumber(int pageNumber) {
            if(pageNumber < 0){
                throw new IllegalArgumentException("pageNumber");
            }

            init();
            mBuilt.mPageNumber = pageNumber;
            return this;
        }

        public Builder withLastVisibleItem(int lastVisibleItem) {
            if(lastVisibleItem < 0){
                throw new IllegalArgumentException("lastVisibleItem");
            }

            init();
            mBuilt.mLastVisibleItem = lastVisibleItem;
            return this;
        }

        public Builder withNumberOfItems(int numberOfItems) {
            if(numberOfItems < 0){
                throw new IllegalArgumentException("numberOfItems");
            }

            init();
            mBuilt.mNumberOfItems = numberOfItems;
            return this;
        }

        public Builder withPageSize(int pageSize) {
            if(pageSize < 0){
                throw new IllegalArgumentException("pageSize");
            }

            init();
            mBuilt.mPageSize = pageSize;
            return this;
        }

        public Builder withIsLoading(boolean isLoading) {
            init();
            mBuilt.mIsLoading = isLoading;
            return this;
        }

        @Override
        protected PageConfig createInstance() {
            return new PageConfig();
        }
    }

}

