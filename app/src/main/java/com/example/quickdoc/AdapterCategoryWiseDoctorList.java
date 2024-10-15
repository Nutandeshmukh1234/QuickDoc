package com.example.quickdoc;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

public class AdapterCategoryWiseDoctorList extends BaseAdapter {
    List<POJOCategoryWiseDoctorList> pojoCategoryWiseDoctorLists1;
    Activity activity;

    public AdapterCategoryWiseDoctorList(List<POJOCategoryWiseDoctorList> pojoCategoryWiseDoctorLists1,Activity activity) {
        this.pojoCategoryWiseDoctorLists1 = pojoCategoryWiseDoctorLists1;
        this.activity = activity;
    }


    @Override
    public int getCount() {
        return pojoCategoryWiseDoctorLists1.size();
    }

    @Override
    public Object getItem(int position) {
        return pojoCategoryWiseDoctorLists1.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (view==null){
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.iv_category_wise_doctor,null);
           holder.ivDoctorimage = view.findViewById(R.id.ivCategaryimage);
            holder.tvDoctorName = view.findViewById(R.id.tvCategaryName);
            holder.tvHospitalName = view.findViewById(R.id.tvHospitalName);
            holder.tvDoctorField = view.findViewById(R.id.tvDoctorField);
            holder.tvDoctorExperience = view.findViewById(R.id.tvDoctorExperience);
            holder.tvDoctorPrice = view.findViewById(R.id.tvDoctorPrice);
            holder.tvDoctorRating = view.findViewById(R.id.tvDoctorRating);
            holder.tvDoctorTime = view.findViewById(R.id.tvDoctorTime);
            holder.tvDoctorDescription = view.findViewById(R.id.tvDoctorDescription);
            holder.btnBookClinicVisit = view.findViewById(R.id.btnBookClinicVisit);
            holder.cardView = view.findViewById(R.id.cdCategoryShow);
            view.setTag(holder);
        }
        else {
           holder=(ViewHolder) view.getTag();
        }
        final POJOCategoryWiseDoctorList obj = pojoCategoryWiseDoctorLists1.get(position);
        holder.tvDoctorName.setText(obj.getDoctorname());
        holder.tvHospitalName.setText(obj.getHospitalname());
        holder.tvDoctorField.setText(obj.getDoctorfield());
        holder.tvDoctorExperience.setText(obj.getDoctorexperience());
        holder.tvDoctorPrice.setText(obj.getDoctorprice());
        holder.tvDoctorRating.setText(obj.getDoctorrating());
        holder.tvDoctorTime.setText(obj.getDoctortime());
        holder.tvDoctorDescription.setText(obj.getDoctordescription());

        Glide.with(activity)
                .load("http://192.168.220.113:80/QuickDoc/images/"+obj.getDoctorimage())
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .placeholder(R.drawable.icon_home)
                .into(holder.ivDoctorimage);

        holder.btnBookClinicVisit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(activity,ClinicTimeSlotBookAppointmentActivity.class);
                activity.startActivity(i);

            }
        });
        return view;

    }
    class ViewHolder
    {
        ImageView ivDoctorimage;
        CardView cardView;
        TextView tvDoctorName,tvHospitalName,tvDoctorField,tvDoctorExperience,tvDoctorPrice,tvDoctorRating,
                tvDoctorTime,tvDoctorDescription;
        AppCompatButton btnBookClinicVisit;

    }
}
