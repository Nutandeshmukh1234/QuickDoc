package com.example.quickdoc;

import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
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

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;


public class HomeFragment extends Fragment {
    ListView listView;
    TextView textView;
    List<POJOgetAllDetails> pojOgetAllDetails;
    AdaptergetAllDetails adaptergetAllDetails;
    SearchView searchView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        listView = view.findViewById(R.id.lvListHomeFragment);
        textView=view.findViewById(R.id.tvNotFoundCategorWiseDoctorListFragment);
        searchView= view.findViewById(R.id.svHomeFragmentSearchCategaoryDoctorList);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchView(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                searchView(query);
                return false;
            }
        });

        pojOgetAllDetails = new ArrayList();

        getAllCategariesDetails();


        return view;
    }

    private void searchView(String query) {
        List<POJOgetAllDetails> tempcategory = new ArrayList<>();
        tempcategory.clear();
        for (POJOgetAllDetails obj : pojOgetAllDetails)
        {
            if (obj.getCategoryname().toUpperCase().contains(query.toUpperCase()))
            {
                tempcategory.add(obj);
            } else {
                textView.setVisibility(View.VISIBLE);
            }
            adaptergetAllDetails = new AdaptergetAllDetails(tempcategory,getActivity());
            listView.setAdapter(adaptergetAllDetails);

        }

    }

    private void getAllCategariesDetails() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

                client.post("http://192.168.220.113:80/QuickDoc/quickdocgetAllCategoryDetails.php",params,new JsonHttpResponseHandler(){
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);

                        try {
                            JSONArray jsonArray = response.getJSONArray("getAllCategory");

                            for (int i=0; i<jsonArray.length(); i++) {

                                JSONObject jsonObject = jsonArray.getJSONObject(i);

                                String id = jsonObject.getString("id");
                                String categoryimage = jsonObject.getString("categoryimage");
                                String categoryname = jsonObject.getString("categoryname");

                                pojOgetAllDetails.add(new POJOgetAllDetails(id,categoryimage,categoryname));
                            }

                            adaptergetAllDetails = new AdaptergetAllDetails(pojOgetAllDetails,getActivity());
                            listView.setAdapter(adaptergetAllDetails);

                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        super.onFailure(statusCode, headers, throwable, errorResponse);
                        Toast.makeText(getActivity(),"Server Error",Toast.LENGTH_SHORT).show();
                    }
                });
    }


}