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
    private Double totalPoint;
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

    public Double getTotalPoint() {
        return totalPoint;
    }

    public void setTotalPoint(Double totalPoint) {
        this.totalPoint = totalPoint;
    }

    public String getReportPDFLink() {
        return reportPDFLink;
    }

    public void setReportPDFLink(String reportPDFLink) {
        this.reportPDFLink = reportPDFLink;
    }

}
