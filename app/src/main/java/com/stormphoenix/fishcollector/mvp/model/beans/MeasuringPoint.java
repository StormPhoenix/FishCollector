package com.stormphoenix.fishcollector.mvp.model.beans;


/**
 * Created by Phoenix on 2016/5/31.
 */
public class MeasuringPoint  {

    public String ID;
    public float Longitude;
    public float Latitude;
    public String ID_MeasuringLine;

    public String getID_MeasuringLine() {
        return ID_MeasuringLine;
    }

    public float getLatitude() {
        return Latitude;
    }

    public float getLongitude() {
        return Longitude;
    }

    public String getKey() {
        return ID;
    }

    public void setLongitude(float longitude) {
        Longitude = longitude;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public void setLatitude(float latitude) {
        Latitude = latitude;
    }

    public void setID_MeasuringLine(String ID_MeasuringLine) {
        this.ID_MeasuringLine = ID_MeasuringLine;
    }
}
