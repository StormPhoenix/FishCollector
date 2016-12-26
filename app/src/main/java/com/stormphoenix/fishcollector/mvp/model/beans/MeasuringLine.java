package com.stormphoenix.fishcollector.mvp.model.beans;

/**
 * Created by Phoenix on 2016/5/31.
 */
public class MeasuringLine  {

    private String ID;
    private float StartLongitude;
    private float StartLatitude;
    private float EndLongitude;
    private float EndLatitude;
    private String ID_FractureSurface;

    public void setStartLongitude(float startLongitude) {
        StartLongitude = startLongitude;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public void setStartLatitude(float startLatitude) {
        StartLatitude = startLatitude;
    }

    public void setEndLongitude(float endLongitude) {
        EndLongitude = endLongitude;
    }

    public void setEndLatitude(float endLatitude) {
        EndLatitude = endLatitude;
    }

    public void setID_FractureSurface(String ID_FractureSurface) {
        this.ID_FractureSurface = ID_FractureSurface;
    }

    public String getKey() {
        return ID;
    }

    public float getStartLongitude() {
        return StartLongitude;
    }

    public float getStartLatitude() {
        return StartLatitude;
    }

    public float getEndLongitude() {
        return EndLongitude;
    }

    public float getEndLatitude() {
        return EndLatitude;
    }

    public String getID_FractureSurface() {
        return ID_FractureSurface;
    }
}
