package com.webond.chemicals.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetDetailsForLoginUserDealerPojo {

    @SerializedName("DealerId")
    @Expose
    private Integer dealerId;
    @SerializedName("DealerName")
    @Expose
    private String dealerName;
    @SerializedName("MobileNo")
    @Expose
    private String mobileNo;
    @SerializedName("MobileNo2")
    @Expose
    private String mobileNo2;
    @SerializedName("Email")
    @Expose
    private String email;
    @SerializedName("CityId")
    @Expose
    private Integer cityId;
    @SerializedName("DistrictId")
    @Expose
    private Integer districtId;
    @SerializedName("TalukaId")
    @Expose
    private Integer talukaId;
    @SerializedName("StateId")
    @Expose
    private Integer stateId;
    @SerializedName("DistributorId")
    @Expose
    private Integer distributorId;
    @SerializedName("PhotoPath")
    @Expose
    private String photoPath;
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
    @SerializedName("DistributorName")
    @Expose
    private String distributorName;
    @SerializedName("LoginType")
    @Expose
    private String loginType;

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

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getMobileNo2() {
        return mobileNo2;
    }

    public void setMobileNo2(String mobileNo2) {
        this.mobileNo2 = mobileNo2;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public Integer getDistrictId() {
        return districtId;
    }

    public void setDistrictId(Integer districtId) {
        this.districtId = districtId;
    }

    public Integer getTalukaId() {
        return talukaId;
    }

    public void setTalukaId(Integer talukaId) {
        this.talukaId = talukaId;
    }

    public Integer getStateId() {
        return stateId;
    }

    public void setStateId(Integer stateId) {
        this.stateId = stateId;
    }

    public Integer getDistributorId() {
        return distributorId;
    }

    public void setDistributorId(Integer distributorId) {
        this.distributorId = distributorId;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
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

    public String getDistributorName() {
        return distributorName;
    }

    public void setDistributorName(String distributorName) {
        this.distributorName = distributorName;
    }

    public String getLoginType() {
        return loginType;
    }

    public void setLoginType(String loginType) {
        this.loginType = loginType;
    }

}
