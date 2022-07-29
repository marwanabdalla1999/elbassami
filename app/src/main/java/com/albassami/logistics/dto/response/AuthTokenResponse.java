package com.albassami.logistics.dto.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AuthTokenResponse {
    @SerializedName("uid")
    @Expose
    private Integer uid;
    @SerializedName("company_id")
    @Expose
    private Integer companyId;
    @SerializedName("access_token")
    @Expose
    private String accessToken;
    @SerializedName("expires_in")
    @Expose
    private String expiresIn;

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(String expiresIn) {
        this.expiresIn = expiresIn;
    }
}
