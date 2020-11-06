package com.udara.developer.tha_app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "THA_DB";

    private final String TABLE_USER = "User_Infor";

    private final String COLUMN_INDEX = "_INDEX";
    private final String COLUMN_NAME = "_NAME";
    private final String COLUMN_EMAIL = "_EMAIL";
    private final String COLUMN_MOBILE = "_MOBILE";
    private final String COLUMN_GPA = "_GPA";
    private final String COLUMN_PASSWORD = "_PASSWORD";

    private final String QUERY_CREATE_USER_TABLE = String.format("CREATE TABLE %s(" +
                    "%s VARCHAR(64) PRIMARY KEY," +
                    "%s VARCHAR(64)," +
                    "%s VARCHAR(64)," +
                    "%s VARCHAR(64)," +
                    "%s REAL," +
                    "%s VARCHAR(64)" +
                    ")"
            ,
            TABLE_USER,
            COLUMN_INDEX,
            COLUMN_NAME,
            COLUMN_EMAIL,
            COLUMN_MOBILE,
            COLUMN_GPA,
            COLUMN_PASSWORD
    );

    DatabaseHandler(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(QUERY_CREATE_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String QUERY_DELETE_USER_TABLE = "DROP TABLE IF EXISTS " + TABLE_USER;
        db.execSQL(QUERY_DELETE_USER_TABLE);
        onCreate(db);
    }

    boolean register(String name, String index, String email, String mobile, String gpa, String password) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, name);
        contentValues.put(COLUMN_INDEX, index);
        contentValues.put(COLUMN_EMAIL, email);
        contentValues.put(COLUMN_MOBILE, mobile);
        contentValues.put(COLUMN_GPA, gpa);
        contentValues.put(COLUMN_PASSWORD, password);
        return db.insert(TABLE_USER, null, contentValues) != -1;
    }

    UserProfile login(String index, String password) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_USER,
                null,
                String.format("%s = '%s' AND %s = '%s'", COLUMN_INDEX, index, COLUMN_PASSWORD, password),
                null,
                null,
                null,
                null
        );
        UserProfile userProfile = null;
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            userProfile = new UserProfile(
                    cursor.getString(cursor.getColumnIndex(COLUMN_INDEX)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_NAME)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_MOBILE)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_GPA))
            );
        }
        cursor.close();
        return userProfile;
    }
}
