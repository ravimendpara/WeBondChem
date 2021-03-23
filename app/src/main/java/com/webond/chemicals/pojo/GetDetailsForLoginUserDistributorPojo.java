package com.webond.chemicals.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetDetailsForLoginUserDistributorPojo {

    @SerializedName("DistributorId")
    @Expose
    private Integer distributorId;
    @SerializedName("DistributorName")
    @Expose
    private String distributorName;
    @SerializedName("MobileNo")
    @Expose
    private String mobileNo;
    @SerializedName("MobileNo2")
    @Expose
    private String mobileNo2;
    @SerializedName("Email")
    @Expose
    private String email;
    @SerializedName("PhotoPath")
    @Expose
    private Object photoPath;
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
    @SerializedName("LoginType")
    @Expose
    private String loginType;

    public Integer getDistributorId() {
        return distributorId;
    }

    public void setDistributorId(Integer distributorId) {
        this.distributorId = distributorId;
    }

    public String getDistributorName() {
        return distributorName;
    }

    public void setDistributorName(String distributorName) {
        this.distributorName = distributorName;
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

    public Object getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(Object photoPath) {
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

    public String getLoginType() {
        return loginType;
    }

    public void setLoginType(String loginType) {
        this.loginType = loginType;
    }


}
