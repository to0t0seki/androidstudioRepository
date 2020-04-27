package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int MATCH_PARENT = ViewGroup.LayoutParams.MATCH_PARENT;
        int WRAP_CONTENT = ViewGroup.LayoutParams.WRAP_CONTENT;

        //ベースレイアウト
        LinearLayout baselayout = new LinearLayout(this);
        baselayout.setOrientation(LinearLayout.VERTICAL);
        setContentView(baselayout);
        baselayout.setId(View.generateViewId());

        //トップレイアウト
        LinearLayout layout1 = new LinearLayout(this);
        layout1.setLayoutParams(new LinearLayout.LayoutParams(MATCH_PARENT, 120));
        baselayout.addView(layout1);

        //センターレイアウト
        CenterFragment centerFragment = new CenterFragment();
        FragmentTransaction fragmentTransaction1 = getSupportFragmentManager().beginTransaction();
        fragmentTransaction1.add(baselayout.getId(),centerFragment);
        fragmentTransaction1.commit();


        //ボトムレイアウト
        BottomFragment bottomFragment = new BottomFragment();
        FragmentTransaction fragmentTransaction2 = getSupportFragmentManager().beginTransaction();
        fragmentTransaction2.add(baselayout.getId(),bottomFragment);
        fragmentTransaction2.commit();


        baselayout.setBackgroundColor(Color.WHITE);
        layout1.setBackgroundColor(Color.BLUE);




    }
}
