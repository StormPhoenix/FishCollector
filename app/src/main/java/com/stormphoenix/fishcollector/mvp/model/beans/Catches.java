package com.stormphoenix.fishcollector.mvp.model.beans;


/**
 * 渔获物
 * Created by Phoenix on 2016/5/31.
 */
public class Catches {

    //主键
    private String sampleID = null;
    //鱼类名称
    private String name = null;
    //图片路径
    private String photo = null;
    //卵苗总数
    private int totalQuality;
    //鱼卵总数
    private int eggQuality;
    //幼鱼数
    private int fryQuality;
    //水层外键
    private String idWaterLayer = null;

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

    public int getTotalQuality() {
        return totalQuality;
    }

    public void setTotalQuality(int totalQuality) {
        this.totalQuality = totalQuality;
    }

    public int getEggQuality() {
        return eggQuality;
    }

    public void setEggQuality(int eggQuality) {
        this.eggQuality = eggQuality;
    }

    public int getFryQuality() {
        return fryQuality;
    }

    public void setFryQuality(int fryQuality) {
        this.fryQuality = fryQuality;
    }

    public String getIdWaterLayer() {
        return idWaterLayer;
    }

    public void setIdWaterLayer(String idWaterLayer) {
        this.idWaterLayer = idWaterLayer;
    }
}
