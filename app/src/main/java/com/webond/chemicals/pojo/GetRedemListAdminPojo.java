package com.webond.chemicals.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetRedemListAdminPojo {

    @SerializedName("RedemId")
    @Expose
    private Integer redemId;
    @SerializedName("RedemProductId")
    @Expose
    private Integer redemProductId;
    @SerializedName("RedemPoint")
    @Expose
    private Integer redemPoint;
    @SerializedName("RedemType")
    @Expose
    private String redemType;
    @SerializedName("RedemApproveStatus")
    @Expose
    private Object redemApproveStatus;
    @SerializedName("RedemApproveDate")
    @Expose
    private Object redemApproveDate;
    @SerializedName("RedemCreatedDate")
    @Expose
    private String redemCreatedDate;
    @SerializedName("RedemIpAddress")
    @Expose
    private String redemIpAddress;
    @SerializedName("RedemCustomerId")
    @Expose
    private Integer redemCustomerId;
    @SerializedName("RedemDealerId")
    @Expose
    private Integer redemDealerId;
    @SerializedName("RedemDistributorId")
    @Expose
    private Integer redemDistributorId;
    @SerializedName("Qty")
    @Expose
    private Integer qty;
    @SerializedName("RedemStatus")
    @Expose
    private String redemStatus;
    @SerializedName("SchemeProductName")
    @Expose
    private String schemeProductName;
    @SerializedName("SchemeProductImagePathLink")
    @Expose
    private String schemeProductImagePathLink;
    @SerializedName("CdCode")
    @Expose
    private String cdCode;
    @SerializedName("CdName")
    @Expose
    private String cdName;

    public Integer getRedemId() {
        return redemId;
    }

    public void setRedemId(Integer redemId) {
        this.redemId = redemId;
    }

    public Integer getRedemProductId() {
        return redemProductId;
    }

    public void setRedemProductId(Integer redemProductId) {
        this.redemProductId = redemProductId;
    }

    public Integer getRedemPoint() {
        return redemPoint;
    }

    public void setRedemPoint(Integer redemPoint) {
        this.redemPoint = redemPoint;
    }

    public String getRedemType() {
        return redemType;
    }

    public void setRedemType(String redemType) {
        this.redemType = redemType;
    }

    public Object getRedemApproveStatus() {
        return redemApproveStatus;
    }

    public void setRedemApproveStatus(Object redemApproveStatus) {
        this.redemApproveStatus = redemApproveStatus;
    }

    public Object getRedemApproveDate() {
        return redemApproveDate;
    }

    public void setRedemApproveDate(Object redemApproveDate) {
        this.redemApproveDate = redemApproveDate;
    }

    public String getRedemCreatedDate() {
        return redemCreatedDate;
    }

    public void setRedemCreatedDate(String redemCreatedDate) {
        this.redemCreatedDate = redemCreatedDate;
    }

    public String getRedemIpAddress() {
        return redemIpAddress;
    }

    public void setRedemIpAddress(String redemIpAddress) {
        this.redemIpAddress = redemIpAddress;
    }

    public Integer getRedemCustomerId() {
        return redemCustomerId;
    }

    public void setRedemCustomerId(Integer redemCustomerId) {
        this.redemCustomerId = redemCustomerId;
    }

    public Integer getRedemDealerId() {
        return redemDealerId;
    }

    public void setRedemDealerId(Integer redemDealerId) {
        this.redemDealerId = redemDealerId;
    }

    public Integer getRedemDistributorId() {
        return redemDistributorId;
    }

    public void setRedemDistributorId(Integer redemDistributorId) {
        this.redemDistributorId = redemDistributorId;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public String getRedemStatus() {
        return redemStatus;
    }

    public void setRedemStatus(String redemStatus) {
        this.redemStatus = redemStatus;
    }

    public String getSchemeProductName() {
        return schemeProductName;
    }

    public void setSchemeProductName(String schemeProductName) {
        this.schemeProductName = schemeProductName;
    }

    public String getSchemeProductImagePathLink() {
        return schemeProductImagePathLink;
    }

    public void setSchemeProductImagePathLink(String schemeProductImagePathLink) {
        this.schemeProductImagePathLink = schemeProductImagePathLink;
    }

    public String getCdCode() {
        return cdCode;
    }

    public void setCdCode(String cdCode) {
        this.cdCode = cdCode;
    }

    public String getCdName() {
        return cdName;
    }

    public void setCdName(String cdName) {
        this.cdName = cdName;
    }

}
