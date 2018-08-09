package com.fancycodeworks.tumbleapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.fancycodeworks.tumbleapp.R;
import com.fancycodeworks.tumbleapp.customs.OnSwipeTouchListener;
import com.fancycodeworks.tumbleapp.customs.VerticalViewPager;
import com.fancycodeworks.tumbleapp.fragments.HomePageNewsFragment;

import java.util.ArrayList;

public class MainActivity extends FragmentActivity {
    public static final int NUM_PAGES = 5;
    private VerticalViewPager pager;
    private PagerAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        DrawerLayout dw;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Button(this).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dw.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
            }
        });

        pager = (VerticalViewPager) findViewById(R.id.activity_main_viewPager);
        adapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        pager.setAdapter(adapter);

        Log.e("Main Activity", ":" + pager.getChildCount());
        pager.setOnFlingListener(new SimpleOnFlingListener());

    }

    @Override
    public void onBackPressed() {
        if(pager.getCurrentItem() == 0)
            super.onBackPressed();
        else
            pager.setCurrentItem(pager.getCurrentItem() - 1);
    }

    private class ScreenSlidePagerAdapter extends FragmentPagerAdapter {
        ArrayList<HomePageNewsFragment> fragments ;

        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
            fragments = new ArrayList<>();
            for(int i = 0; i < 10; i++){
                fragments.add(new HomePageNewsFragment(i));
            }

        }

        @Override
        public Fragment getItem(int position) {
            Log.e("Main Activity", ":" + pager.getChildCount());
            return fragments.get(position);
        }

        @Override
        public int getCount() {

            return fragments.size();
        }


    }

    private class SimpleOnFlingListener implements VerticalViewPager.OnFlingListener{

        @Override
        public boolean onSwipeLeft() {
            Intent intent = new Intent(MainActivity.this, BrowserActivity.class);
            startActivity(intent);
            return true;
        }

        @Override
        public boolean onSwipeRight() {
            Intent intent = new Intent(MainActivity.this, CategoryActivity.class);
            startActivity(intent);
            return true;
        }

        @Override
        public boolean onSwipeUp() {
            return false;
        }

        @Override
        public boolean onSwipeDown() {
            return false;
        }
    }
}
