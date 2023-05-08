package com.tamim.task71p;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AdvertDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "advertDatabase.db";
    private static final String ADVERT_TABLE_NAME = "advert";
    private static final int DATABASE_VERSION = 1;

    public AdvertDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE advert (id TEXT PRIMARY KEY, name TEXT, type TEXT, phone TEXT, description TEXT, date TEXT, location TEXT)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // handle any database schema changes in future versions of your app
    }

    public void insertPerson(Advert advert) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id", advert.getId());
        values.put("name", advert.getName());
        values.put("type", advert.getType().getPostTypeValue());
        values.put("phone", advert.getPhone());
        values.put("description", advert.getDescription());
        values.put("date", advert.getDate());
        values.put("location", advert.getLocation());
        db.insert("advert", null, values);
        db.close();
    }

    public Advert getAdvert(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(ADVERT_TABLE_NAME, new String[] { "id", "name", "type", "phone", "description", "date", "location" },
                "id=?", new String[] { id }, null, null, null, null);
        if (cursor != null) cursor.moveToFirst();

        Advert advert = new Advert(cursor.getString(0), cursor.getString(1), POST_TYPE.valueOf(cursor.getString(2)), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6));
        cursor.close();
        db.close();
        return advert;
    }

    public Advert[] getAllAdverts() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(ADVERT_TABLE_NAME, null, null, null, null, null, null);
        Advert[] result = new Advert[cursor.getCount()];
        int i = 0;
        while (cursor.moveToNext()) {
            result[i++] = new Advert(cursor);
        }
        cursor.close();
        return result;
    }

    public Boolean deleteEntry(String id) {
        SQLiteDatabase db = getWritableDatabase();
        String whereClause = "id" + " = ?";
        String[] whereArgs = { id };
        int numRowsDeleted = db.delete(ADVERT_TABLE_NAME, whereClause, whereArgs);
        if (numRowsDeleted > 0) {
            return true;
        } else {
            return false;
        }
    }
}
