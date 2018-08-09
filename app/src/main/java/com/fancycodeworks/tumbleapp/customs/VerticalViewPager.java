package com.fancycodeworks.tumbleapp.customs;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

public class VerticalViewPager extends ViewPager {

    private GestureDetector gestureDetector;
    private OnFlingListener onFlingListener;

    public void setOnFlingListener(OnFlingListener onFlingListener) {
        this.onFlingListener = onFlingListener;
    }

    public VerticalViewPager(Context context) {
        super(context);
    }

    public VerticalViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        gestureDetector = new GestureDetector(context, new GestureListener());
        setPageTransformer(true, new VerticalPageTransformer());
        setOverScrollMode(OVER_SCROLL_NEVER);
    }

    public interface OnFlingListener{
        boolean onSwipeLeft();
        boolean onSwipeRight();
        boolean onSwipeUp();
        boolean onSwipeDown();
    }

    private class VerticalPageTransformer implements ViewPager.PageTransformer {
        private static final float MIN_SCALE = 0.75f;
        @Override
        public void transformPage(View page, float position) {
            int pageWidth = page.getWidth();

            if(position < -1) {
                page.setAlpha(1);
            } else if (position <= 0){
                page.setAlpha(1);
                page.setTranslationX(pageWidth * -position);

                float yPosition = position * page.getHeight();
                page.setTranslationY(yPosition);
                page.setScaleX(1);
                page.setScaleY(1);
            } else if(position <= 1){

                page.setAlpha(1 - position);
                page.setTranslationX(pageWidth * -position);

                float scaleFactor = MIN_SCALE + (1 - MIN_SCALE) * (1 - Math.abs(position));

                page.setScaleX(scaleFactor);
                page.setScaleY(scaleFactor);
            } else{
                setAlpha(1);
            }
        }
    }

    private static final int SWIPE_THRESHOLD = 100;
    private static final int SWIPE_VELOCITY_THRESHOLD = 100;

    private final class GestureListener extends GestureDetector.SimpleOnGestureListener{
        @Override
        public boolean onDown(MotionEvent e) {
            return false;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            boolean result = false;
            if(onFlingListener == null)
                return false;
            try{
                float diffY = e2.getY() - e1.getY();
                float diffX = e2.getX() - e1.getX();

                if(Math.abs(diffX) > Math.abs(diffY)){
                    if(Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD){
                        if(diffX > 0)
                            result = onFlingListener.onSwipeRight();
                        else
                            result = onFlingListener.onSwipeLeft();
                    }
                } else if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD){
                    if(diffY > 0)
                        result = onFlingListener.onSwipeDown();
                    else
                        result = onFlingListener.onSwipeUp();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            return result;
        }
    }

    private MotionEvent swapXY(MotionEvent event){
        float width = getWidth();
        float height = getHeight();

        float newX = (event.getY()/height) * width;
        float newY = (event.getX()/width) * height;

        event.setLocation(newX, newY);
        return event;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        boolean intercepted = super.onInterceptTouchEvent(swapXY(event));
        swapXY(event);
        return intercepted;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gestureDetector.onTouchEvent(event);
        return super.onTouchEvent(swapXY(event));
    }
}
