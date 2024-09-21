package com.example.quickdoc;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class SetUpNewPasswordActivity extends AppCompatActivity {
    EditText editTextNewPassword,editTextConformPassword;
    AppCompatButton appCompatButton;
    ProgressDialog progressDialog;
    private String strmobileno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_up_new_password);

        editTextNewPassword=findViewById(R.id.etNewPassword);
        editTextConformPassword=findViewById(R.id.etConformPassword);
        appCompatButton=findViewById(R.id.btnChangePassword);

        strmobileno=getIntent().getStringExtra("modileno");

        appCompatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editTextNewPassword.getText().toString().isEmpty()) {
                    editTextNewPassword.setError("Please Enter a New Password");
                } else if (editTextConformPassword.getText().toString().isEmpty()) {
                    editTextNewPassword.setError("Please Enter a New Password");
                } else if (editTextNewPassword.getText().toString().length() < 8) {
                    editTextNewPassword.setError("Please Enter a 8 Digit Number");
                } else if (editTextConformPassword.getText().toString().length() < 8) {
                    editTextConformPassword.setError("Please Enter a 8 Digit Number");
                } else if (!editTextNewPassword.getText().toString().equals(editTextConformPassword.getText().toString())){
                    editTextConformPassword.setError("Password Does Not Match");
                } else {
                    progressDialog = new ProgressDialog(SetUpNewPasswordActivity.this);
                    progressDialog.setTitle("Change Password");
                    progressDialog.setMessage("Please Wait..");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();

                    forgetPassword();
                }
            }
        });
    }
    private void forgetPassword() {

        AsyncHttpClient asyncHttpClient = new AsyncHttpClient(); // client server communication
        RequestParams requestParams = new RequestParams(); //get the data

        requestParams.put("modileno",strmobileno);
        requestParams.put("password",editTextNewPassword);

        asyncHttpClient.post("http://192.168.199.113:80/QuickDoc/quickdocchangePassword.php",requestParams,new JsonHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    String string = response.getString("success");
                    if (string.equals("1"))
                    {
                        progressDialog.dismiss();
                        Intent i = new Intent(SetUpNewPasswordActivity.this,LoginActivity.class);
                        startActivity(i);
                        Toast.makeText(SetUpNewPasswordActivity.this,"Change Password Sucessful",Toast.LENGTH_SHORT).show();
                        finish();
                    }else {
                        progressDialog.dismiss();
                        Toast.makeText(SetUpNewPasswordActivity.this,"Already data Exists",Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject response) {
                super.onFailure(statusCode, headers, throwable, response);
                progressDialog.dismiss();
                Toast.makeText(SetUpNewPasswordActivity.this,"Server Error",Toast.LENGTH_SHORT).show();

            }
        });
    }
}