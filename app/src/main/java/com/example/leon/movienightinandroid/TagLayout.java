package com.example.leon.movienightinandroid;

/**
 * Created by Leon on 2/19/2018.
 */

import android.content.Context;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

/**
 * Created by Leon on 19.2.2018..
 */

public class TagLayout extends ViewGroup {
    int deviceWidth;

    public TagLayout(Context context) {
        this(context, null, 0);
    }

    public TagLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TagLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        final Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        Point deviceDisplay = new Point();
        display.getSize(deviceDisplay);
        deviceWidth = deviceDisplay.x;
    }

    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {

        int childWidth, childHeight, curLeft, curTop, curBottom, maxHeight;

        final int rootLeft = getPaddingLeft();
        final int rootRight = getMeasuredWidth() - getPaddingRight();

        final int rootWidth = getMeasuredWidth() - (getPaddingLeft() + getPaddingRight());
        final int rootHeight = getMeasuredHeight() - (getPaddingTop() + getPaddingBottom());

        final int rootTop = getPaddingTop();
        final int rootBottom = getMeasuredHeight() - getPaddingBottom();


        maxHeight = rootHeight;
        curLeft = rootLeft;
        curTop = rootTop;
        curBottom = rootBottom;


        for (int l = 0; l < getChildCount(); l++) {
            View child = getChildAt(l);

            if (child.getVisibility() == GONE) return;

            child.measure(MeasureSpec.makeMeasureSpec(rootWidth, MeasureSpec.AT_MOST), MeasureSpec.makeMeasureSpec(rootHeight, MeasureSpec.AT_MOST));
            childWidth = child.getMeasuredWidth();
            childHeight = child.getMeasuredHeight();

            if (curLeft + childWidth >= rootRight) {


                // new row
                curLeft = rootLeft;
                curTop += childHeight;

            }


            //do the layout
            child.layout(curLeft, curTop, curLeft + childWidth, curTop + childHeight);
            //store the max height

            curLeft += childWidth;


        }
    }




    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int count = getChildCount();
        // Measurement will ultimately be computing these values.
        int maxHeight = 0;

        int childState = 0;
        int sumWidth = 0;
        int rowCount = 0;


      /* int widthMode = MeasureSpec.getMode(widthMeasureSpec);
       int widthSize = MeasureSpec.getSize(widthMeasureSpec);
       int heightMode = MeasureSpec.getMode(heightMeasureSpec);
       int heightSize = MeasureSpec.getSize(heightMeasureSpec);

       //Measure Width
       if (widthMode == MeasureSpec.EXACTLY) {
           //Must be this size
           System.out.println("EXACTLY");
       } else if (widthMode == MeasureSpec.AT_MOST) {
           //Can't be bigger than...
           System.out.println("AT_MOST");
       } else {
           //Be whatever you want
           System.out.println("else");
       }*/

        // Iterate through all children, measuring them and computing our dimensions
        // from their size.
        for (int i = 0; i < count; i++) {
            final View child = getChildAt(i);

            if (child.getVisibility() == GONE)
                continue;

            // Measure the child.
            measureChild(child, widthMeasureSpec, heightMeasureSpec);

            sumWidth += child.getMeasuredWidth();

            if ((sumWidth / deviceWidth) > rowCount) {
                maxHeight += child.getMeasuredHeight();
                rowCount++;

            } else {
                maxHeight = Math.max(maxHeight, child.getMeasuredHeight());
            }
            childState = combineMeasuredStates(childState, child.getMeasuredState());
        }

        // Check against our minimum height and width
        maxHeight = Math.max(maxHeight, getSuggestedMinimumHeight());


        // Report our final dimensions.
        setMeasuredDimension(resolveSizeAndState(500, widthMeasureSpec, childState), resolveSizeAndState(500, heightMeasureSpec, childState << MEASURED_HEIGHT_STATE_SHIFT));
    }
}
