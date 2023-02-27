package com.example.auotmaticattendance.Account_Access_Section;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.auotmaticattendance.MainActivity;
import com.example.auotmaticattendance.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class GenderActivity extends AppCompatActivity {


    LinearLayout malelayout, femalelayout;
    private ImageView maleimg,femaleimg;
    private TextView maletext,femaletext,personname;
    DatabaseReference databaseReference;
    FirebaseAuth mauth;
    private Button mnext;
    String name,userid,gender="";
    int rand=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gender);


        mauth=FirebaseAuth.getInstance();
        userid=mauth.getCurrentUser().getUid();
        databaseReference= FirebaseDatabase.getInstance().getReference().child("Users").child("Teachers").child(userid);
        malelayout=findViewById(R.id.malelayout);
        femalelayout=findViewById(R.id.femalelayout);
        maleimg=findViewById(R.id.maleimg);
        femaleimg=findViewById(R.id.femaleimg);
        maletext=findViewById(R.id.maletext);
        femaletext=findViewById(R.id.femaletext);
       personname=findViewById(R.id.personname);
       mnext=findViewById(R.id.next);
        Bundle bundle = getIntent().getExtras();
        name= bundle.getString("name");
         personname.setText("Hi,  "+name);


         mnext.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Log.d("Gender",gender);
                 if(!gender.equals("")) {
                     if(rand==1)
                     {
                         Map userinfo = new HashMap();
                         userinfo.put("gender", gender);
                         databaseReference.updateChildren(userinfo);
                         Intent intent = new Intent(GenderActivity.this, MainActivity.class);
                         intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                         startActivity(intent);
                         Toast.makeText(GenderActivity.this, "Profile Completed!", Toast.LENGTH_SHORT).show();
                         overridePendingTransition(R.anim.rightstart, R.anim.leftend);
                         finish();
                     }else {
                         Intent intent = new Intent(GenderActivity.this, Depart_PhoneActivity.class);
                         intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                         Bundle bundle = new Bundle();
                         bundle.putString("gender", gender);
                         intent.putExtras(bundle);
                         startActivity(intent);
                         overridePendingTransition(R.anim.rightstart, R.anim.leftend);
                     }
                 }
             }
         });
    }

    public void onmaleclick(View view)
    {
        ViewGroup.LayoutParams mparams = malelayout.getLayoutParams();
        ViewGroup.LayoutParams fparams = femalelayout.getLayoutParams();
        int heightmale = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 160, getResources().getDisplayMetrics());
        int widthmale = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 160, getResources().getDisplayMetrics());
        int heightfemale = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 130, getResources().getDisplayMetrics());
        int widthfemale = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 130, getResources().getDisplayMetrics());
        mparams.width=widthmale;
        mparams.height= heightmale;
        fparams.width=widthfemale;
        fparams.height= heightfemale;
        malelayout.setLayoutParams(mparams);
        femalelayout.setLayoutParams(fparams);
        malelayout.setBackgroundResource(R.drawable.malecircularborder);
        femalelayout.setBackgroundResource(R.drawable.circularborder);
        maleimg.setImageResource(R.mipmap.malecolor);
        femaleimg.setImageResource(R.mipmap.femalecolorless);
        maletext.setTextColor(Color.parseColor("#ff3399"));
        femaletext.setTextColor(Color.parseColor("#ffffff"));
        gender="Male";
    }
    public void onfemaleclick(View view)
    {
        ViewGroup.LayoutParams mparams = malelayout.getLayoutParams();
        ViewGroup.LayoutParams fparams = femalelayout.getLayoutParams();
        int heightfemale = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 160, getResources().getDisplayMetrics());
        int widthfemale = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 160, getResources().getDisplayMetrics());
        int heightmale = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 130, getResources().getDisplayMetrics());
        int widthmale = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 130, getResources().getDisplayMetrics());
        mparams.width=widthmale;
        mparams.height= heightmale;
        fparams.width=widthfemale;
        fparams.height= heightfemale;
        malelayout.setLayoutParams(mparams);
        femalelayout.setLayoutParams(fparams);
        femalelayout.setBackgroundResource(R.drawable.femalecircularborder);
        malelayout.setBackgroundResource(R.drawable.circularborder);
        maleimg.setImageResource(R.mipmap.malecolorless);
        femaleimg.setImageResource(R.mipmap.femalecolor);
        femaletext.setTextColor(Color.parseColor("#ff3399"));
        maletext.setTextColor(Color.parseColor("#ffffff"));
        gender="Female";

    }

    @Override
    protected void onStart() {
        super.onStart();
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists() && snapshot.getChildrenCount() > 0) {

                    Map map = (Map) snapshot.getValue();
                    String depart = map.get("department").toString();
                    if (!depart.equals("default")) {
                        mnext.setText("Confrim");
                        mnext.setBackgroundResource(R.drawable.customcolor3);
                        rand=1;
//                        Intent intent = new Intent(getApplicationContext(), GenderActivity.class);
//                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                        Bundle bundle = new Bundle();
//                        bundle.putString("name", name);
//                        intent.putExtras(bundle);
//                        startActivity(intent);
//                        finish();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}