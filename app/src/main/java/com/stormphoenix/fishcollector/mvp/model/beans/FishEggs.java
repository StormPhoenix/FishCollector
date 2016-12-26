package com.stormphoenix.fishcollector.mvp.model.beans;


/**
 * Created by Phoenix on 2016/5/31.
 */
public class FishEggs {
    private String sampleID;
    private String photo;
    private String period;
    private float diameter;
    private float emDiameter;
    private String pigmentProp;
    private String embryoProp;
    private String idCatches;

    public String getSampleID() {
        return sampleID;
    }

    public void setSampleID(String sampleID) {
        this.sampleID = sampleID;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public float getDiameter() {
        return diameter;
    }

    public void setDiameter(float diameter) {
        this.diameter = diameter;
    }

    public float getEmDiameter() {
        return emDiameter;
    }

    public void setEmDiameter(float emDiameter) {
        this.emDiameter = emDiameter;
    }

    public String getPigmentProp() {
        return pigmentProp;
    }

    public void setPigmentProp(String pigmentProp) {
        this.pigmentProp = pigmentProp;
    }

    public String getEmbryoProp() {
        return embryoProp;
    }

    public void setEmbryoProp(String embryoProp) {
        this.embryoProp = embryoProp;
    }

    public String getIdCatches() {
        return idCatches;
    }

    public void setIdCatches(String idCatches) {
        this.idCatches = idCatches;
    }
}
