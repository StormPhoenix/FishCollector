package com.stormphoenix.fishcollector.mvp.model.beans;


/**
 * Created by Phoenix on 2016/5/31.
 */
public class DominantPhytoplanktonSpecies {

    private String sampleID;
    private String name;
    private String photo;
    private float quality;
    private float biomass;
    private String idPhytoplankton;

    public String getSampleID() {
        return sampleID;
    }

    public void setSampleID(String sampleID) {
        this.sampleID = sampleID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public float getQuality() {
        return quality;
    }

    public void setQuality(float quality) {
        this.quality = quality;
    }

    public float getBiomass() {
        return biomass;
    }

    public void setBiomass(float biomass) {
        this.biomass = biomass;
    }

    public String getIdPhytoplankton() {
        return idPhytoplankton;
    }

    public void setIdPhytoplankton(String idPhytoplankton) {
        this.idPhytoplankton = idPhytoplankton;
    }
}
