package nanodegree.prips.com.mymovies.common;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

import nanodegree.prips.com.mymovies.model.MoviesManager;

/**
 * Created by pseth on 9/6/15.
 */
public class AppHandles {
    private static AppHandles sInstance;
    private static Context mContext;
    private MoviesManager mMoviesManager;
    private ImageLoader mImageLoader;
    private RequestQueue mRequestQueue;


    private AppHandles(Context context) {
        mContext = context;
        mRequestQueue = getRequestQueue();
        mImageLoader = new ImageLoader(mRequestQueue, new LruBitmapCache(LruBitmapCache.getCacheSize(mContext)));
    }

    public static AppHandles getInstance(Context context) {
        if (context == null) {
            throw new IllegalArgumentException("context is null");
        }
        synchronized (AppHandles.class){
            if (sInstance == null) {
                sInstance = new AppHandles(context);
            }
            return sInstance;
        }
    }

    public MoviesManager getMoviesManager() {
        synchronized (AppHandles.class) {
            if (mMoviesManager == null) {
                mMoviesManager = new MoviesManager(mContext);
            }
            return mMoviesManager;
        }
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            mRequestQueue = Volley.newRequestQueue(mContext.getApplicationContext());
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

    public ImageLoader getImageLoader() {
        return mImageLoader;
    }
}
