package com.example.quickdoc;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

import cz.msebera.android.httpclient.Header;

public class Register_Activity extends AppCompatActivity {
    EditText etname, etUsermobileno, etEmailid, etusername, etPassword;
    CheckBox cbSign;
    Button btnUser;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etname = findViewById(R.id.etName);
        etUsermobileno = findViewById(R.id.etMobileNumber);
        etEmailid = findViewById(R.id.etEmailId);
        etusername = findViewById(R.id.etUsernameTitle);
        etPassword = findViewById(R.id.etPassword);
        cbSign = findViewById(R.id.cbShowPassword);
        btnUser = findViewById(R.id.btnRegister);


        cbSign.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        btnUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etname.getText().toString().isEmpty()) {
                    etname.setError("Please Enter Your Name");
                } else if (etUsermobileno.getText().toString().isEmpty()) {
                    etUsermobileno.setError("Please Enter Your Mobile No ");
                } else if (etEmailid.getText().toString().isEmpty()) {
                    etEmailid.setError("Please Enter Your Email ID ");
                } else if (etusername.getText().toString().isEmpty()) {
                    etusername.setError("Please Enter Your Username ");
                } else if (etPassword.getText().toString().isEmpty()) {
                    etPassword.setError("Please Enter Your Email ID ");
                } else if (etUsermobileno.getText().toString().length() != 10) {
                    etUsermobileno.setError("Please Enter Your 10 digit mobile no ");
                } else if (etPassword.getText().toString().length()<8) {
                    etPassword.setError("Please Enter Your Email ID ");
                } else if (!etEmailid.getText().toString().contains("@")||
                        !etEmailid.getText().toString().contains(".com")) {
                    etEmailid.setError("Invalid Email Id");
                } else {
                    progressDialog = new ProgressDialog(Register_Activity.this);
                    progressDialog.setTitle("Registraction");
                    progressDialog.setMessage("Please Wait....");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();

                    PhoneAuthProvider.getInstance().verifyPhoneNumber("+91"+etUsermobileno.getText().toString() ,
                            60, TimeUnit.SECONDS, Register_Activity.this, new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                                @Override
                                public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                    progressDialog.dismiss();
                                }

                                @Override
                                public void onVerificationFailed(@NonNull FirebaseException e) {
                                    progressDialog.dismiss();
                                    Toast.makeText(Register_Activity.this, "Failed OTP", Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onCodeSent(@NonNull String verificationcode, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                    Intent i = new Intent(Register_Activity.this, OTP_Activity.class);
                                    i.putExtra("name",etname.getText().toString());
                                    i.putExtra("modileno",etUsermobileno.getText().toString());
                                    i.putExtra("emailid",etEmailid.getText().toString());
                                    i.putExtra("username",etusername.getText().toString());
                                    i.putExtra("password",etPassword.getText().toString());
                                    i.putExtra("verificationcode",verificationcode);
                                    startActivity(i);


                                }
                            });



            // QuickDocuserRegister();


                }

            }

            private void QuickDocuserRegister() {
                AsyncHttpClient client = new AsyncHttpClient();
                RequestParams params = new RequestParams();

                params.put("name",etname.getText().toString());
                params.put("modileno",etUsermobileno.getText().toString());
                params.put("emailid",etEmailid.getText().toString());
                params.put("username",etusername.getText().toString());
                params.put("password",etPassword.getText().toString());

                client.post("http://192.168.29.70:80/QuickDoc/quickdocuserRegister.php",params,new JsonHttpResponseHandler(){
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response); try {
                            String string = response.getString("success");
                            if (string.equals("1"))
                            {
                                progressDialog.dismiss();
                                Toast.makeText(Register_Activity.this,"Registration Successful",Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(Register_Activity.this,LoginActivity.class);
                                startActivity(i);
                                finish();

                            }else {
                                progressDialog.dismiss();
                                Toast.makeText(Register_Activity.this,"Already data Exists",Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        super.onFailure(statusCode, headers, throwable, errorResponse);
                        Toast.makeText(Register_Activity.this, "Server Issue",Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                });

            }
        });




    }
}