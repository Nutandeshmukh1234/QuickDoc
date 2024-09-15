package com.example.quickdoc;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;


public class MyProfileFragment extends Fragment {

    ImageView imageView;
    AppCompatButton button1,button2;
    TextView textView1,textView2, textView3,textView4;
    ProgressDialog progressDialog;
    SharedPreferences sharedPreferences;


    String strUsername;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_profile, container, false);

        sharedPreferences= PreferenceManager.getDefaultSharedPreferences(getActivity());
        strUsername = sharedPreferences.getString("username","");


        imageView = view.findViewById(R.id.ivQuickDocLogo);
        button1= view.findViewById(R.id.btnChangeProfile);
        button2= view.findViewById(R.id.btnSignOut);
        textView1=view.findViewById(R.id.tvNameUser);
        textView2=view.findViewById(R.id.tvMobilenoUser);
        textView3=view.findViewById(R.id.tvEmailidUser);
        textView4=view.findViewById(R.id.tvUsernameUser);


       return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("My Profile");
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCanceledOnTouchOutside(true);
        progressDialog.show();

        getMyDetails();
    }

    private void getMyDetails() {
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        RequestParams params =  new RequestParams();

        params.put("username",strUsername);

        asyncHttpClient.post("http://192.168.199.113:80/QuickDoc/quickdocMyDetails.php",params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);

                try {
                    JSONArray jsonArray = response.getJSONArray("getMyDetails");

                    for (int i=0; i<jsonArray.length(); i++) {
                        progressDialog.dismiss();
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String id = jsonObject.getString("id");
                        String image = jsonObject.getString("image");
                        String name = jsonObject.getString("name");
                        String modileno = jsonObject.getString("modileno");
                        String emailid = jsonObject.getString("emailid");
                        String username = jsonObject.getString("username");

                        textView1.setText(name);
                        textView2.setText(modileno);
                        textView3.setText(emailid);
                        textView4.setText(username);

                        Glide.with(getActivity())
                                .load("http://192.168.199.113:80/QuickDoc/images/"+image)
                                .skipMemoryCache(true)
                                .diskCacheStrategy(DiskCacheStrategy.NONE)
                                .error(R.drawable.imagenotfound)
                                .into(imageView);

                    }

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                progressDialog.dismiss();
                Toast.makeText(getActivity(),"Could not Connect",Toast.LENGTH_SHORT).show();
            }
        });
    }

}