package com.example.auotmaticattendance.Ac_section.Upload_Ac;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.cardview.widget.CardView;

import android.animation.ValueAnimator;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.auotmaticattendance.Ac_section.View_Ac.ViewAcActivity;
import com.example.auotmaticattendance.R;
import com.example.auotmaticattendance.Setting_section.SaveTheme;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import static java.lang.Integer.parseInt;

public class UpdateAcActivity extends AppCompatActivity {

    private TextView mname,mrollno,mtotalattendance,mcurrentac,mtotalac,maddon,msubject,mtotalclasses,mbranch;
    private EditText maddac;
    private ImageView mpic;
    private Button mupdatebtn;
    DatabaseReference mdatabaseReference;
    String rollno,totalac,key;
    String ac,attended, add_ac="",totalclasses,branch,subject;
    private int add=0;
    private CardView cardView;
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
        setContentView(R.layout.activity_update_ac);
        setTitle("Update AC");
        Bundle bundle=getIntent().getExtras();
        if(bundle!=null) {
            key = bundle.getString("key");
            rollno = bundle.getString("rollno");
            totalac = bundle.getString("total Ac");
            branch = bundle.getString("branch");
            subject = bundle.getString("subject");
            Log.d("BRANCh", branch);
            Log.d("SUBJECT", subject);
        }
        ProgressDialog dialog=new ProgressDialog(this);
        dialog.setMessage("loading...");
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        mname=findViewById(R.id.name);
        mbranch=findViewById(R.id.branch);
        cardView=findViewById(R.id.card);
        msubject=findViewById(R.id.subject);
        mpic=findViewById(R.id.image);
        msubject.setText(subject);
        mbranch.setText(branch);
        mtotalclasses=findViewById(R.id.totalclasses);
        mrollno=findViewById(R.id.rollno);
        mupdatebtn=findViewById(R.id.updatenbtn);
        maddon=findViewById(R.id.addon);
        mtotalattendance=findViewById(R.id.totalattendance);
        mcurrentac=findViewById(R.id.presentac);
        mtotalac=findViewById(R.id.totalac);
        maddac=findViewById(R.id.addac);
        mrollno.setText(rollno);
        mdatabaseReference= FirebaseDatabase.getInstance().getReference().child("Users").child("Students").child(key);

        mdatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                                              @Override
                                                              public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                                  if (snapshot.exists() && snapshot.getChildrenCount() > 0) {
                                                                      Map map = (Map) snapshot.getValue();

                                                                      if (map.get("name") != null) {
                                                                          String name = map.get("name").toString();
                                                                          mname.setText(name);

                                                                      }
                                                                      if (map.get("profilepic") != null) {
                                                                          String profileimageurl = (String) map.get("profilepic");
//                                                                          Toast.makeText(UpdateAcActivity.this, profileimageurl, Toast.LENGTH_SHORT).show();
                                                                          switch (profileimageurl) {
                                                                              case "default":
                                                                                  Glide.with(getApplication()).load(R.mipmap.profile).placeholder(R.mipmap.profile).into(mpic);

                                                                                  break;
                                                                              default:
                                                                                  Glide.with(getApplication()).load(profileimageurl).into(mpic);

                                                                                  break;
                                                                          }
                                                                      }



                    mdatabaseReference.child("subjects").child(subject).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.exists() && snapshot.getChildrenCount()>0) {

                                Map map = (Map) snapshot.getValue();
                                if(map.get("Total Ac")!=null) {

                                  ac = map.get("Total Ac").toString();
                                    Log.d("add",ac);
                                    mcurrentac.setText(ac);
                                    mtotalac.setText(ac);
                                    maddac.requestFocus(4);
                                }
                                if(map.get("Attended")!=null) {
                                   attended = map.get("Attended").toString();
                                   int totalattend=parseInt(attended)+parseInt(ac);
                                   startCountAnimation(totalattend,mtotalattendance);
                                }
                                 if(map.get("Total Classes")!=null) {
                                   totalclasses= map.get("Total Classes").toString();
                                   int totclasses=Integer.valueOf(totalclasses);
                                     startCountAnimation(totclasses,mtotalclasses);
                                     dialog.dismiss();

                                }


                            }
                            }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        TextWatcher fieldValidatorTextWatcher = new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                    add_ac=maddac.getText().toString();
                     int r=0;
                     if(!add_ac.equals(""))
                     {
                         maddon.setVisibility(View.VISIBLE);
                         maddon.setText("+"+add_ac);
                         r=Integer.valueOf(add_ac)+Integer.valueOf(ac);
                         mtotalac.setText(String.valueOf(r));
                     }else{
                         maddon.setVisibility(View.INVISIBLE);
                         mtotalac.setText(ac);

                     }


            }

            private boolean filterLongEnough() {
                return maddac.getText().toString().trim().length() > 2;
            }
        };
        maddac.addTextChangedListener(fieldValidatorTextWatcher);


        mupdatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!add_ac.equals("") && !add_ac.equals("0"))
                {
                    boolean s1=(Integer.valueOf(add_ac) + Integer.valueOf(ac)<5);
                    boolean s2=parseInt(attended)+parseInt(add_ac)+parseInt(ac)<Integer.valueOf(totalclasses)+1;
                    if(  s1 && s2) {
                        DatabaseReference subdatabaseReference = mdatabaseReference.child("subjects").child(subject);
                        HashMap subinfo = new HashMap();
                        subinfo.put("Total Classes", totalclasses);
                        subinfo.put("Total Ac", String.valueOf(Integer.valueOf(add_ac) + Integer.valueOf(ac)));
                        subdatabaseReference.updateChildren(subinfo);
                        Intent intent = new Intent(UpdateAcActivity.this, ViewAcActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        Bundle b=new Bundle();
                        b.putString("branch",branch);
                        b.putString("subject",subject);
                        intent.putExtras(b);
                        startActivity(intent);
                        Toast.makeText(UpdateAcActivity.this, "Ac Updated!", Toast.LENGTH_SHORT).show();
                        overridePendingTransition(R.anim.leftstart, R.anim.rightend);
                        finish();
                    }else{
                        if(!s2)
                        {
                            Toast.makeText(UpdateAcActivity.this, "Max Attendance Reached!", Toast.LENGTH_SHORT).show();
                        }else
                            Toast.makeText(UpdateAcActivity.this, "MAX Ac Limit Reached!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


    }

    private void startCountAnimation(int attendance,TextView textView) {
        ValueAnimator animator = ValueAnimator.ofInt(0, attendance);
        animator.setDuration(3000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
               textView.setText(animation.getAnimatedValue().toString());
            }
        });
        animator.start();
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
}