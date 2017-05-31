package me.danielrosero.coso.modelo;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by danrosero on 25/05/17.
 */

public class Captura {

    private float accX;
    private float accY;
    private float accZ;
    private LatLng latilongi;

    public LatLng getLatilongi() {
        return latilongi;
    }

    private int timeStamp;

    public float getAccX() {
        return accX;
    }

    public float getAccY() {
        return accY;
    }

    public float getAccZ() {
        return accZ;
    }




    public Captura(float accX, float accY, float accZ, LatLng latilongi) {
        this.accX = accX;
        this.accY = accY;
        this.accZ = accZ;
        this.latilongi = latilongi;
    }
}
