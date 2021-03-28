package com.webond.chemicals.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetVersionInfoPojo {

    @SerializedName("AppVersionId")
    @Expose
    private Integer appVersionId;
    @SerializedName("AppVersionCode")
    @Expose
    private String appVersionCode;
    @SerializedName("AppVersionType")
    @Expose
    private Integer appVersionType;

    public Integer getAppVersionId() {
        return appVersionId;
    }

    public void setAppVersionId(Integer appVersionId) {
        this.appVersionId = appVersionId;
    }

    public String getAppVersionCode() {
        return appVersionCode;
    }

    public void setAppVersionCode(String appVersionCode) {
        this.appVersionCode = appVersionCode;
    }

    public Integer getAppVersionType() {
        return appVersionType;
    }

    public void setAppVersionType(Integer appVersionType) {
        this.appVersionType = appVersionType;
    }

}
