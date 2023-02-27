package com.example.auotmaticattendance.Ac_section.View_Ac;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.auotmaticattendance.Ac_section.Upload_Ac.UpdateAcActivity;
import com.example.auotmaticattendance.R;

public class view_acViewHolder  extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView mname, mrollno, mtotalac,msubject,mbranch,mkey;
    public LinearLayout mlay;

    public view_acViewHolder(@NonNull View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        mname = itemView.findViewById(R.id.name);
        mkey=itemView.findViewById(R.id.key);
        mrollno = itemView.findViewById(R.id.rollno);
        mtotalac = itemView.findViewById(R.id.totalac);
        msubject = itemView.findViewById(R.id.subject);
        mbranch = itemView.findViewById(R.id.branch);
        mlay=itemView.findViewById(R.id.itemac);
    }


    @Override
    public void onClick(View v) {
//        Toast.makeText(v.getContext(), "working", Toast.LENGTH_SHORT).show();
        Intent intent=new Intent(v.getContext(), UpdateAcActivity.class);
        Bundle b=new Bundle();
        b.putString("key",mkey.getText().toString());
        b.putString("rollno",mrollno.getText().toString());
        b.putString("total Ac",mtotalac.getText().toString());
        b.putString("branch",mbranch.getText().toString());
        b.putString("subject",msubject.getText().toString());
        intent.putExtras(b);
        v.getContext().startActivity(intent);
    }
}