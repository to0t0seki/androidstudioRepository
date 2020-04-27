package com.example.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;


public class CenterFragment extends Fragment implements LoaderManager.LoaderCallbacks<SQLiteDatabase> {

    int MATCH_PARENT = ViewGroup.LayoutParams.MATCH_PARENT;
    LinearLayout linearLayout;
    Cursor cursor=null;




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        HorizontalScrollView horizontalScrollView = new HorizontalScrollView(getActivity());
        linearLayout = new LinearLayout(getActivity());
        horizontalScrollView.addView(linearLayout);

        horizontalScrollView.setLayoutParams(new ViewGroup.LayoutParams(MATCH_PARENT,120));
        container.addView(horizontalScrollView);
        horizontalScrollView.setBackgroundColor(Color.RED);
        LoaderManager loaderManager = getLoaderManager();
        Bundle bundle = new Bundle();
        loaderManager.initLoader(0,bundle,this);


        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @NonNull
    @Override
    public Loader onCreateLoader(int id, @Nullable Bundle args) {

        return new MachineListLoader(getActivity());
    }

    @Override
    public void onLoadFinished(@NonNull Loader loader, SQLiteDatabase db) {
        System.out.println("finish");
        //Cursor cursor=null;
        try{
            cursor = db.query("minoasamachine",
                    new String[]{"name","URL"},
                    null,
                    null,
                    null,
                    null,
                    null);

            int indexName = cursor.getColumnIndex("name");
            final int indexURL = cursor.getColumnIndex("URL");

            while (cursor.moveToNext()){
                Button button=new Button(getActivity());
                button.setText(cursor.getString(indexName));
                button.setTextSize(8);
                String s = cursor.getString(indexURL);
                                button.setOnClickListener(new View.OnClickListener() {
                                              @Override
                                              public void onClick(View v) {
                                                  v.setBackgroundColor(Color.RED);
                                                 // Log.d(cursor.getString(indexURL), "onClick: ");
                                              }
                                          });
                        //button.setWidth(1000);
                        linearLayout.addView(button);
            }
        }finally {
            if(cursor !=null){
                cursor.close();
            }
        }

    }

    @Override
    public void onLoaderReset(@NonNull Loader loader) {

    }

}
