package com.webond.chemicals.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AdminRedeemListReportPojo {
    @SerializedName("PethiName")
    @Expose
    private String pethiName;
    @SerializedName("CdCode")
    @Expose
    private String cdCode;
    @SerializedName("CdName")
    @Expose
    private String cdName;
    @SerializedName("SchemeProductName")
    @Expose
    private String schemeProductName;
    @SerializedName("Qty")
    @Expose
    private Integer qty;
    @SerializedName("RedemPoint")
    @Expose
    private Integer redemPoint;
    @SerializedName("RedemDate")
    @Expose
    private String redemDate;
    @SerializedName("RedemStatus")
    @Expose
    private String redemStatus;
    @SerializedName("ReportPdfLink")
    @Expose
    private String reportPdfLink;

    public String getPethiName() {
        return pethiName;
    }

    public void setPethiName(String pethiName) {
        this.pethiName = pethiName;
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

    public String getSchemeProductName() {
        return schemeProductName;
    }

    public void setSchemeProductName(String schemeProductName) {
        this.schemeProductName = schemeProductName;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public Integer getRedemPoint() {
        return redemPoint;
    }

    public void setRedemPoint(Integer redemPoint) {
        this.redemPoint = redemPoint;
    }

    public String getRedemDate() {
        return redemDate;
    }

    public void setRedemDate(String redemDate) {
        this.redemDate = redemDate;
    }

    public String getRedemStatus() {
        return redemStatus;
    }

    public void setRedemStatus(String redemStatus) {
        this.redemStatus = redemStatus;
    }

    public String getReportPdfLink() {
        return reportPdfLink;
    }

    public void setReportPdfLink(String reportPdfLink) {
        this.reportPdfLink = reportPdfLink;
    }
}
