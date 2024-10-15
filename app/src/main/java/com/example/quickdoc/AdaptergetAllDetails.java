package com.example.quickdoc;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

public class AdaptergetAllDetails extends BaseAdapter {
    List<POJOgetAllDetails> pojOgetAllDetails1;
    Activity activity;

    public AdaptergetAllDetails(List<POJOgetAllDetails> list, Activity activity) {
        this.pojOgetAllDetails1 = list;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return pojOgetAllDetails1.size();
    }

    @Override
    public Object getItem(int position) {
        return pojOgetAllDetails1.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        final  ViewHolder viewHolder;
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (view==null){
            viewHolder = new ViewHolder();
            view = inflater.inflate(R.layout.lt_category_shoe_activity,null);
            viewHolder.ivCategaryimage = view.findViewById(R.id.ivCategaryimage);
            viewHolder.tvCategaryName = view.findViewById(R.id.tvCategaryName);
            viewHolder.cardView = view.findViewById(R.id.cdCategoryShow);
            view.setTag(viewHolder);
        }
        else {
            viewHolder=(ViewHolder) view.getTag();
        }
        final POJOgetAllDetails obj = pojOgetAllDetails1.get(position);
        viewHolder.tvCategaryName.setText(obj.getCategoryname());
        Glide.with(activity)
                .load("http://192.168.220.113:80/QuickDoc/images/"+obj.getCategoryimage())
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .placeholder(R.drawable.icon_home)
                .into(viewHolder.ivCategaryimage);
        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(activity,CategoryWiseDoctorListActivity.class);
                i.putExtra("categoryname",obj.getCategoryname());
                activity.startActivity(i);

            }
        });

        return view;
    }
    class ViewHolder
    {
        ImageView ivCategaryimage;
        CardView cardView;
        TextView tvCategaryName;
    }

}
