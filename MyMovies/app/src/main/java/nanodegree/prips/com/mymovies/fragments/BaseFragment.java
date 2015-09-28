package nanodegree.prips.com.mymovies.fragments;

import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.AlphaAnimation;

import nanodegree.prips.com.mymovies.R;

/**
 * Created by pseth on 9/27/15.
 */
public class BaseFragment extends Fragment implements AppBarLayout.OnOffsetChangedListener {
    private static final float PERCENTAGE_TO_HIDE_TITLE_DETAILS = 0.9f;
    private static final int ALPHA_TITLE_CONTAINER_ANIMATIONS_DURATION = 300;
    private boolean mIsTheToolbarContainerVisible = true;

    protected void initToolBar(String title, int navigationResId, boolean displayHomeAsUpEnabled) {
        Toolbar toolbar = (Toolbar) getView().findViewById(R.id.toolbar);
        if (navigationResId > 0) {
            toolbar.setNavigationIcon(navigationResId);
        }
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        ActionBar bar = activity.getSupportActionBar();
        if (bar != null) {
            bar.setDisplayHomeAsUpEnabled(displayHomeAsUpEnabled);
            //bar.setDisplayShowTitleEnabled(false);
            if (title != null) {
                bar.setTitle(title);
            }
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getActivity().onBackPressed();
                }
            });
        }
        AppBarLayout appBarLayout = (AppBarLayout) getView().findViewById(R.id.appbar);
        if (appBarLayout != null) {
            appBarLayout.addOnOffsetChangedListener(this);
        }
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int offset) {
        int maxScroll = appBarLayout.getTotalScrollRange();
        float percentage = Math.abs(offset) / (float) Math.abs(maxScroll);
        handleToolbarContainerVisibility(percentage);
    }

    private void handleToolbarContainerVisibility(float percentage) {
        View content = getView().findViewById(R.id.appbar_content);
        if (percentage >= PERCENTAGE_TO_HIDE_TITLE_DETAILS) {
            if (mIsTheToolbarContainerVisible) {
                startAlphaAnimation(content, ALPHA_TITLE_CONTAINER_ANIMATIONS_DURATION, View.INVISIBLE);
                mIsTheToolbarContainerVisible = false;
            }
        } else {
            if (!mIsTheToolbarContainerVisible) {
                startAlphaAnimation(content, ALPHA_TITLE_CONTAINER_ANIMATIONS_DURATION, View.VISIBLE);
                mIsTheToolbarContainerVisible = true;
            }
        }
    }

    private void startAlphaAnimation(View v, long duration, int visibility) {
        AlphaAnimation alphaAnimation = (visibility == View.VISIBLE)
                ? new AlphaAnimation(0f, 1f)
                : new AlphaAnimation(1f, 0f);
        alphaAnimation.setDuration(duration);
        alphaAnimation.setFillAfter(true);
        v.startAnimation(alphaAnimation);
    }
}
