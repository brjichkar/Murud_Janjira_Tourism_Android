package com.login_section.mvp;

import com.login_section.model.login_request.LoginRequest;
import com.login_section.model.login_response.LoginResData;


public class LoginPresenterImplementer implements LoginMVP.LoginPresenter, LoginMVP.LoginModel.OnLoginFinished {
    private LoginMVP.LoginView mViews;
    private LoginModelImplementer mModel=new LoginModelImplementer();

    public LoginPresenterImplementer(LoginMVP.LoginView mViews) {
        this.mViews = mViews;
    }

    @Override
    public void onAttach(LoginMVP.LoginView mView) {
        mViews=mView;
    }

    @Override
    public void onDestroyView() {
        mViews=null;
    }


    @Override
    public void onProcessLogin(LoginRequest loginRequest) {
        if(mViews!=null){
            if(mViews.isNetworkConnected ()){
                mViews.showLoading ();
                mModel.processLogin (loginRequest,this);
            }
            else{
                mViews.hideLoading ();
                mViews.onError ("Please connect with Internet.");
            }
        }
    }

    @Override
    public void onLoginSuccess(LoginResData result) {
        if(mViews!=null){
            mViews.hideLoading ();
            mViews.onLoginSuccess (result);
        }
    }

    @Override
    public void onLoginFailed(String errorMsg) {
        if(mViews!=null){
            mViews.hideLoading ();
            mViews.onLoginFailed (errorMsg);
        }
    }
}
