package me.queue.smartqueue.main.data.models;

import com.google.firebase.database.PropertyName;

import java.io.Serializable;

public class LatLongModel implements Serializable {
    @PropertyName("latitude")
    private String latitude;
    @PropertyName("longitude")
    private String longitude;

    public LatLongModel(String latitude, String longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "My Location";
    }
}

