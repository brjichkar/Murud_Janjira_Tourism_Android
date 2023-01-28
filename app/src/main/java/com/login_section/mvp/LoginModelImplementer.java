package com.login_section.mvp;




import com.api_section.ApiClient;
import com.api_section.ApiInterface;
import com.login_section.model.login_request.LoginRequest;
import com.login_section.model.login_response.LoginResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginModelImplementer implements LoginMVP.LoginModel {
    ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

    @Override
    public void processLogin(LoginRequest loginRequest, final OnLoginFinished onLoginFinished) {
        Call<LoginResponse> call = apiService.getLoginDetails (loginRequest);
        call.enqueue(new Callback<LoginResponse> () {
            @Override
            public void onResponse(Call<LoginResponse>call, Response<LoginResponse> response) {
                if (response.code() == 200 ) {
                    if (response.body()!=null && response.body ().getData()!=null) {
                        onLoginFinished.onLoginSuccess (response.body().getData().getData());
                    } else {
                        onLoginFinished.onLoginFailed ("Invalid Login");
                    }
                } else {
                    onLoginFinished.onLoginFailed ("Invalid Login");
                }
            }

            @Override
            public void onFailure(Call<LoginResponse>call, Throwable t) {
                onLoginFinished.onLoginFailed ("");
            }
        });
    }
}
