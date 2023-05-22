package com.tamim.task71p;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class AdvertDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "advertDatabase.db";
    private static final String ADVERT_TABLE_NAME = "advert";
    private static final int DATABASE_VERSION = 1;

    public AdvertDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE advert (id TEXT PRIMARY KEY, name TEXT, type TEXT, phone TEXT, description TEXT, date TEXT, lat TEXT, lng TEXT)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // handle any database schema changes in future versions of your app
    }

    public Boolean insertAdvert(Advert advert) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id", advert.getId());
        values.put("name", advert.getName());
        values.put("type", advert.getType().getPostTypeValue());
        values.put("phone", advert.getPhone());
        values.put("description", advert.getDescription());
        values.put("date", advert.getDate());
        values.put("lat", advert.getLat());
        values.put("lng", advert.getLng());
        long rowId = db.insert(ADVERT_TABLE_NAME, null, values);
        db.close();
        if (rowId > -1) {
            System.out.println("Insert successful. Row ID: " + rowId);
            return true;
        } else {
            System.out.println("Insert failed.");
            return false;
        }
    }

    public Advert getAdvert(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(ADVERT_TABLE_NAME, new String[] { "id", "name", "type", "phone", "description", "date", "lat", "lng" },
                "id=?", new String[] { id }, null, null, null, null);
        if (cursor != null) cursor.moveToFirst();

        Advert advert = new Advert(cursor.getString(0), cursor.getString(1), POST_TYPE.valueOf(cursor.getString(2)), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7));
        cursor.close();
        db.close();
        return advert;
    }

    public List<Advert> getAllAdverts() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(ADVERT_TABLE_NAME, null, null, null, null, null, null);
        List<Advert> result = new ArrayList<>();
        int i = 0;
        while (cursor.moveToNext()) {
            result.add(new Advert(cursor));
        }
        cursor.close();
        db.close();
        return result;
    }

    public Boolean deleteEntry(String id) {
        SQLiteDatabase db = getWritableDatabase();
        String whereClause = "id" + " = ?";
        String[] whereArgs = { id };
        int numRowsDeleted = db.delete(ADVERT_TABLE_NAME, whereClause, whereArgs);
        db.close();
        if (numRowsDeleted > 0) {
            return true;
        } else {
            return false;
        }
    }
}
