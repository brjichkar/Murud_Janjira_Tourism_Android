package com.login_section.mvp;


import com.base_class_section.MvpView;
import com.login_section.model.login_request.LoginRequest;
import com.login_section.model.login_response.LoginResData;

public class LoginMVP {

    public interface LoginView extends MvpView {
        void onLoginSuccess(LoginResData result);
        void onLoginFailed(String errorMsg);
    }

    public interface LoginPresenter{
        void onAttach(LoginView mView);
        void onDestroyView();

        void onProcessLogin(LoginRequest loginRequest);
    }


    public interface LoginModel{
        void processLogin(LoginRequest loginRequest, OnLoginFinished onLoginFinished);

        interface OnLoginFinished{
            void onLoginSuccess(LoginResData result);
            void onLoginFailed(String errorMsg);
        }
    }
}
