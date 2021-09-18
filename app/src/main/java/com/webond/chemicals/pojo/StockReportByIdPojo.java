package com.webond.chemicals.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class StockReportByIdPojo {

    @SerializedName("ProductCode")
    @Expose
    private String ProductCode;
    @SerializedName("ProductName")
    @Expose
    private String ProductName;
    @SerializedName("TotalQty")
    @Expose
    private Double totalQty;
    @SerializedName("SaleQty")
    @Expose
    private Double saleQty;
    @SerializedName("AvailableQty")
    @Expose
    private Double availableQty;


    public String getProductCode() {
        return ProductCode;
    }

    public void setProductCode(String ProductCode) {
        this.ProductCode = ProductCode;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String ProductName) {
        this.ProductName = ProductName;
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

}
