package com.example.auotmaticattendance.Student_section;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.auotmaticattendance.R;


import java.util.List;

public class view_StudentAdapter extends RecyclerView.Adapter<view_StudentViewHolder> {
    private List<viewstudentobject> viewstudentobjectList;
    private Context context;


    public view_StudentAdapter(List<viewstudentobject> viewstudentobjectList, Context context)
    {
        this.viewstudentobjectList = viewstudentobjectList;
        this.context=context;
    }
    @NonNull
    @Override
    public view_StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View layoutView= LayoutInflater.from(parent.getContext()).inflate(R.layout.view_student_item,null,false);
        RecyclerView.LayoutParams lp =new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutView.setLayoutParams(lp);
        view_StudentViewHolder rcv=new view_StudentViewHolder((layoutView));
        return rcv;

    }

    @Override
    public void onBindViewHolder(@NonNull view_StudentViewHolder holder, int position) {
        holder.mname.setText(viewstudentobjectList.get(position).getName());
        if(!viewstudentobjectList.get(position).getProfilepic().equals("default"))
        {
            Glide.with(context).load(viewstudentobjectList.get(position).getProfilepic()).into(holder.mprofilepic);
        }
        holder.mrollno.setText(viewstudentobjectList.get(position).getRollno());
        holder.memail.setText(viewstudentobjectList.get(position).getEmail());
        holder.mphone.setText(viewstudentobjectList.get(position).getPhone());

    }

    @Override
    public int getItemCount() {
        return viewstudentobjectList.size();
    }
}
