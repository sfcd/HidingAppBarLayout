package com.sfcd.hidingappbar;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

public class HidingAppBarLayoutBehavior extends AppBarLayout.Behavior {
    View firstAppBarView;
    ValueAnimator mAnimator = null;


    public HidingAppBarLayoutBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onLayoutChild(CoordinatorLayout parent, AppBarLayout child, int layoutDirection) {
        boolean superLayout = super.onLayoutChild(parent, child, layoutDirection);

        this.firstAppBarView = child.getChildAt(0);

        return superLayout;
    }

    private void removeAnimator() {
        if (mAnimator != null) {
            mAnimator.cancel();
            mAnimator = null;
        }
    }

    private static int getStatusBarHeight(Context ctx) {
        int result = 0;
        int resourceId = ctx.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = ctx.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    private int getHiddenHeight() {
        int res = 0;
        if (ViewCompat.getFitsSystemWindows(firstAppBarView)) {
            res -= getStatusBarHeight(firstAppBarView.getContext());
        }

        res += firstAppBarView.getHeight();

        return res;
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, AppBarLayout child, View directTargetChild, View target, int nestedScrollAxes) {
        boolean nestedScrollStarted = super.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target, nestedScrollAxes);
        if (nestedScrollStarted) {
            removeAnimator();
        }
        return nestedScrollStarted;
    }

    @Override
    public void onStopNestedScroll(final CoordinatorLayout coordinatorLayout, AppBarLayout child, View target) {
        super.onStopNestedScroll(coordinatorLayout, child, target);
        onTouchEnded();
    }

    @Override
    public boolean onNestedFling(CoordinatorLayout coordinatorLayout, AppBarLayout child, View target, float velocityX, float velocityY, boolean consumed) {
        int offset = getTopAndBottomOffset();
        if (-offset < getHiddenHeight() && velocityY < 0 && !consumed) {
            return false;
        } else {
            return super.onNestedFling(coordinatorLayout, child, target, velocityX, velocityY, consumed);
        }
    }

    @Override
    public boolean onTouchEvent(CoordinatorLayout parent, AppBarLayout child, MotionEvent ev) {
        boolean clearAnim = false;
        switch (MotionEventCompat.getActionMasked(ev)) {
            case MotionEvent.ACTION_UP: {
                onTouchEnded();
                clearAnim = true;
            }
        }

        if(!clearAnim) {
            super.onTouchEvent(parent, child, ev);
        }

        return true;
    }

    private void onTouchEnded() {
        int offset = getTopAndBottomOffset();

        if (-offset < getHiddenHeight()) {
            if (mAnimator == null) {
                mAnimator = new ValueAnimator();
                mAnimator.setInterpolator(new DecelerateInterpolator());
                mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        setTopAndBottomOffset((int) animation.getAnimatedValue());
                    }
                });
                mAnimator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mAnimator = null;
                    }
                });
                mAnimator.setIntValues(offset, -getHiddenHeight());
                mAnimator.start();
            }

        }

    }
}
