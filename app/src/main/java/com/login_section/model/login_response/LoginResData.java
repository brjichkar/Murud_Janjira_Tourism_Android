
package com.login_section.model.login_response;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class LoginResData {

    @SerializedName("user_id")
    @Expose
    private Object userId;
    @SerializedName("mobile")
    @Expose
    private String mobile;

    public Object getUserId() {
        return userId;
    }

    public void setUserId(Object userId) {
        this.userId = userId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(LoginResData.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("userId");
        sb.append('=');
        sb.append(((this.userId == null)?"<null>":this.userId));
        sb.append(',');
        sb.append("mobile");
        sb.append('=');
        sb.append(((this.mobile == null)?"<null>":this.mobile));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

}
