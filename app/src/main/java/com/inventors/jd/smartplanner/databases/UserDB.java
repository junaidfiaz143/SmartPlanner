package com.inventors.jd.smartplanner.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.inventors.jd.smartplanner.models.User;

/**
 * Created by jd on 20-Apr-18.
 */

public class UserDB extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "user.db";
    private static final int DATABASE_VERSION = 1;

    public UserDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String query = "create table user(id integer primary key, name text, email text, password text)";
        sqLiteDatabase.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS user");
    }

    public long onUserInsert(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("name", user.getName());
        values.put("email", user.getEmail());
        values.put("password", user.getPassword());

        return db.insert("user", null, values);
    }

    public User onGetUser(String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        User user = null;

        String query = "select * from user where email = '" + email + "' and password = '" + password + "'";

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            user = new User();
            for (int i = 0; i < cursor.getCount(); i++) {
                user.setId(cursor.getInt(0));
                user.setName(cursor.getString(1));
                user.setEmail(cursor.getString(2));
                user.setPassword(cursor.getString(3));
            }
        }

        return user;
    }
}
