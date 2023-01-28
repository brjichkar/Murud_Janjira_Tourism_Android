
package com.login_section.model.login_request;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class LoginRequest {

    @SerializedName("jsondata")
    @Expose
    private LoginReqData jsondata;

    public LoginReqData getJsondata() {
        return jsondata;
    }

    public void setJsondata(LoginReqData jsondata) {
        this.jsondata = jsondata;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(LoginRequest.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("jsondata");
        sb.append('=');
        sb.append(((this.jsondata == null)?"<null>":this.jsondata));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

}
