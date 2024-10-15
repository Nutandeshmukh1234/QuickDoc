package com.example.quickdoc;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class CategoryWiseDoctorListActivity extends AppCompatActivity {
    SearchView searchViewCategoryWiseDoctorList;
    ListView listView;
    TextView textView;
    String strcategoryname;
    List<POJOCategoryWiseDoctorList> pojoCategoryWiseDoctorLists;
    AdapterCategoryWiseDoctorList adapterCategoryWiseDoctorList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_wise_doctor_list);

        searchViewCategoryWiseDoctorList = findViewById(R.id.svHomeFragmentSearchCategaoryDoctorList);
        listView = findViewById(R.id.lvListHomeFragment);
        textView=findViewById(R.id.tvNotFoundCategorWiseDoctorListFragment);

        strcategoryname=getIntent().getStringExtra("categoryname");


        pojoCategoryWiseDoctorLists = new ArrayList<>();

        getCategoryWiseDoctorList();

        searchViewCategoryWiseDoctorList.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                searchdoctorbyCategory(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                searchdoctorbyCategory(s);
                return false;
            }
        });

    }

    private void searchdoctorbyCategory(String s) {
        List<POJOCategoryWiseDoctorList> templist = new ArrayList<>();
        templist.clear();

        for (POJOCategoryWiseDoctorList obj:pojoCategoryWiseDoctorLists) {
            if (obj.getDoctorname().toUpperCase().contains(s.toUpperCase()) ||
                    obj.getHospitalname().toUpperCase().contains(s.toUpperCase()) ||
                    obj.getDoctorfield().toUpperCase().contains(s.toUpperCase()) ||
                    obj.getDoctorimage().toUpperCase().contains(s.toUpperCase())) {

                templist.add(obj);

            }
            adapterCategoryWiseDoctorList = new AdapterCategoryWiseDoctorList(templist, this);
            listView.setAdapter(adapterCategoryWiseDoctorList);
        }

    }

    private void getCategoryWiseDoctorList() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        params.put("categoryname",strcategoryname);

        client.post("http://192.168.220.113:80/QuickDoc/quickdoccategortwisetable.php",params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);

                try {
                    JSONArray jsonArray = response.getJSONArray("getAllCategoryWiseDoctorList");

                    if (jsonArray.isNull(0)){

                       listView.setVisibility(View.GONE);
                       textView.setVisibility(View.VISIBLE);
                    }
                    for (int i=0; i<jsonArray.length(); i++) {

                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String id = jsonObject.getString("id");
                        String doctorimage = jsonObject.getString("doctorimage");
                        String doctorname = jsonObject.getString("doctorname");
                        String hospitalname = jsonObject.getString("hospitalname");
                        String doctorfield = jsonObject.getString("doctorfield");
                        String doctorexperience = jsonObject.getString("doctorExperience");
                        String doctorprice = jsonObject.getString("doctorprice");
                        String doctorrating = jsonObject.getString("doctorrating");
                        String doctortime = jsonObject.getString("doctortime");
                        String doctordescription = jsonObject.getString("doctordescription");

                        pojoCategoryWiseDoctorLists.add(new POJOCategoryWiseDoctorList(id, doctorimage, doctorname,hospitalname ,doctorfield, doctorexperience,
                                doctorprice,doctorrating,doctortime,doctordescription));
                    }
                    adapterCategoryWiseDoctorList = new AdapterCategoryWiseDoctorList(pojoCategoryWiseDoctorLists,CategoryWiseDoctorListActivity.this);
                    listView.setAdapter(adapterCategoryWiseDoctorList);


                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Toast.makeText(CategoryWiseDoctorListActivity.this,"Server Error",Toast.LENGTH_SHORT).show();
            }
        });
    }
}