package com.example.auotmaticattendance.Student_section;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.auotmaticattendance.R;

public class view_StudentViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView mname,mrollno,mphone,memail;
   public ImageView mprofilepic;
    public view_StudentViewHolder(@NonNull View itemView) {
        super(itemView);
//        itemView.setOnClickListener(this);
        memail= itemView.findViewById(R.id.email);
        mname = itemView.findViewById(R.id.name);
       mprofilepic=itemView.findViewById(R.id.profilepic);
       mrollno = itemView.findViewById(R.id.rollno);
       mphone = itemView.findViewById(R.id.phone);

    }


    @Override
    public void onClick(View v) {
//        Toast.makeText(v.getContext(), "working", Toast.LENGTH_SHORT).show();
//        Intent intent=new Intent(v.getContext(), UpdateAcActivity.class);
//        Bundle b=new Bundle();
//        b.putString("key",mkey.getText().toString());
//        b.putString("rollno",mrollno.getText().toString());
//        b.putString("total Ac",mtotalac.getText().toString());
//        b.putString("branch",mbranch.getText().toString());
//        b.putString("subject",msubject.getText().toString());
//        intent.putExtras(b);
//        v.getContext().startActivity(intent);
    }
}