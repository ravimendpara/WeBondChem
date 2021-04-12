package com.webond.chemicals.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CheckMobileNoExitstOrNoPojo {

    @SerializedName("Status")
    @Expose
    private Integer status;
    @SerializedName("ApproveStatus")
    @Expose
    private Integer approveStatus;
    @SerializedName("Msg")
    @Expose
    private String msg;
    @SerializedName("IsSmsProviderWorking")
    @Expose
    private String isSmsProviderWorking;
    @SerializedName("NeedToVerifyOTPOnRegister")
    @Expose
    private String needToVerifyOTPOnRegister;

    public String getIsSmsProviderWorking() {
        return isSmsProviderWorking;
    }

    public void setIsSmsProviderWorking(String isSmsProviderWorking) {
        this.isSmsProviderWorking = isSmsProviderWorking;
    }

    public String getNeedToVerifyOTPOnRegister() {
        return needToVerifyOTPOnRegister;
    }

    public void setNeedToVerifyOTPOnRegister(String needToVerifyOTPOnRegister) {
        this.needToVerifyOTPOnRegister = needToVerifyOTPOnRegister;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getApproveStatus() {
        return approveStatus;
    }

    public void setApproveStatus(Integer approveStatus) {
        this.approveStatus = approveStatus;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }


}
