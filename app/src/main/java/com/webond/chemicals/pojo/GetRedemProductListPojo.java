package com.webond.chemicals.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetRedemProductListPojo {

    @SerializedName("SchemeProductImagePathLink")
    @Expose
    private String schemeProductImagePathLink;
    @SerializedName("SchemeProductId")
    @Expose
    private Integer schemeProductId;
    @SerializedName("SchemeProductName")
    @Expose
    private String schemeProductName;
    @SerializedName("SchemeProductPoints")
    @Expose
    private Integer schemeProductPoints;
    @SerializedName("TotalPoint")
    @Expose
    private Integer totalPoint;
    @SerializedName("ExpiryDate")
    @Expose
    private Object expiryDate;
    @SerializedName("ProdDesc")
    @Expose
    private Object prodDesc;

    public String getSchemeProductImagePathLink() {
        return schemeProductImagePathLink;
    }

    public void setSchemeProductImagePathLink(String schemeProductImagePathLink) {
        this.schemeProductImagePathLink = schemeProductImagePathLink;
    }

    public Integer getSchemeProductId() {
        return schemeProductId;
    }

    public void setSchemeProductId(Integer schemeProductId) {
        this.schemeProductId = schemeProductId;
    }

    public String getSchemeProductName() {
        return schemeProductName;
    }

    public void setSchemeProductName(String schemeProductName) {
        this.schemeProductName = schemeProductName;
    }

    public Integer getSchemeProductPoints() {
        return schemeProductPoints;
    }

    public void setSchemeProductPoints(Integer schemeProductPoints) {
        this.schemeProductPoints = schemeProductPoints;
    }

    public Integer getTotalPoint() {
        return totalPoint;
    }

    public void setTotalPoint(Integer totalPoint) {
        this.totalPoint = totalPoint;
    }

    public Object getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Object expiryDate) {
        this.expiryDate = expiryDate;
    }

    public Object getProdDesc() {
        return prodDesc;
    }

    public void setProdDesc(Object prodDesc) {
        this.prodDesc = prodDesc;
    }
}
