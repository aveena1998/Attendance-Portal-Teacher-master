package com.example.auotmaticattendance.Ac_section.View_Ac;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.auotmaticattendance.Attendance_section.View_Attendance.viewobject;
import com.example.auotmaticattendance.R;
import com.example.auotmaticattendance.Setting_section.SaveTheme;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ViewAcActivity extends AppCompatActivity {

    private Spinner mbranch,msubject;
    private RecyclerView mrecyclerView;
    private RecyclerView.Adapter mviewacadapter;
    private  RecyclerView.LayoutManager mviewaclayoutManager;
    private ProgressBar mprogressbar;
    private String branch,subject;
    private TextView emptylist;
    int count=0,incr=0;
    SaveTheme saveTheme;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        saveTheme=new SaveTheme(this);
        if(saveTheme.getTheme()==true)
        {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        if(AppCompatDelegate.getDefaultNightMode()==AppCompatDelegate.MODE_NIGHT_YES)
        {
            setTheme(R.style.CreamTheme);
        }else{
            setTheme(R.style.AppTheme);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_ac);
        setTitle("Provisional Attendance");
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        mbranch=findViewById(R.id.branch);
        msubject=findViewById(R.id.subject);
       Bundle bundle=getIntent().getExtras();
       if(bundle!=null)
       {
           String branch=bundle.getString("branch");
           String subject=bundle.getString("subject");
           Log.d("BRANCH","asdfghgfdsasdfgh"+branch);
           mbranch.setSelection(((ArrayAdapter<String>)mbranch.getAdapter()).getPosition(branch));
           msubject.setSelection(((ArrayAdapter<String>)msubject.getAdapter()).getPosition(subject));
       }

        emptylist=findViewById(R.id.emptylist);
        mprogressbar=findViewById(R.id.progressBar);
        mrecyclerView=findViewById(R.id.recyclerView);
        mrecyclerView.setNestedScrollingEnabled(false);
        mrecyclerView.setHasFixedSize(true);
        mviewaclayoutManager=new LinearLayoutManager(ViewAcActivity.this);
        mrecyclerView.setLayoutManager(mviewaclayoutManager);
        mviewacadapter=new view_acAdapter(getdatasetviewac(),ViewAcActivity.this);
        branch = mbranch.getSelectedItem().toString();
        subject = msubject.getSelectedItem().toString();
//        getstudendid();
        mrecyclerView.setAdapter(mviewacadapter);


        mbranch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {
                incr=0;
                resultsViewac.clear();
                mviewacadapter.notifyDataSetChanged();
                emptylist.setVisibility(View.INVISIBLE);
                mprogressbar.setVisibility(View.VISIBLE);
                String selectedBranch = (String) mbranch.getItemAtPosition(i);
                branch=selectedBranch;
                getstudendid();
                mviewacadapter.notifyDataSetChanged();
                count=1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        msubject.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {
                if (count == 1) {
                    incr=0;
                    resultsViewac.clear();
                    mviewacadapter.notifyDataSetChanged();
                    emptylist.setVisibility(View.INVISIBLE);
                    mprogressbar.setVisibility(View.VISIBLE);
                    String selectedSubject = (String) msubject.getItemAtPosition(i);
                    subject = selectedSubject;
                    getstudendid();
                    mviewacadapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }


    private void getstudendid() {

        Query userdb= FirebaseDatabase.getInstance().getReference().child("Users").child("Students").orderByChild("rollno");
        userdb.addListenerForSingleValueEvent(sval);

    }

    private void fetchstudentsinfo(String key){
        Query databaseReference= FirebaseDatabase.getInstance().getReference().child("Users").child("Students").child(key);
        databaseReference.addListenerForSingleValueEvent(val);


    }
    private ArrayList<viewacobject> resultsViewac=new ArrayList<viewacobject>();
    private List<viewacobject> getdatasetviewac() {
        return resultsViewac;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() ==android.R.id.home) {
            super.onBackPressed();
            finish();
            overridePendingTransition(R.anim.leftstart,R.anim.rightend);
        }
        return super.onOptionsItemSelected(item);
    }


    ValueEventListener sval= new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            if(dataSnapshot.exists())
            {
//                    Toast.makeText(ViewAttendanceActivity.this, "hurray", Toast.LENGTH_SHORT).show();
                for(DataSnapshot match:dataSnapshot.getChildren())
                {
                    fetchstudentsinfo(match.getKey());
                }
            }else{
                emptylist.setVisibility(View.VISIBLE);
                mprogressbar.setVisibility(View.INVISIBLE);
            }

        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };


    ValueEventListener val=new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            if(snapshot.exists())
            {
                String key=(String) snapshot.getKey();
                String brnch=(String) snapshot.child("branch").getValue();
                String rollno=(String) snapshot.child("rollno").getValue();
                String name=(String) snapshot.child("name").getValue();
                String attended=String.valueOf(snapshot.child("subjects").child(subject).child("Attended").getValue());
                String totalclass= String.valueOf(snapshot.child("subjects").child(subject).child("Total Classes").getValue());
                String totalac= String.valueOf(snapshot.child("subjects").child(subject).child("Total Ac").getValue());
                if(branch.equals(brnch)) {
                    incr++;
                    viewacobject obk=new viewacobject(key,rollno,name,attended,totalclass,totalac,subject,branch);
                    resultsViewac.add(obk);
                }
            }
            if(incr!=0)
            {
                emptylist.setVisibility(View.INVISIBLE);
            }else{
                emptylist.setVisibility(View.VISIBLE);
            }
            mviewacadapter.notifyDataSetChanged();
            mprogressbar.setVisibility(View.INVISIBLE);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    };
}
