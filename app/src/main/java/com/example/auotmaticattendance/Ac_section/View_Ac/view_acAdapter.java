package com.example.auotmaticattendance.Ac_section.View_Ac;


import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.auotmaticattendance.Attendance_section.View_Attendance.view_attenViewHolders;
import com.example.auotmaticattendance.Attendance_section.View_Attendance.viewobject;
import com.example.auotmaticattendance.R;

import java.util.List;

public class view_acAdapter extends RecyclerView.Adapter<view_acViewHolder> {
    private List<viewacobject> viewacobjectList;
    private Context context;


    public view_acAdapter(List<viewacobject> viewacobjectList, Context context)
    {
        this.viewacobjectList=viewacobjectList;
        this.context=context;
    }
    @NonNull
    @Override
    public view_acViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View layoutView= LayoutInflater.from(parent.getContext()).inflate(R.layout.view_ac_item,null,false);
        RecyclerView.LayoutParams lp =new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutView.setLayoutParams(lp);
        view_acViewHolder rcv=new view_acViewHolder((layoutView));
        return rcv;

    }

    @Override
    public void onBindViewHolder(@NonNull view_acViewHolder holder, int position) {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
              holder.mlay.setBackgroundResource(R.drawable.tap_color);
            }
        }, 700);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                holder.mlay.setBackgroundResource(R.drawable.ripple_effect);
            }
        }, 1200);
        holder.mname.setText(viewacobjectList.get(position).getName());
        holder.mrollno.setText(viewacobjectList.get(position).getRollno());
        holder.mkey.setText(viewacobjectList.get(position).getKey());
        holder.mtotalac.setText(viewacobjectList.get(position).getTotal_ac());
        holder.msubject.setText(viewacobjectList.get(position).getSubject());
        holder.mbranch.setText(viewacobjectList.get(position).getBranch());
    }

    @Override
    public int getItemCount() {
        return viewacobjectList.size();
    }
}
