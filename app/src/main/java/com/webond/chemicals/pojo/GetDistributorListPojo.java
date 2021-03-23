package com.webond.chemicals.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetDistributorListPojo {

    @SerializedName("DistributorId")
    @Expose
    private Integer distributorId;
    @SerializedName("DistributorName")
    @Expose
    private String distributorName;
    @SerializedName("DateOfBith")
    @Expose
    private String dateOfBith;
    @SerializedName("StateId")
    @Expose
    private Integer stateId;
    @SerializedName("DistrictId")
    @Expose
    private Integer districtId;
    @SerializedName("TalukaId")
    @Expose
    private Integer talukaId;
    @SerializedName("CityId")
    @Expose
    private Integer cityId;
    @SerializedName("MobileNo")
    @Expose
    private String mobileNo;
    @SerializedName("MobileNo2")
    @Expose
    private String mobileNo2;
    @SerializedName("Address")
    @Expose
    private String address;
    @SerializedName("PinCode")
    @Expose
    private String pinCode;
    @SerializedName("Email")
    @Expose
    private String email;
    @SerializedName("AadharNo")
    @Expose
    private String aadharNo;
    @SerializedName("GSTNo")
    @Expose
    private Object gSTNo;
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
    @SerializedName("Status")
    @Expose
    private String status;
    @SerializedName("PhotoPath")
    @Expose
    private Object photoPath;

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

    public String getDateOfBith() {
        return dateOfBith;
    }

    public void setDateOfBith(String dateOfBith) {
        this.dateOfBith = dateOfBith;
    }

    public Integer getStateId() {
        return stateId;
    }

    public void setStateId(Integer stateId) {
        this.stateId = stateId;
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

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAadharNo() {
        return aadharNo;
    }

    public void setAadharNo(String aadharNo) {
        this.aadharNo = aadharNo;
    }

    public Object getGSTNo() {
        return gSTNo;
    }

    public void setGSTNo(Object gSTNo) {
        this.gSTNo = gSTNo;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(Object photoPath) {
        this.photoPath = photoPath;
    }

}