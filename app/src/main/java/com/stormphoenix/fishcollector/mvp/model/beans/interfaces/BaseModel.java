package com.stormphoenix.fishcollector.mvp.model.beans.interfaces;

/**
 * Created by Developer on 16-12-29.
 * Wang Cheng is a intelligent Android developer.
 */
public interface BaseModel {

    public String getModelId();

    public void setModelId(String modelId);

    public Long getId();

    public void setId(Long id);

    public Long getForeignKey();

    public void setForeignKey(Long foreignKey);
}

