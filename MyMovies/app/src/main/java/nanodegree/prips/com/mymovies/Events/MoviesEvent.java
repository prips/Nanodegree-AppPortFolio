package nanodegree.prips.com.mymovies.Events;

/**
 * Created by pseth on 9/26/15.
 */
public class MoviesEvent {
    public boolean mIsError;
    public String mFailureMessage;

    public MoviesEvent() {
    }

    public MoviesEvent(String message) {
        mIsError = true;
        mFailureMessage = message;
    }
}
