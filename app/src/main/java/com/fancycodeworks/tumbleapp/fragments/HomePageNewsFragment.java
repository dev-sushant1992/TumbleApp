package com.fancycodeworks.tumbleapp.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fancycodeworks.tumbleapp.R;


public class HomePageNewsFragment extends Fragment {
    private int id;

    public HomePageNewsFragment(int id) {
        this.id = id;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_main_article, container, false);
        TextView related = (TextView)rootView.findViewById(R.id.fragment_home_related);
        related.setText(related.getText().toString() + id);
        return rootView;
    }
}
