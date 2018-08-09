package com.fancycodeworks.tumbleapp.customs;

import android.content.Context;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

public class FlowLayout extends ViewGroup{
    private int deviceWidth;

    public FlowLayout(Context context) {
        this(context, null ,0 ,0);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0, 0);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context){
        final Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        Point deviceDisplay = new Point();
        display.getSize(deviceDisplay);
        deviceWidth = deviceDisplay.x;
    }

    //Is called after onMeasure()
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        final int count = getChildCount();
        int currentWidth, currentHeight, currentLeft, currentTop, maxHeight;

        final int childLeft = getPaddingLeft();
        final int childTop = getPaddingTop();
        final int childRight  = getMeasuredWidth() - getPaddingRight();
        final int childBottom = this.getMeasuredHeight() - getPaddingBottom();
        final int childWidth = childRight - childLeft;
        final int childHeight = childBottom - childTop;

        Log.e("FlowLayout", "OnLayout PaddingLeft = " + getPaddingLeft() + " PaddingRight = " + getPaddingRight() + " PaddingTop = " + getPaddingTop() + " PaddingBottom = " + getPaddingBottom());

        maxHeight = 0;
        currentLeft = childLeft;
        currentTop = childTop;

        Log.e("Flow Layout", "onLayout : width = " + getMeasuredWidth() + " height = " + getMeasuredHeight());
        for(int i = 0 ; i < count; i++){
            View child = getChildAt(i);

            //takes maximum available width and height as parameter
            //child.measure(MeasureSpec.makeMeasureSpec(childWidth, MeasureSpec.AT_MOST), MeasureSpec.makeMeasureSpec(childHeight, MeasureSpec.AT_MOST));

            currentHeight = child.getMeasuredHeight() + child.getPaddingTop() + child.getPaddingBottom();
            currentWidth = child.getMeasuredWidth() + child.getPaddingLeft() + child.getPaddingRight();

            if(currentLeft + currentWidth > childRight){
                currentLeft = childLeft;
                currentTop += maxHeight;
                maxHeight = 0;
            }

            Log.e("FlowLayout", "On Layout : Left = " + currentLeft + " Top = " + currentTop + " Width = " + currentWidth + " Height = " + currentHeight);
            child.layout(currentLeft, currentTop, currentLeft + currentWidth, currentTop + currentHeight);

            if(maxHeight < currentHeight){
                maxHeight = currentHeight;
            }
            currentLeft += currentWidth;
        }
    }

    //find and set size for all children and this view.
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int count = getChildCount();

        int height = 0, width = 0;
        int maxWidth = 0, maxHeight = 0;
        int childState = 0;
        for(int i = 0; i < count; i++)
        {
            int w, h;
            View child = getChildAt(i);
            LayoutParams lp = child.getLayoutParams();

            if(child.getVisibility() == GONE){
                continue;
            }
            child.measure(widthMeasureSpec, heightMeasureSpec);
            w = child.getMeasuredWidth() + child.getPaddingLeft() + child.getPaddingRight();
            h = child.getMeasuredHeight() + child.getPaddingTop() + child.getPaddingBottom();

            Log.e("FlowLayout", "OnMeasure width = " + child.getMeasuredWidth() + " height = " + child.getMeasuredHeight());
            //measureChild(child, widthMeasureSpec, heightMeasureSpec);
            Log.e("FlowLayout", "OnMeasure width = " + child.getMeasuredWidth() + " height = " + child.getMeasuredHeight());
            Log.e("FlowLayout", "On Measure : deviceWidth =  " + deviceWidth);

            if((width + w) <= deviceWidth){
                //Same row
                Log.e("FlowLayout" , " On Measure true");
                width += w;
                maxWidth  = Math.max(maxWidth, width);

                if(h > height){
                    maxHeight = maxHeight - height + (height = h);

                }

            } else {
                //Next Row
                Log.e("FlowLayout" , " On Measure false");
                maxWidth = Math.max(maxWidth, width);
                maxHeight += h;
                height = h;
                width = w;
            }
            //return bitwise OR of the parameters passed.
            childState = combineMeasuredStates(childState, child.getMeasuredState());

            Log.e("FlowLayout", "On Measure : width = " + width + " height = " + height + " maxW = " + maxWidth + " maxH = " + maxHeight);
        }
        maxHeight = Math.max(maxHeight, getSuggestedMinimumHeight());
        maxWidth = Math.max(maxWidth, getSuggestedMinimumWidth());

        setMeasuredDimension(
                resolveSizeAndState(maxWidth, widthMeasureSpec, childState)
               ,resolveSizeAndState(maxHeight, heightMeasureSpec, childState));
        Log.e("CategoryActivity" , "Width = " + getMeasuredWidth() + " Height = " + getMeasuredHeight());
    }


}
