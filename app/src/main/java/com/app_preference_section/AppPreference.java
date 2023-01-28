
package com.app_preference_section;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.fragment.app.FragmentActivity;

import com.AppConstants;


public class AppPreference implements com.app_preference_section.IPreferenceHelper {
    private final SharedPreferences mSharedPreferences;
    private final SharedPreferences.Editor mEditor;

    public AppPreference(Context context) {
        mSharedPreferences  = context.getSharedPreferences(AppConstants.PREF_NAME, Context.MODE_PRIVATE);
        mEditor=mSharedPreferences.edit();
    }

    private static SharedPreferences getPrefs(Context context) {
        return context.getSharedPreferences(AppConstants.PREF_NAME, 0);
    }


    @Override
    public void setFirstTimeLaunch(boolean state) {
        mEditor.putBoolean(AppConstants.SP_FIRST_LAUNCH,state);
        mEditor.commit();
    }

    @Override
    public boolean getFirstTimeLaunch() {
        return mSharedPreferences.getBoolean(AppConstants.SP_FIRST_LAUNCH, true);
    }

    @Override
    public void setUserId(String userid) {
        mEditor.putString (AppConstants.USER_ID,userid);
        mEditor.commit();
    }

    @Override
    public String getUserId() {
        return mSharedPreferences.getString (AppConstants.USER_ID,"");
    }

    @Override
    public void setIsLoginDone(boolean state) {
        mEditor.putBoolean(AppConstants.SP_IS_USER_LOGGED_IN,state);
        mEditor.commit();
    }

    @Override
    public boolean getIsLoginDone() {
        return mSharedPreferences.getBoolean(AppConstants.SP_IS_USER_LOGGED_IN, false);
    }


    @Override
    public void setIsNeedToReadAgain(boolean state) {
        mEditor.putBoolean(AppConstants.SP_TERM_READ,state);
        mEditor.commit();
    }

    @Override
    public boolean getIsTermRead() {
        return mSharedPreferences.getBoolean(AppConstants.SP_TERM_READ, true);
    }

    @Override
    public void setAccessToken(String accessToken) {

    }

    @Override
    public String getAccessToken() {
        return null;
    }


    @Override
    public void setPhoneNumber(String phoneNumber) {
        mEditor.putString ("UserPhoneNumber",phoneNumber);
        mEditor.commit();
    }

    @Override
    public String getPhoneNumber() {
        return mSharedPreferences.getString ("UserPhoneNumber", "");
    }

    @Override
    public void setFirebaseToken(String token) {
        mEditor.putString ("FirebaseToken",token);
        mEditor.commit();
    }

    @Override
    public String getFirebaseToken() {
        return mSharedPreferences.getString ("FirebaseToken", "");
    }


}
