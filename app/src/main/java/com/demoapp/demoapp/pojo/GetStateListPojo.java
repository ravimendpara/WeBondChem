package com.demoapp.demoapp.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetStateListPojo {//make list in interface

    @SerializedName("StateId")
    @Expose
    private Integer stateId;
    @SerializedName("StateName")
    @Expose
    private String stateName;
    @SerializedName("State_Is_Delete")
    @Expose
    private Integer stateIsDelete;
    @SerializedName("State_Is_Status")
    @Expose
    private Integer stateIsStatus;

    public Integer getStateId() {
        return stateId;
    }

    public void setStateId(Integer stateId) {
        this.stateId = stateId;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public Integer getStateIsDelete() {
        return stateIsDelete;
    }

    public void setStateIsDelete(Integer stateIsDelete) {
        this.stateIsDelete = stateIsDelete;
    }

    public Integer getStateIsStatus() {
        return stateIsStatus;
    }

    public void setStateIsStatus(Integer stateIsStatus) {
        this.stateIsStatus = stateIsStatus;
    }

}
