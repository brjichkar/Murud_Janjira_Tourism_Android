package com.api_section;


import com.login_section.model.login_request.LoginRequest;
import com.login_section.model.login_response.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiInterface {

   // ... Login Mobile NO ....//
    @POST("api/Login_check")
    Call<LoginResponse> getLoginDetails(@Body LoginRequest loginRequest);







}
