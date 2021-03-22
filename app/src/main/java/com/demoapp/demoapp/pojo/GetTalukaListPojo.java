package com.demoapp.demoapp.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetTalukaListPojo {//make list in interface and implementer

    @SerializedName("TalukaId")
    @Expose
    private Integer talukaId;
    @SerializedName("TalukaName")
    @Expose
    private String talukaName;
    @SerializedName("DistrictId")
    @Expose
    private Integer districtId;
    @SerializedName("Taluka_Is_Delete")
    @Expose
    private Integer talukaIsDelete;
    @SerializedName("Taluka_Is_Status")
    @Expose
    private Integer talukaIsStatus;

    public Integer getTalukaId() {
        return talukaId;
    }

    public void setTalukaId(Integer talukaId) {
        this.talukaId = talukaId;
    }

    public String getTalukaName() {
        return talukaName;
    }

    public void setTalukaName(String talukaName) {
        this.talukaName = talukaName;
    }

    public Integer getDistrictId() {
        return districtId;
    }

    public void setDistrictId(Integer districtId) {
        this.districtId = districtId;
    }

    public Integer getTalukaIsDelete() {
        return talukaIsDelete;
    }

    public void setTalukaIsDelete(Integer talukaIsDelete) {
        this.talukaIsDelete = talukaIsDelete;
    }

    public Integer getTalukaIsStatus() {
        return talukaIsStatus;
    }

    public void setTalukaIsStatus(Integer talukaIsStatus) {
        this.talukaIsStatus = talukaIsStatus;
    }


}
