package com.webond.chemicals.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AdminStockReportPojo implements Serializable {

    @SerializedName("Id")
    @Expose
    private Integer id;
    @SerializedName("CDCode")
    @Expose
    private String cDCode;
    @SerializedName("CDName")
    @Expose
    private String cDName;
    @SerializedName("ProductId")
    @Expose
    private Integer productId;
    @SerializedName("ProductName")
    @Expose
    private String productName;
    @SerializedName("TotalQty")
    @Expose
    private Double totalQty;
    @SerializedName("SaleQty")
    @Expose
    private Double saleQty;
    @SerializedName("AvailableQty")
    @Expose
    private Double availableQty;
    @SerializedName("ReportPDFLink")
    @Expose
    private String reportPDFLink;
    @SerializedName("ReportDate")
    @Expose
    private String reportDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCDCode() {
        return cDCode;
    }

    public void setCDCode(String cDCode) {
        this.cDCode = cDCode;
    }

    public String getCDName() {
        return cDName;
    }

    public void setCDName(String cDName) {
        this.cDName = cDName;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Double getTotalQty() {
        return totalQty;
    }

    public void setTotalQty(Double totalQty) {
        this.totalQty = totalQty;
    }

    public Double getSaleQty() {
        return saleQty;
    }

    public void setSaleQty(Double saleQty) {
        this.saleQty = saleQty;
    }

    public Double getAvailableQty() {
        return availableQty;
    }

    public void setAvailableQty(Double availableQty) {
        this.availableQty = availableQty;
    }

    public String getReportPDFLink() {
        return reportPDFLink;
    }

    public void setReportPDFLink(String reportPDFLink) {
        this.reportPDFLink = reportPDFLink;
    }

    public String getReportDate() {
        return reportDate;
    }

    public void setReportDate(String reportDate) {
        this.reportDate = reportDate;
    }
}
