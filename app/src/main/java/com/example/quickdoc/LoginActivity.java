package com.example.quickdoc;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class LoginActivity extends AppCompatActivity {
    EditText etusername, etPassword;
    CheckBox cbLogin;
    TextView tvForget,tvContactWedSiteText;
    Button btnLogin;
    TextView tvLoginSign;
    GoogleSignInOptions googleSignInOptions;
    GoogleSignInClient googleSignInClient;
    ProgressDialog progressDialog;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etusername = findViewById(R.id.etUsernameTitle);
        etPassword = findViewById(R.id.etPasswordTitle);
        cbLogin = findViewById(R.id.cbShowPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvLoginSign = findViewById(R.id.tvNewUser);
        tvForget=findViewById(R.id.tvForgotText);
        tvContactWedSiteText=findViewById(R.id.tvContactWedSiteText);

        sharedPreferences= PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
        editor = sharedPreferences.edit();

        if (sharedPreferences.getBoolean("isLogin",false)){
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(intent);
        }

        googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        googleSignInClient = GoogleSignIn.getClient(LoginActivity.this,googleSignInOptions);

        tvContactWedSiteText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }


        });
        tvForget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this,ConformMobilenoActivity.class);
                startActivity(i);
            }
        });

        cbLogin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etusername.getText().toString().isEmpty()) {
                    etusername.setError("Please Enter Your Username");
                } else if (etPassword.getText().toString().isEmpty()) {
                    etPassword.setError("Please Enter Your Password");
                } else if (etusername.getText().toString().length() < 8) {
                    etusername.setError("Please Enter 8 Character");
                } else if (etPassword.getText().toString().length() < 8) {
                    etPassword.setError("Please Enter 8 Character");
                } else {
                    editor.putBoolean("isLogin",true).commit();
                    progressDialog = new ProgressDialog(LoginActivity.this);
                    progressDialog.setTitle("QuickDoc");
                    progressDialog.setMessage("Please Wait");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();

                    userlogin();

                }
            }
        });

        tvLoginSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(LoginActivity.this,Register_Activity.class);
                startActivity(i);
            }
        });
    }
    private void signIn() {
        Intent i = googleSignInClient.getSignInIntent();
        startActivityForResult(i,999);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 999) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                task.getResult(ApiException.class);
                Intent i = new Intent(LoginActivity.this, Email_Activity.class);
                startActivity(i);
                finish();
            } catch (ApiException e) {
                Toast.makeText(this, "Something went Wrong", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void userlogin() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        params.put("username",etusername.getText().toString());
        params.put("password",etPassword.getText().toString());

        client.post("http://192.168.199.113:80/QuickDoc/quickdocuserLogin.php",params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response); try {
                    String string = response.getString("success");
                    if (string.equals("1"))
                    {
                        progressDialog.dismiss();
                        Toast.makeText(LoginActivity.this,"Login Successful",Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(LoginActivity.this,HomeActivity.class);
                        startActivity(i);
                        finish();

                    }else {
                        Toast.makeText(LoginActivity.this,"Already data Exists",Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                progressDialog.dismiss();
                Toast.makeText(LoginActivity.this,"Could not Connect",Toast.LENGTH_SHORT).show();
            }
        });
    }
}


