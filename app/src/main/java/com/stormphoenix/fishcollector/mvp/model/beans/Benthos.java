package com.stormphoenix.fishcollector.mvp.model.beans;

/**
 * 底栖生物
 * Created by Phoenix on 2016/5/31.
 */
public class Benthos {

    //地栖生物主键
    private String sampleID = null;
    //照片路径
    private String photo = null;
    //数量
    private int quality = 0;
    //生物量
    private float biomass = 0;
    //断面外键
    private String idFractureSurface = null;

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

    public int getQuality() {
        return quality;
    }

    public void setQuality(int quality) {
        this.quality = quality;
    }

    public float getBiomass() {
        return biomass;
    }

    public void setBiomass(float biomass) {
        this.biomass = biomass;
    }

    public String getIdFractureSurface() {
        return idFractureSurface;
    }

    public void setIdFractureSurface(String idFractureSurface) {
        this.idFractureSurface = idFractureSurface;
    }
}
