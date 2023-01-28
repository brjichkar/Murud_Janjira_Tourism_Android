package com.app_preference_section;

public interface IPreferenceHelper {
    void setFirstTimeLaunch(boolean state);
    boolean getFirstTimeLaunch();

    void setUserId(String userid);
    String getUserId();

    void setIsLoginDone(boolean state);
    boolean getIsLoginDone();


    void setIsNeedToReadAgain(boolean state);
    boolean getIsTermRead();

    void setAccessToken(String accessToken) ;
    String getAccessToken();

    void setPhoneNumber(String phoneNumber) ;
    String getPhoneNumber();

    void setFirebaseToken(String firebaseToken);
    String getFirebaseToken();


}
