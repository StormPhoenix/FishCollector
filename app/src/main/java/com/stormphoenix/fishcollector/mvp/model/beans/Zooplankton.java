package com.stormphoenix.fishcollector.mvp.model.beans;


/**
 * Created by Phoenix on 2016/5/31.
 */
public class Zooplankton {

    private String SampleID;
    private String Photo;
    private int Quality;
    private float Biomass;
    private String ID_FractureSurface;

    public void setSampleID(String sampleID) {
        SampleID = sampleID;
    }

    public void setPhoto(String photo) {
        Photo = photo;
    }

    public void setQuality(int quality) {
        Quality = quality;
    }

    public void setBiomass(float biomass) {
        Biomass = biomass;
    }

    public void setID_FractureSurface(String ID_FractureSurface) {
        this.ID_FractureSurface = ID_FractureSurface;
    }


    public String getID_FractureSurface() {
        return ID_FractureSurface;
    }

    public String getKey() {
        return SampleID;
    }

    public String getPhoto() {
        return Photo;
    }

    public float getQuality() {
        return Quality;
    }

    public float getBiomass() {
        return Biomass;
    }
}
