package com.sty.content.provider.private1;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.sty.content.provider.private1.db.MyOpenHelper;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        queryDatabase();
    }

    private void queryDatabase(){
        MyOpenHelper helper = new MyOpenHelper(getApplicationContext());
        SQLiteDatabase db = helper.getReadableDatabase();

        //取出数据
        Cursor cursor = db.query("info", null, null, null, null, null, null);
        if(cursor != null && cursor.getCount() > 0){
            while(cursor.moveToNext()){
                String name = cursor.getString(1);
                String money = cursor.getString(2);

                Log.i("Tag", "name: " + name + "    money: " + money);
            }
            cursor.close();
        }
    }
}
