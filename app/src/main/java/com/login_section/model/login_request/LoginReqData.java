
package com.login_section.model.login_request;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginReqData {

    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("token")
    @Expose
    private String token;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(LoginReqData.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("mobile");
        sb.append('=');
        sb.append(((this.mobile == null)?"<null>":this.mobile));
        sb.append(',');
        sb.append("token");
        sb.append('=');
        sb.append(((this.token == null)?"<null>":this.token));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

}
