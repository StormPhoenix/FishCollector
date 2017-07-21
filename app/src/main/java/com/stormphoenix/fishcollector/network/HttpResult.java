package com.stormphoenix.fishcollector.network;

import com.google.gson.annotations.Expose;

public class HttpResult<T> {
    @Expose
    private int resultCode;
    @Expose
    private String resultMessage;

    @Expose
    private T data;

    @Override
    public String toString() {
        return "{ resultCode:" + resultCode + " , resultMessage:" + resultMessage + " }";
    }

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultMessage() {
        return resultMessage;
    }

    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
