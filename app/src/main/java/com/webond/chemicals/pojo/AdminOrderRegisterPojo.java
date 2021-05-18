package com.webond.chemicals.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AdminOrderRegisterPojo {

    @SerializedName("OrderDate")
    @Expose
    private String orderDate;

    @SerializedName("Status")
    @Expose
    private String Status;
    @SerializedName("OrderNo")
    @Expose
    private String orderNo;
    @SerializedName("CdCode")
    @Expose
    private String cdCode;
    @SerializedName("CdName")
    @Expose
    private String cdName;
    @SerializedName("ProductCode")
    @Expose
    private String productCode;
    @SerializedName("ProductName")
    @Expose
    private String productName;
    @SerializedName("Qty")
    @Expose
    private Double qty;
    @SerializedName("Points")
    @Expose
    private Double points;
    @SerializedName("OrderType")
    @Expose
    private String orderType;
    @SerializedName("OrderStatus")
    @Expose
    private String orderStatus;
    @SerializedName("ReportPDFLink")
    @Expose
    private String reportPDFLink;

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
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

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Double getQty() {
        return qty;
    }

    public void setQty(Double qty) {
        this.qty = qty;
    }

    public Double getPoints() {
        return points;
    }

    public void setPoints(Double points) {
        this.points = points;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getReportPDFLink() {
        return reportPDFLink;
    }

    public void setReportPDFLink(String reportPDFLink) {
        this.reportPDFLink = reportPDFLink;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }
}
