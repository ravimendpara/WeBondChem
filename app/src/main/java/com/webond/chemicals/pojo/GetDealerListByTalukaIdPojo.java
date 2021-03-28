package com.webond.chemicals.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetDealerListByTalukaIdPojo {

    @SerializedName("DealerId")
    @Expose
    private Integer dealerId;
    @SerializedName("DealerName")
    @Expose
    private String dealerName;
    @SerializedName("PethiName")
    @Expose
    private String pethiName;
    @SerializedName("DistributorName")
    @Expose
    private String distributorName;
    @SerializedName("CityName")
    @Expose
    private String cityName;
    @SerializedName("TalukaName")
    @Expose
    private String talukaName;
    @SerializedName("DistrictName")
    @Expose
    private String districtName;
    @SerializedName("StateName")
    @Expose
    private String stateName;

    public Integer getDealerId() {
        return dealerId;
    }

    public void setDealerId(Integer dealerId) {
        this.dealerId = dealerId;
    }

    public String getDealerName() {
        return dealerName;
    }

    public void setDealerName(String dealerName) {
        this.dealerName = dealerName;
    }

    public String getPethiName() {
        return pethiName;
    }

    public void setPethiName(String pethiName) {
        this.pethiName = pethiName;
    }

    public String getDistributorName() {
        return distributorName;
    }

    public void setDistributorName(String distributorName) {
        this.distributorName = distributorName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getTalukaName() {
        return talukaName;
    }

    public void setTalukaName(String talukaName) {
        this.talukaName = talukaName;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

}
