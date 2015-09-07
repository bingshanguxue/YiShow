package com.bingshanguxue.yishow.app.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.util.Log;
import android.view.View;

import com.bingshanguxue.yishow.R;
import com.bingshanguxue.yishow.observablescrollview.ObservableScrollView;
import com.bingshanguxue.yishow.observablescrollview.ObservableScrollViewCallbacks;
import com.bingshanguxue.yishow.observablescrollview.ScrollState;
import com.bingshanguxue.yishow.observablescrollview.ScrollUtils;
import com.nineoldandroids.view.ViewHelper;
import com.nineoldandroids.view.ViewPropertyAnimator;


public class ObservScrollViewActivity extends Activity implements ObservableScrollViewCallbacks {

    private static final String TAG = "Nat " +  ObservScrollViewActivity.class.getSimpleName();

    private View mHeaderView;
    private View mToolbarView;
    private ObservableScrollView mScrollView;

    private int mBaseTranslationY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stickyheaderscrollview);

        mHeaderView = findViewById(R.id.header);
        ViewCompat.setElevation(mHeaderView, getResources().getDimension(R.dimen.toolbar_elevation));

        mToolbarView = findViewById(R.id.toolbar);

        mScrollView = (ObservableScrollView) findViewById(R.id.scroll);
        mScrollView.setScrollViewCallbacks(this);
    }


    @Override
    public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {
        Log.v(TAG, "onScrollChanged: scrollY: " + scrollY + " firstScroll: " + firstScroll + " dragging: " + dragging);
        if (dragging) {
            int toolbarHeight = mToolbarView.getHeight();
            if (firstScroll) {
                float currentHeaderTranslationY = ViewHelper.getTranslationY(mHeaderView);
                if (-toolbarHeight < currentHeaderTranslationY) {
                    mBaseTranslationY = scrollY;
                }
            }
            float headerTranslationY = ScrollUtils.getFloat(-(scrollY - mBaseTranslationY), -toolbarHeight, 0);
            ViewPropertyAnimator.animate(mHeaderView).cancel();
            ViewHelper.setTranslationY(mHeaderView, headerTranslationY);
        }
    }

    @Override
    public void onDownMotionEvent() {
        Log.v(TAG, "onDownMotionEvent");

    }

    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {
        Log.v(TAG, "onUpOrCancelMotionEvent: scrollState: " + scrollState);
        mBaseTranslationY = 0;

        if (scrollState == ScrollState.DOWN) {
            showToolbar();
        } else if (scrollState == ScrollState.UP) {
            int toolbarHeight = mToolbarView.getHeight();
            int scrollY = mScrollView.getCurrentScrollY();
            if (toolbarHeight <= scrollY) {
                hideToolbar();
            } else {
                showToolbar();
            }
        } else {
            // Even if onScrollChanged occurs without scrollY changing, toolbar should be adjusted
            if (!toolbarIsShown() && !toolbarIsHidden()) {
                // Toolbar is moving but doesn't know which to move:
                // you can change this to hideToolbar()
                showToolbar();
            }
        }
    }

    private boolean toolbarIsShown() {
        return ViewHelper.getTranslationY(mHeaderView) == 0;
    }

    private boolean toolbarIsHidden() {
        return ViewHelper.getTranslationY(mHeaderView) == -mToolbarView.getHeight();
    }

    private void showToolbar() {
        float headerTranslationY = ViewHelper.getTranslationY(mHeaderView);
        if (headerTranslationY != 0) {
            ViewPropertyAnimator.animate(mHeaderView).cancel();
            ViewPropertyAnimator.animate(mHeaderView).translationY(0).setDuration(200).start();
        }
    }

    private void hideToolbar() {
        float headerTranslationY = ViewHelper.getTranslationY(mHeaderView);
        int toolbarHeight = mToolbarView.getHeight();
        if (headerTranslationY != -toolbarHeight) {
            ViewPropertyAnimator.animate(mHeaderView).cancel();
            ViewPropertyAnimator.animate(mHeaderView).translationY(-toolbarHeight).setDuration(200).start();
        }
    }

}
