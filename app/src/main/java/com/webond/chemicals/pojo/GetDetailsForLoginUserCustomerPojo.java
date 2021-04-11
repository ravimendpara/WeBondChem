package com.webond.chemicals.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetDetailsForLoginUserCustomerPojo {

    @SerializedName("CustomerId")
    @Expose
    private Integer customerId;
    @SerializedName("CustomerName")
    @Expose
    private String customerName;
    @SerializedName("MobileNo")
    @Expose
    private String mobileNo;
    @SerializedName("MobileNo2")
    @Expose
    private String mobileNo2;
    @SerializedName("EmailId")
    @Expose
    private String emailId;
    @SerializedName("CityId")
    @Expose
    private Integer cityId;
    @SerializedName("DealerId")
    @Expose
    private Integer dealerId;
    @SerializedName("DistrictId")
    @Expose
    private Integer districtId;
    @SerializedName("TalukaId")
    @Expose
    private Integer talukaId;
    @SerializedName("StateId")
    @Expose
    private Integer stateId;
    @SerializedName("PhotoPath")
    @Expose
    private String photoPath;
    @SerializedName("DealerName")
    @Expose
    private Object dealerName;
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
    @SerializedName("LoginType")
    @Expose
    private String loginType;
    @SerializedName("CustomerUnderRegiterStatus")
    @Expose
    private Integer customerUnderRegiterStatus;
    @SerializedName("CustomerUnderRegiter")
    @Expose
    private String customerUnderRegiter;

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
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

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public Integer getDealerId() {
        return dealerId;
    }

    public void setDealerId(Integer dealerId) {
        this.dealerId = dealerId;
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

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public Object getDealerName() {
        return dealerName;
    }

    public void setDealerName(Object dealerName) {
        this.dealerName = dealerName;
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

    public String getLoginType() {
        return loginType;
    }

    public void setLoginType(String loginType) {
        this.loginType = loginType;
    }

    public Integer getCustomerUnderRegiterStatus() {
        return customerUnderRegiterStatus;
    }

    public void setCustomerUnderRegiterStatus(Integer customerUnderRegiterStatus) {
        this.customerUnderRegiterStatus = customerUnderRegiterStatus;
    }

    public String getCustomerUnderRegiter() {
        return customerUnderRegiter;
    }

    public void setCustomerUnderRegiter(String customerUnderRegiter) {
        this.customerUnderRegiter = customerUnderRegiter;
    }
}
