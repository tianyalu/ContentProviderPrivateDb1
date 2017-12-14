package com.sty.content.provider.private1.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Steven.T on 2017/12/14/0014.
 */

public class MyOpenHelper extends SQLiteOpenHelper {
    public MyOpenHelper(Context context) {
        /**
         * name: 数据库名称
         * factory: 游标工厂
         * version: 版本
         */
        super(context, "account.db", null, 1);
    }

    /**
     * 当数据库第一次创建的时候调用，这个方法适合做表结构的初始化
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql_create = "create table info(_id integer primary key autoincrement, name varchar(20), money varchar(20))";
        db.execSQL(sql_create);
        String sql_insert = "insert into info(name, money) values(?, ?)";
        db.execSQL(sql_insert, new String[]{"张三", "2000"});
        db.execSQL(sql_insert, new String[]{"李四","3000"});
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
