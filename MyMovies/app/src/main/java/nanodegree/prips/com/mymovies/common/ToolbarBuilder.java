package nanodegree.prips.com.mymovies.common;

import android.content.Context;
import android.support.v7.widget.Toolbar;

/**
 * Created by pseth on 9/10/15.
 */
public class ToolbarBuilder extends AbstractBuilder<Toolbar> {
    private Context mContext;

    public ToolbarBuilder(Context context) {
        if (context == null) {
            throw new IllegalArgumentException("context");
        }
        mContext = context;
    }

    public ToolbarBuilder buildWithTitle(String title) {
        init();
        mBuilt.setTitle(title);
        return this;
    }

    @Override
    protected Toolbar createInstance() {
        return new Toolbar(mContext);
    }

    private interface IToolbarType {
        int TOOLBAR_SHORT = 0;
        int TOOLBAR_TALL = 1;
    }
}
