package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_main);
        int MATCH_PARENT = ViewGroup.LayoutParams.MATCH_PARENT;
        int WRAP_CONTENT = ViewGroup.LayoutParams.WRAP_CONTENT;

        LinearLayout baselayout = new LinearLayout(this);
        CenterFragment centerFragment = new CenterFragment();
        Database dbHelper = new Database(this,"slotdb",null,1);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        doAddEntry(db,"aaa",129,"bbb",100,10,20,30,40);
        String s = searchByAge(db);
        System.out.println("aa");


    }
    private void doAddEntry(SQLiteDatabase db,String date,int machineNo,String machineName,int medal,int BB,int RB,int totalGames,int last){
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
    }
}
