package com.webond.chemicals.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AdminPointReportPojo {

    @SerializedName("CdCode")
    @Expose
    private String cdCode;
    @SerializedName("CdName")
    @Expose
    private String cdName;
    @SerializedName("TotalPoint")
    @Expose
    private Integer totalPoint;
    @SerializedName("RedemPoint")
    @Expose
    private Integer redemPoint;
    @SerializedName("AvailablePoint")
    @Expose
    private Integer availablePoint;
    @SerializedName("ReportPDFLink")
    @Expose
    private String reportPDFLink;

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

    public Integer getTotalPoint() {
        return totalPoint;
    }

    public void setTotalPoint(Integer totalPoint) {
        this.totalPoint = totalPoint;
    }

    public Integer getRedemPoint() {
        return redemPoint;
    }

    public void setRedemPoint(Integer redemPoint) {
        this.redemPoint = redemPoint;
    }

    public Integer getAvailablePoint() {
        return availablePoint;
    }

    public void setAvailablePoint(Integer availablePoint) {
        this.availablePoint = availablePoint;
    }

    public String getReportPDFLink() {
        return reportPDFLink;
    }

    public void setReportPDFLink(String reportPDFLink) {
        this.reportPDFLink = reportPDFLink;
    }

}
