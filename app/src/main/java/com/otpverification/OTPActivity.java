package com.otpverification;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.app_preference_section.AppPreference;
import com.google.firebase.auth.FirebaseUser;
import com.otpverification.HomeActivity;
import com.otpverification.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;


public class OTPActivity extends AppCompatActivity{
    private Button mBtnSubmit;
    private CountDownTimer mCTimer = null;
    private TextView mTvTimer;
    private AppPreference mAppPreference;

    private FirebaseAuth mAuth;

    // variable for our text input
    // field for phone and OTP.
    private EditText edtOTP;

    // buttons for generating OTP and verifying OTP
    private Button verifyOTPBtn, generateOTPBtn;

    // string for storing our verification ID
    private String verificationId;
    private String mobile="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        verifyOTPBtn = findViewById(R.id.idBtnVerify);
        mAuth = FirebaseAuth.getInstance();
        edtOTP = findViewById(R.id.idEdtOtp);

        mAppPreference=new AppPreference(this);

        Intent intent = getIntent();
        if (intent.getExtras().containsKey("mobile")) {
            mobile = intent.getStringExtra("mobile");
            sendVerificationCode(mobile);
        }

        findViewById(R.id.idBtnVerify).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OTPActivity.this, HomeActivity.class);

                startActivity(intent);

            }
        });
        //if the automatic sms detection did not work, user can also enter the code manually
        //so adding a click listener to the button
        findViewById(R.id.idBtnVerify).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = edtOTP.getText().toString().trim();
                if (code.isEmpty() || code.length() < 6) {
                    edtOTP.setError("Enter valid code");
                    edtOTP.requestFocus();
                    return;
                }

                //verifying the code entered manually
                verifyCode(code);
            }

        });
    }


    //start timer function
    void startTimer() {
        mTvTimer.setVisibility(View.VISIBLE);
        mCTimer = new CountDownTimer(60000, 1000) {
            public void onTick(long millisUntilFinished) {
                mTvTimer.setVisibility(View.VISIBLE);
                mTvTimer.setText("Valid for " + millisUntilFinished / 1000+" Seconds");
            }
            public void onFinish() {
                mTvTimer.setVisibility(View.GONE);
            }
        };
        mCTimer.start();
    }


    //cancel timer
    void cancelTimer() {
        if(mCTimer!=null){
            mCTimer.cancel();
        }
    }




    // initializing on click listener
    // for verify otp button



    private void signInWithCredential(PhoneAuthCredential credential) {
        // inside this method we are checking if
        // the code entered is correct or not.
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = task.getResult().getUser();
                            Log.d("user_token_temp",user.getUid());
                            mAppPreference.setIsLoginDone(true);
                            mAppPreference.setPhoneNumber(mobile);
                            mAppPreference.setAccessToken(user.getUid());
                            Intent intentHome = new Intent(OTPActivity.this, HomeActivity.class);
                            startActivity(intentHome);
                            finish();

                        } else {
                            Toast.makeText(OTPActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }


    private void sendVerificationCode(String mobile) {
        // this method is used for getting
        // OTP on user phone number.
        // OTP on user phone number.
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber( "+91"+mobile)		 // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)				 // Activity (for callback binding)
                        .setCallbacks(mCallBack)		 // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    // callback method is called on Phone auth provider.
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks

            // initializing our callbacks for on
            // verification callback method.
            mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        // below method is used when
        // OTP is sent from Firebase
        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            // when we receive the OTP it
            // contains a unique id which
            // we are storing in our string
            // which we have already created.
            verificationId = s;
        }

        // this method is called when user
        // receive OTP from Firebase.
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            // below line is used for getting OTP code
            // which is sent in phone auth credentials.
            final String code = phoneAuthCredential.getSmsCode();

            // checking if the code
            // is null or not.
            if (code != null) {
                // if the code is not null then
                // we are setting that code to
                // our OTP edittext field.
                edtOTP.setText(code);
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // after setting this code
                // to OTP edittext field we
                // are calling our verifycode method.
                verifyCode(code);
            }
        }

        // this method is called when firebase doesn't
        // sends our OTP code due to any error or issue.
        @Override
        public void onVerificationFailed(FirebaseException e) {
            // displaying error message with firebase exception.
            Toast.makeText(OTPActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    };

    // below method is use to verify code from Firebase.
    private void verifyCode(String code) {
        // below line is used for getting
        // credentials from our verification id and code.
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);

        // after getting credential we are
        // calling sign in method.
        signInWithCredential(credential);
    }


}
