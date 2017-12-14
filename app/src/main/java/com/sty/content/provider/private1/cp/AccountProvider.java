package com.sty.content.provider.private1.cp;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.sty.content.provider.private1.db.MyOpenHelper;

/**
 * Created by Steven.T on 2017/12/14/0014.
 */

public class AccountProvider extends ContentProvider {

    //1.定义一个Uri路径匹配器
    private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    private static final int INSERT_SUCCESS = 0;
    private static final int DELETE_SUCCESS = 1;
    private static final int UPDATE_SUCCESS = 2;
    private static final int QUERY_SUCCESS = 3;

    private MyOpenHelper helper;

    //2.创建一个静态代码块，在这个里面添加uri
    static{
        /**
         * authority: 注意和清单文件中定义的一样  com.sty.provider/query
         */
        sURIMatcher.addURI("com.sty.provider", "insert", INSERT_SUCCESS);
        sURIMatcher.addURI("com.sty.provider", "delete", DELETE_SUCCESS);
        sURIMatcher.addURI("com.sty.provider", "update", UPDATE_SUCCESS);
        sURIMatcher.addURI("com.sty.provider", "query", QUERY_SUCCESS);
    }

    //内容提供者初始化时会执行这个方法
    @Override
    public boolean onCreate() {
        //3.初始化MyOpenHelper对象 就可以获得SQLiteDatabase对象，从而对数据库进行操作
        helper = new MyOpenHelper(getContext());
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        int code = sURIMatcher.match(uri);
        if(code == QUERY_SUCCESS){ //说明路径匹配成功
            SQLiteDatabase db = helper.getReadableDatabase();
            //调用query方法
            Cursor cursor = db.query("info", projection, selection, selectionArgs, null, null, sortOrder);
            //db.close(); //关闭数据库会报错
            //※ 注意：这个cursor 不能关闭
            return cursor;
        }else { //说明路径不匹配
            //return null;
            throw new IllegalArgumentException("哥们：uri路径不匹配，请检查路径");
        }
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        int code = sURIMatcher.match(uri);
        if(code == INSERT_SUCCESS){
            SQLiteDatabase db = helper.getReadableDatabase();
            long insert = db.insert("info", null, values);

            Uri uri2 = Uri.parse("com.sty.insert/" + insert);
            db.close(); //关闭数据库

            return uri2;

        }else{
            //return null;
            throw new IllegalArgumentException("哥们：uri路径不匹配，请检查路径");
        }
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int code = sURIMatcher.match(uri);
        if(code == DELETE_SUCCESS){
            SQLiteDatabase db = helper.getReadableDatabase();

            //代表影响的行数
            int delete = db.delete("info", selection, selectionArgs);

            return delete;
        }
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        int code = sURIMatcher.match(uri);
        if(code == UPDATE_SUCCESS){
            SQLiteDatabase db = helper.getReadableDatabase();

            //代表影响的行数
            int update = db.update("info", values, selection, selectionArgs);

            return update;
        }else{
            throw new IllegalArgumentException("哥们：uri路径不匹配，请检查路径");
        }
    }
}
