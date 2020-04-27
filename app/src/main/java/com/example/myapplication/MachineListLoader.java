package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

public class MachineListLoader extends AsyncTaskLoader {

    private String url="https://papimo.jp/h/00041817/hit/index_machine/1-20-260540/";
    Context context;

    public MachineListLoader(@NonNull Context context) {
        super(context);
        this.context=context;
        forceLoad();
    }

    @Nullable
    @Override
    public SQLiteDatabase loadInBackground() {
        try{
            DatabaseHelper dbHelper = new DatabaseHelper(context,"slotdb3",null,1);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            Document document = Jsoup.connect(this.url).timeout(10000).get();
            String maxpage=document.select("#max_page").val();
            for(int i=0;i < Integer.parseInt(maxpage);i++){
                document=Jsoup.connect(this.url+"?page="+String.valueOf((i+1))).timeout(10000).get();
                Elements elements=document.select(".item li a");
                for (Element element : elements) {
                    int indexname = element.select(".name").text().indexOf("台") + 1;
                    int indexurl = element.attr("href").indexOf("index_sort")+11;
                    ContentValues val = new ContentValues();
                    val.put("name",element.select(".name").text().substring(indexname));
                    val.put("URL",element.attr("href").substring(indexurl,indexurl+9));
                    //String s= element.attr("href").substring(indexurl,indexurl+9);
                    db.insert("minoasamachine",null,val);
//                    this.machinehashmap.put(element.select(".name").text().substring(index),element.attr("href"));

                }
            }


            //String s = searchByAge(db);
            //System.out.println("aa");
            return db;
        }catch(IOException e){
            return null;
        }finally {

        }
    }

/*
    private void doAddEntry(SQLiteDatabase db, String date, int machineNo, String machineName, int medal, int BB, int RB, int totalGames, int last){
        ContentValues val = new ContentValues();
        val.put("date",date);
        val.put("machineNo",machineNo);
        val.put("machineName",machineName);
        val.put("medal",medal);
        val.put("BB",BB);
        val.put("RB",RB);
        val.put("totalGames",totalGames);
        val.put("last",last);
        db.insert("minoasatable",null,val);
    }

    private String searchByAge(SQLiteDatabase db){
        Cursor cursor=null;
        try{
            cursor = db.query("minoasatable",
                    new String[]{"date,machineNo"},
                    null,
                    null,
                    null,
                    null,
                    null);
            return readCursor(cursor);

        }finally {
            if(cursor !=null){
                cursor.close();
            }
        }
    }

    private String readCursor(Cursor cursor){
        String result="";
        int indexDate = cursor.getColumnIndex("date");
        int indexMachineNo = cursor.getColumnIndex("machineNo");

        while (cursor.moveToNext()){
            String date= cursor.getString(indexDate);
            int machineNo = cursor.getInt(indexMachineNo);
            result+=date+machineNo;
        }
        return result;
    }*/
}
