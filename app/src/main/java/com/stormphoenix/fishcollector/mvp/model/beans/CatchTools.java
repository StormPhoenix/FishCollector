package com.stormphoenix.fishcollector.mvp.model.beans;

import java.util.Date;

/**
 * Created by Phoenix on 2016/5/31.
 */
public class CatchTools {
    //CatchTools主键
    private String sampleID;
    //网具名字
    private String name;
    //照片路径，多个路径用分号隔开
    private String photo;
    //网型
    private String netsModel;
    //网口面积
    private float netMouthArea;
    //网口倾角
    private float netMouthDip;
    //开始时间
    private Date startTime;
    //结束时间
    private Date endTime;
    //网口流速
    private float netMouthVelocity;
    // 水层外键
    private String idWaterLayer;

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

    public String getNetsModel() {
        return netsModel;
    }

    public void setNetsModel(String netsModel) {
        this.netsModel = netsModel;
    }

    public float getNetMouthArea() {
        return netMouthArea;
    }

    public void setNetMouthArea(float netMouthArea) {
        this.netMouthArea = netMouthArea;
    }

    public float getNetMouthDip() {
        return netMouthDip;
    }

    public void setNetMouthDip(float netMouthDip) {
        this.netMouthDip = netMouthDip;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public float getNetMouthVelocity() {
        return netMouthVelocity;
    }

    public void setNetMouthVelocity(float netMouthVelocity) {
        this.netMouthVelocity = netMouthVelocity;
    }

    public String getIdWaterLayer() {
        return idWaterLayer;
    }

    public void setIdWaterLayer(String idWaterLayer) {
        this.idWaterLayer = idWaterLayer;
    }
}
