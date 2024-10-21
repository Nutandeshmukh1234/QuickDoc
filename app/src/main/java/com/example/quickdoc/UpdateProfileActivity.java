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

public class UpdateProfileActivity extends AppCompatActivity {

    EditText etName, etMobileno, etEmailid, etUsername;
    AppCompatButton btnUpdateProfile;
    String strName, strMobileno, strEmailid, strUsername;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        etName = findViewById(R.id.etUpdateName);
        etMobileno = findViewById(R.id.etUpdateMobileNumber);
        etEmailid = findViewById(R.id.etUpdateEmailId);
        etUsername = findViewById(R.id.etUpdateUsername);

        btnUpdateProfile = findViewById(R.id.btnUpadeteProfile);

        strName = getIntent().getStringExtra("name");
        strMobileno = getIntent().getStringExtra("mobileno");
        strEmailid = getIntent().getStringExtra("emailid");
        strUsername = getIntent().getStringExtra("username");

        etName.setText(strName);
        etMobileno.setText(strMobileno);
        etEmailid.setText(strEmailid);
        etUsername.setText(strUsername);

        btnUpdateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog = new ProgressDialog(UpdateProfileActivity.this);
                progressDialog.setTitle("Update Profile");
                progressDialog.setMessage("Please wait");
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();

                updateProfile();
            }
        });

    }

    private void updateProfile() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        params.put("name", etName.getText().toString());
        params.put("mobileno", etMobileno.getText().toString());
        params.put("emailid", etEmailid.getText().toString());
        params.put("username", etUsername.getText().toString());

        client.post("http://192.168.221.99:80/QuickDoc/updateProfileDetails.php", params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    String status = response.getString("Success");
                    if (status.equals("1")) 
                    {
                        progressDialog.dismiss();
                        Toast.makeText(UpdateProfileActivity.this, "Profile Update Successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(UpdateProfileActivity.this, MyProfileFragment.class);
                        startActivity(intent);
                    }
                    else{
                        progressDialog.dismiss();
                        Toast.makeText(UpdateProfileActivity.this, "Update Not Done", Toast.LENGTH_SHORT).show();

                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                progressDialog.dismiss();
                Toast.makeText(UpdateProfileActivity.this, "Server Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

