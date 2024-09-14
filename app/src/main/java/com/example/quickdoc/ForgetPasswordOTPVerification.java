package com.example.quickdoc;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class ForgetPasswordOTPVerification extends AppCompatActivity {
    EditText editText1,editText2,editText3,editText4,editText5,editText6;
    TextView textView;
    AppCompatButton button1;
    private String strVerificationCode,mobileno;
    ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        editText1 = findViewById(R.id.etOtpVerification1);
        editText2 = findViewById(R.id.etOtpVerification2);
        editText3 = findViewById(R.id.etOtpVerification3);
        editText4 = findViewById(R.id.etOtpVerification4);
        editText5 = findViewById(R.id.etOtpVerification5);
        editText6 = findViewById(R.id.etOtpVerification6);
        textView = findViewById(R.id.tvResendOTP);
        button1 = findViewById(R.id.btnVerify1);

        strVerificationCode = getIntent().getStringExtra("verificationcode");
        mobileno = getIntent().getStringExtra("modileno");


        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText1.getText().toString().trim().isEmpty() || editText2.getText().toString().trim().isEmpty() ||
                        editText3.getText().toString().trim().isEmpty() || editText4.getText().toString().trim().isEmpty() ||
                        editText5.getText().toString().trim().isEmpty() || editText6.getText().toString().trim().isEmpty())
                {
                    Toast.makeText(ForgetPasswordOTPVerification.this, "Please Enter Valid OTP", Toast.LENGTH_SHORT).show();
                }
                String otpCode = editText1.getText().toString() + editText2.getText().toString() +
                        editText3.getText().toString() + editText4.getText().toString() +
                        editText5.getText().toString() + editText6.getText().toString();

                if (strVerificationCode != null) {
                    progress = new ProgressDialog(ForgetPasswordOTPVerification.this);
                    progress.setTitle("Verification OTP");
                    progress.setMessage("Please Wait...");
                    progress.setCanceledOnTouchOutside(false);
                    progress.show();

                    PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(strVerificationCode,otpCode);
                    FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Intent i =new Intent(ForgetPasswordOTPVerification.this, SetUpNewPasswordActivity.class);
                                i.putExtra("modileno",mobileno);
                                startActivity(i);

                            } else {
                                Toast.makeText(ForgetPasswordOTPVerification.this, "OTP Invalid", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                } else {
                    Toast.makeText(ForgetPasswordOTPVerification.this, "OTP Successful", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(ForgetPasswordOTPVerification.this, SetUpNewPasswordActivity.class);
                    startActivity(i);
                }
            }
        });


        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhoneAuthProvider.getInstance().verifyPhoneNumber("+91"+mobileno, 60, TimeUnit.SECONDS, ForgetPasswordOTPVerification.this,
                        new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                Toast.makeText(ForgetPasswordOTPVerification.this, "Failed OTP", Toast.LENGTH_SHORT).show();

                            }

                            @Override
                            public void onCodeSent(@NonNull String newVerificationcode, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                strVerificationCode = newVerificationcode;

                            }
                        });
            }
        });
        setUpOTP();
    }

    private void setUpOTP() {
        editText1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()) {
                    editText2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        editText2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()) {
                    editText3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        editText3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()) {
                    editText4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        editText4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()) {
                    editText5.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        editText5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()) {
                    editText6.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}
