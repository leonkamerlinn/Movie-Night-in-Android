package com.example.leon.movienightinandroid.utils;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Leon on 27.5.2016..
 */

public class RecyclerViewTouchListener implements RecyclerView.OnItemTouchListener {
    private final Context mContext;
    private final RecyclerView mRecyclerView;
    private GestureDetector mGestureDetector;
    private static final int SWIPE_THRESHOLD = 100;
    private static final int SWIPE_VELOCITY_THRESHOLD = 100;


    public RecyclerViewTouchListener(final Context context, final RecyclerView recyclerView) {
        mContext = context;
        mRecyclerView = recyclerView;
        mGestureDetector = new GestureDetector(mContext, new GestureListener());
    }


    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        View child = rv.findChildViewUnder(e.getX(), e.getY());
        int position = rv.getChildAdapterPosition(child);
        mGestureDetector.onTouchEvent(e);

        if (e.getAction() == MotionEvent.ACTION_MOVE) {
            onMove(child, position, e);
        }



        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }


    private class GestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onDown(MotionEvent e) {
            View child = mRecyclerView.findChildViewUnder(e.getX(), e.getY());
            int position = mRecyclerView.getChildAdapterPosition(child);
            RecyclerViewTouchListener.this.onDown(child, position, e);
            return super.onDown(e);
        }

        @Override
        public boolean onContextClick(MotionEvent e) {
            return super.onContextClick(e);
        }


        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            View child = mRecyclerView.findChildViewUnder(e.getX(), e.getY());
            int position = mRecyclerView.getChildAdapterPosition(child);
            RecyclerViewTouchListener.this.onSingleTapUp(child, position);
            return super.onSingleTapUp(e);
        }

        @Override
        public void onLongPress(MotionEvent e) {
            View child = mRecyclerView.findChildViewUnder(e.getX(), e.getY());
            int position = mRecyclerView.getChildAdapterPosition(child);
            RecyclerViewTouchListener.this.onLongPress(child, position);
            super.onLongPress(e);
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            boolean result = false;
            View child = mRecyclerView.findChildViewUnder(e1.getX(), e1.getY());
            int position = mRecyclerView.getChildAdapterPosition(child);
            try {
                float diffY = e2.getY() - e1.getY();
                float diffX = e2.getX() - e1.getX();
                if (Math.abs(diffX) > Math.abs(diffY)) {
                    if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffX > 0) {
                            onSwipeRight(child, position);
                        } else {
                            onSwipeLeft(child, position);
                        }
                    }
                    result = true;
                }
                else if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                    if (diffY > 0) {
                        onSwipeBottom(child, position);
                    } else {
                        onSwipeTop(child, position);
                    }
                }
                result = true;

            } catch (Exception exception) {
                exception.printStackTrace();
            }
            return result;
        }
    }

    public void onLongPress(View child, int position) {
    }

    public void onSingleTapUp(View child, int position) {
    }

    public void onDown(View child, int position, MotionEvent event) {
    }

    public void onMove(View child, int position, MotionEvent event) {
    }

    public void onSwipeRight(View child, int position) {
    }

    public void onSwipeLeft(View child, int position) {
    }

    public void onSwipeTop(View child, int position) {
    }

    public void onSwipeBottom(View child, int position) {
    }


}