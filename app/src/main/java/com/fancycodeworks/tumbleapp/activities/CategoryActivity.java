package com.fancycodeworks.tumbleapp.activities;

import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fancycodeworks.tumbleapp.R;
import com.fancycodeworks.tumbleapp.customs.FlowLayout;
import com.fancycodeworks.tumbleapp.customs.PieChart;

import java.util.Random;

public class CategoryActivity extends BaseAuthenticatedActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        /*Resources res = getResources();
        final PieChart pie = (PieChart) this.findViewById(R.id.Pie);
        pie.addItem("Agamemnon", 2, res.getColor(R.color.seafoam));
        pie.addItem("Bocephus", 3.5f, res.getColor(R.color.chartreuse));
        pie.addItem("Calliope", 2.5f, res.getColor(R.color.emerald));
        pie.addItem("Daedalus", 3, res.getColor(R.color.bluegrass));
        pie.addItem("Euripides", 1, res.getColor(R.color.turquoise));
        pie.addItem("Ganymede", 3, res.getColor(R.color.slate));

        ((Button) findViewById(R.id.Reset)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                pie.setCurrentItem(0);
            }
        });*/

        FlowLayout flowLayout = (FlowLayout) findViewById(R.id.activity_category_flowLayout);
        Random random = new Random();
        for (int i = 0; i < 6 ; i++){
            LinearLayout layout = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.activity_category_selector,flowLayout,false);
            layout.setPadding(4,8,4,8);
            ((ImageView)layout.findViewById(R.id.activity_category_image)).setImageResource(R.drawable.ic_file_download_black_24dp);

            if(i == 3) {
                ((ImageView) layout.findViewById(R.id.activity_category_circle)).setImageResource(R.drawable.filled_circle);
            }

            ((TextView) layout.findViewById(R.id.activity_category_text)).setText("My Feed " + i);
            flowLayout.addView(layout);
        }
    }
}
