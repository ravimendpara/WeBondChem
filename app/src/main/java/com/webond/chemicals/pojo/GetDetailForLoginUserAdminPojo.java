package com.webond.chemicals.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetDetailForLoginUserAdminPojo {

    @SerializedName("CompanyId")
    @Expose
    private Integer companyId;
    @SerializedName("CompanyTagLine")
    @Expose
    private String companyTagLine;
    @SerializedName("CopanyName")
    @Expose
    private String copanyName;
    @SerializedName("Email")
    @Expose
    private String email;
    @SerializedName("LoginType")
    @Expose
    private String loginType;

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getCompanyTagLine() {
        return companyTagLine;
    }

    public void setCompanyTagLine(String companyTagLine) {
        this.companyTagLine = companyTagLine;
    }

    public String getCopanyName() {
        return copanyName;
    }

    public void setCopanyName(String copanyName) {
        this.copanyName = copanyName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLoginType() {
        return loginType;
    }

    public void setLoginType(String loginType) {
        this.loginType = loginType;
    }

}
