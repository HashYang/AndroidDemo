package com.chen.demo.viewdraghelper;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * Created by Chen on 15/12/7.
 */
public class ViewDragHelperPanel extends RelativeLayout {
    private static final int MIN_DRAG_OFFSET = 4;
    /**
     * 当前拖动的方向
     */
    public static final int NONE = 1 << 0;
    public static final int HORIZONTAL = 1 << 1;
    public static final int VERTICAL = 1 << 2;
    private View mView1;
    private View mView2;
    private ViewDragHelper mDragHelper;
    private int mDownX;
    private int mDownY;

    private int mLastX;
    private int mLastY;


    /**
     * 当前拖动的方向
     */
    private int mDragDirect = NONE;


    public ViewDragHelperPanel(Context context) {
        super(context);
    }

    public ViewDragHelperPanel(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ViewDragHelperPanel(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mDragHelper = ViewDragHelper.create(this, new ViewDragHelper.Callback() {
            @Override
            public boolean tryCaptureView(View child, int pointerId) {
                return child == mView1 || child == mView2;
            }

            @Override
            public int clampViewPositionHorizontal(View child, int left, int dx) {
                Log.d("DragLayout", "clampViewPositionHorizontal " + left + "," + dx);
                final int leftBound = getPaddingLeft();
                final int rightBound = getWidth() - mView1.getWidth();
                final int newLeft = Math.min(Math.max(left, leftBound), rightBound);
                return newLeft;
            }

            @Override
            public int clampViewPositionVertical(View child, int top, int dy) {
                if (child == mView2) {
                    return 0;
                }
                final int topBound = getPaddingStart();
                final int bottomBound = getHeight() - mView1.getHeight();
                final int newtop = Math.min(Math.max(top, topBound), bottomBound);
                return newtop;
            }

            @Override
            public void onEdgeTouched(int edgeFlags, int pointerId) {
                super.onEdgeTouched(edgeFlags, pointerId);
            }

            @Override
            public void onViewReleased(View releasedChild, float xvel, float yvel) {
                super.onViewReleased(releasedChild, xvel, yvel);
                int left = 0;
                int top = 0;
                if (mDragDirect == VERTICAL) {
                    if (Math.abs(xvel) < MIN_DRAG_OFFSET * releasedChild.getHeight()) {
                        top = mLastY;
                        left = releasedChild.getLeft();
                    } else if (yvel < 0) {
                        left = releasedChild.getLeft();
                        top = 0;
                    } else {
                        left = releasedChild.getLeft();
                        top = getBottom() - releasedChild.getHeight();
                    }
                } else if (mDragDirect == HORIZONTAL) {
                    if (Math.abs(xvel) < MIN_DRAG_OFFSET * releasedChild.getWidth()) {
                        left = mLastX;
                        top = releasedChild.getTop();
                    } else if (xvel < 0) {
                        left = 0;
                        top = releasedChild.getTop();
                    } else {
                        left = getRight() - releasedChild.getWidth();
                        top = releasedChild.getTop();
                    }
                }
                slideTo(left, top);
                mDragDirect = NONE;

            }
        });
        mDragHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_ALL);
    }

    @Override
    public void computeScroll() {
        if (mDragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mView1 = findViewById(R.id.view1);
        mView2 = findViewById(R.id.view2);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        final int action = MotionEventCompat.getActionMasked(ev);
        if (action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_UP) {
            mDragHelper.cancel();
            return false;
        }
        return mDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int event = MotionEventCompat.getActionMasked(ev);
        switch (event) {
            case MotionEvent.ACTION_DOWN:
                mDownX = (int) ev.getX();
                mDownY = (int) ev.getY();
                break;

            case MotionEvent.ACTION_MOVE:
                if (mDragDirect == NONE) {
                    int dx = Math.abs(mDownX - (int) ev.getX());
                    int dy = Math.abs(mDownY - (int) ev.getY());
                    int slop = mDragHelper.getTouchSlop();

                    if (Math.sqrt(dx * dx + dy * dy) >= slop) {
                        if (dy >= dx)
                            mDragDirect = VERTICAL;
                        else
                            mDragDirect = HORIZONTAL;
                    }
                }

                break;
            case MotionEvent.ACTION_UP: {
                if (mDragDirect == NONE) {
                    int dx = Math.abs(mDownX - (int) ev.getX());
                    int dy = Math.abs(mDownY - (int) ev.getY());
                    int slop = mDragHelper.getTouchSlop();
                    if (Math.sqrt(dx * dx + dy * dy) >= slop) {
                        if (dy >= dx)
                            mDragDirect = VERTICAL;
                        else
                            mDragDirect = HORIZONTAL;
                    }
                }
            }
            break;
        }
        mDragHelper.processTouchEvent(ev);

        return true;
    }

    private void slideTo(int left, int top) {
        mLastX = left;
        mLastY = top;
        if (mDragHelper.smoothSlideViewTo(mView1, left, top)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }


}

