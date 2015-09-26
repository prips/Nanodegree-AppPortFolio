package nanodegree.prips.com.mymovies.common;

/**
 * Created by pseth on 9/9/15.
 */
public abstract class AbstractBuilder<T> {
    protected T mBuilt = null;

    public T build() {
        init();
        return mBuilt;
    }

    protected void init() {
        if(mBuilt == null) {
            mBuilt = createInstance();
        }
    }

    protected abstract T createInstance();
}
