package com.otpverification;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.app_preference_section.AppPreference;
import com.base_class_section.BaseActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.login_section.model.login_request.LoginReqData;
import com.login_section.model.login_request.LoginRequest;
import com.login_section.model.login_response.LoginResData;
import com.login_section.mvp.LoginMVP;
import com.login_section.mvp.LoginPresenterImplementer;
import com.otpverification.R;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends BaseActivity implements View.OnClickListener, LoginMVP.LoginView {

    // variable for FirebaseAuth class
    private FirebaseAuth mAuth;
    private EditText edtPhone, edtOTP;
    private Button verifyOTPBtn, generateOTPBtn;
    private String verificationId;
    public  String user_id;
    public String mobileid;
    private AppPreference mAppPreference;
    private LoginMVP.LoginPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // below line is for getting instance
        // of our FirebaseAuth.
        mAuth = FirebaseAuth.getInstance();

        // initializing variables for button and Edittext.
        edtPhone = findViewById(R.id.idEdtPhoneNumber);
//
        generateOTPBtn = findViewById(R.id.idBtnGetOtp);

        mAppPreference = new AppPreference(this);
        mPresenter = new LoginPresenterImplementer(this);

        // setting onclick listener for generate OTP button.
        generateOTPBtn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                LoginRequest loginRequest = new LoginRequest();
                LoginReqData logReq = new LoginReqData();
                logReq.setMobile(edtPhone.getText().toString());
                logReq.setToken(mAppPreference.getFirebaseToken());
                loginRequest.setJsondata(logReq);
                mPresenter.onProcessLogin(loginRequest);

//                Intent logIntent = new Intent(MainActivity.this, OTPActivity.class);
//                startActivity(logIntent);

            }
        });


        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                if(!task.isSuccessful()){
                    return;
                }
                // Get new Instance ID token
                String token = task.getResult();
                Log.d("Mainactivity", "Refreshed token Mainactivity: " + token);
                mAppPreference = new AppPreference(getApplicationContext());
                mAppPreference.setFirebaseToken(token);
            }
        });

    }


    @Override
    protected void onResume() {
        super.onResume ();
        mPresenter.onAttach (this);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy ();
        mPresenter.onDestroyView ();

    }
    private boolean isValidMobile(String mobileid) {
        if (!TextUtils.isEmpty(mobileid)) {
            return Patterns.PHONE.matcher(mobileid).matches();
        }
        return false;
    }


    @Override
    public void onClick(View v) {

    }

    @Override
    public void onFragmentAttached() {

    }

    @Override
    public void onFragmentDetached(String tag) {

    }

    @Override
    public void onLoginSuccess(LoginResData result) {
//       mAppPreference.setPhoneNumber(result.getMobile());
//       mAppPreference.setUserId(String.valueOf(result.getUserId()));

        Intent logIntent = new Intent(MainActivity.this, OTPActivity.class);
        Bundle b =new Bundle();
        b.putString("mobile",edtPhone.getText().toString());
        mAppPreference.setUserId(String.valueOf(result.getUserId()));
        logIntent.putExtras(b);
        startActivity(logIntent);
        finish();

    }

    @Override
    public void onLoginFailed(String errorMsg) {
        if(errorMsg.equals ("")){
            errorMsg=getResources ().getString (R.string.some_error);

        }
        onError (errorMsg);

    }
}


