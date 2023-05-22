package com.tamim.task71p;

import android.database.Cursor;

import java.io.Serializable;
import java.util.UUID;

public class Advert implements Serializable {
    private String id;
    private POST_TYPE type;
    private String name;
    private String phone;
    private String description;
    private String date;
    private String lat;
    private String lng;

    public Advert(String id, String name, POST_TYPE type, String phone, String description, String date, String lat, String lng) {
        this.type = type;
        this.name = name;
        this.phone = phone;
        this.description = description;
        this.lat = lat;
        this.lng = lng;
        this.date = date;
        this.id = id;//UUID.randomUUID().toString();
    }

    public Advert(Cursor cursor) {
        this.type = POST_TYPE.valueOf(cursor.getString(2));
        this.name = cursor.getString(1);
        this.phone = cursor.getString(3);
        this.description = cursor.getString(4);
        this.lat = cursor.getString(6);
        this.lng = cursor.getString(7);
        this.date = cursor.getString(5);
        this.id = cursor.getString(0);//UUID.randomUUID().toString();
    }

    public String getDate() {
        return date;
    }

    public POST_TYPE getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public String getLat() {
        return lat;
    }
    public String getLng() {
        return lng;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getId() {
        return id;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLocation(String lat, String lng) {
        this.lat = lat;
        this.lng = lng;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setType(POST_TYPE type) {
        this.type = type;
    }
}
