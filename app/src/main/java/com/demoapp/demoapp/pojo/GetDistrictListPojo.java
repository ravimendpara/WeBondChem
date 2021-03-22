package com.demoapp.demoapp.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetDistrictListPojo {//make list in interface and implementer

    @SerializedName("DistrictId")
    @Expose
    private Integer districtId;
    @SerializedName("DistrictName")
    @Expose
    private String districtName;
    @SerializedName("StateId")
    @Expose
    private Integer stateId;
    @SerializedName("District_Is_Delete")
    @Expose
    private Integer districtIsDelete;
    @SerializedName("District_Is_Status")
    @Expose
    private Integer districtIsStatus;

    public Integer getDistrictId() {
        return districtId;
    }

    public void setDistrictId(Integer districtId) {
        this.districtId = districtId;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public Integer getStateId() {
        return stateId;
    }

    public void setStateId(Integer stateId) {
        this.stateId = stateId;
    }

    public Integer getDistrictIsDelete() {
        return districtIsDelete;
    }

    public void setDistrictIsDelete(Integer districtIsDelete) {
        this.districtIsDelete = districtIsDelete;
    }

    public Integer getDistrictIsStatus() {
        return districtIsStatus;
    }

    public void setDistrictIsStatus(Integer districtIsStatus) {
        this.districtIsStatus = districtIsStatus;
    }

}
