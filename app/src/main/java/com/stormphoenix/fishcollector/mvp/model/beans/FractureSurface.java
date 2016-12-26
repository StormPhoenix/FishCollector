package com.stormphoenix.fishcollector.mvp.model.beans;


/**
 * Created by Phoenix on 2016/5/31.
 */
public class FractureSurface {

    private String ID;
    private String Position;
    private float Distance2Bank;
    private String ID_MonitoringSite;

    public void setPosition(String position) {
        Position = position;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public void setDistance2Bank(float distance2Bank) {
        Distance2Bank = distance2Bank;
    }

    public void setID_MonitoringSite(String ID_MonitoringSite) {
        this.ID_MonitoringSite = ID_MonitoringSite;
    }

    public String getKey() {
        return ID;
    }

    public String getPosition() {
        return Position;
    }

    public float getDistance2Bank() {
        return Distance2Bank;
    }

    public String getID_MonitoringSite() {
        return ID_MonitoringSite;
    }
}
