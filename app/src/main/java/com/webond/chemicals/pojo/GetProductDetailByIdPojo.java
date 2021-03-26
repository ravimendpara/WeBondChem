package com.webond.chemicals.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetProductDetailByIdPojo {

    @SerializedName("ProductId")
    @Expose
    private Integer productId;
    @SerializedName("ProductCode")
    @Expose
    private String productCode;
    @SerializedName("ProductName")
    @Expose
    private String productName;
    @SerializedName("ProductPrice")
    @Expose
    private Double productPrice;
    @SerializedName("ProductTotalPoint")
    @Expose
    private Double productTotalPoint;
    @SerializedName("DistPer")
    @Expose
    private Double distPer;
    @SerializedName("DealerPer")
    @Expose
    private Double dealerPer;
    @SerializedName("CustomerPer")
    @Expose
    private Double customerPer;
    @SerializedName("ProductDescription")
    @Expose
    private String productDescription;
    @SerializedName("ProductPhoto1")
    @Expose
    private Object productPhoto1;
    @SerializedName("ProductPhoto2")
    @Expose
    private Object productPhoto2;
    @SerializedName("ProductPhoto3")
    @Expose
    private Object productPhoto3;
    @SerializedName("ProductPhoto4")
    @Expose
    private Object productPhoto4;
    @SerializedName("ProductPhoto5")
    @Expose
    private Object productPhoto5;

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
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

    public Double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Double productPrice) {
        this.productPrice = productPrice;
    }

    public Double getProductTotalPoint() {
        return productTotalPoint;
    }

    public void setProductTotalPoint(Double productTotalPoint) {
        this.productTotalPoint = productTotalPoint;
    }

    public Double getDistPer() {
        return distPer;
    }

    public void setDistPer(Double distPer) {
        this.distPer = distPer;
    }

    public Double getDealerPer() {
        return dealerPer;
    }

    public void setDealerPer(Double dealerPer) {
        this.dealerPer = dealerPer;
    }

    public Double getCustomerPer() {
        return customerPer;
    }

    public void setCustomerPer(Double customerPer) {
        this.customerPer = customerPer;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public Object getProductPhoto1() {
        return productPhoto1;
    }

    public void setProductPhoto1(Object productPhoto1) {
        this.productPhoto1 = productPhoto1;
    }

    public Object getProductPhoto2() {
        return productPhoto2;
    }

    public void setProductPhoto2(Object productPhoto2) {
        this.productPhoto2 = productPhoto2;
    }

    public Object getProductPhoto3() {
        return productPhoto3;
    }

    public void setProductPhoto3(Object productPhoto3) {
        this.productPhoto3 = productPhoto3;
    }

    public Object getProductPhoto4() {
        return productPhoto4;
    }

    public void setProductPhoto4(Object productPhoto4) {
        this.productPhoto4 = productPhoto4;
    }

    public Object getProductPhoto5() {
        return productPhoto5;
    }

    public void setProductPhoto5(Object productPhoto5) {
        this.productPhoto5 = productPhoto5;
    }

}
