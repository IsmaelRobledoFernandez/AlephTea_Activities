package es.aleph_tea.teabuddy.models;

import com.google.gson.annotations.SerializedName;

public class LocalizacionActividad {
    @SerializedName("lat")
    private double lat;
    @SerializedName("lon")
    private double lon;

    public double getLat() {
        return lat;
    }
    public double getLon() {
        return lon;
    }
}
