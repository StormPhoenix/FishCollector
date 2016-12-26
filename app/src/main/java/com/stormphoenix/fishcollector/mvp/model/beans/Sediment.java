package com.stormphoenix.fishcollector.mvp.model.beans;


/**
 * 沉积物
 * Created by Phoenix on 2016/5/31.
 */
public class Sediment {

    private String SampleID;
    private String Photo;
    private String ID_FractureSurface;

    public String getKey() {
        return SampleID;
    }

    public String getPhoto() {
        return Photo;
    }

    public void setSampleID(String sampleID) {
        SampleID = sampleID;
    }

    public void setPhoto(String photo) {
        Photo = photo;
    }

    public String getID_FractureSurface() {
        return ID_FractureSurface;
    }

    public void setID_FractureSurface(String ID_FractureSurface) {
        this.ID_FractureSurface = ID_FractureSurface;
    }
}
