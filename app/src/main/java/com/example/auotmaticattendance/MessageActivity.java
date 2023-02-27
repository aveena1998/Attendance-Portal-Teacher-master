package com.example.auotmaticattendance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.auotmaticattendance.Student_section.ViewStudentActivity;
import com.example.auotmaticattendance.Student_section.viewstudentobject;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class MessageActivity extends AppCompatActivity {

    LinearLayout messagepage;
    ProgressBar progressBar;
    EditText messageedittext;
    Button send;
    DatabaseReference databaseReferencestudents,databaseReferencemsg,databaseReferencerecievedmsg;
    DatabaseReference databaseReferenceuser;
    FirebaseAuth mauth;
    int row=0,oldrow=0;
    String userid,branch,year,teachername,message;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.passAppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_material);
        upArrow.setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        messageedittext=findViewById(R.id.msg);
        send=findViewById(R.id.sendmsg);
        progressBar=findViewById(R.id.progressBar1);
        progressBar.setVisibility(View.INVISIBLE);
        messagepage=findViewById(R.id.messagepage);
        mauth=FirebaseAuth.getInstance();
        FirebaseUser user=mauth.getCurrentUser();
        userid=user.getUid();
        Bundle bundle=getIntent().getExtras();
        if(bundle!=null)
        {
            branch=bundle.getString("branch");
            year=bundle.getString("year");
        }


        databaseReferenceuser=FirebaseDatabase.getInstance().getReference().child("Users").child("Teachers").child(userid);
        fetchteacherinfo();
        databaseReferencestudents= FirebaseDatabase.getInstance().getReference().child("Users").child("Students");


        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                messagepage.setAlpha(0.4f);
                message=messageedittext.getText().toString();
                getstudentid();
                DatabaseReference databaseReferencecreatedmsg=databaseReferenceuser.child("Messages").child("Created Messages").push();
                Map newmessage = new HashMap();
                newmessage.put("message",message);
                newmessage.put("timestamp", ServerValue.TIMESTAMP);
                databaseReferencecreatedmsg.setValue(newmessage);
            }
        });


    }
    private void getstudentid() {

        databaseReferencestudents.addListenerForSingleValueEvent(sval);

    }

    private void fetchstudentsinfo(String key){
        databaseReferencestudents.child(key).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    row++;
//                String name=snapshot.child("name").getValue().toString();
//                String profilepic=snapshot.child("profilepic").getValue().toString();
//                String rollno=snapshot.child("rollno").getValue().toString();
//                String email=snapshot.child("email").getValue().toString();
                    String Year=snapshot.child("year").getValue().toString();
                    String Branch=snapshot.child("branch").getValue().toString();
//                String phone=snapshot.child("phone").getValue().toString();
                    if(Branch.equals(branch) && Year.equals(year)){
                        databaseReferencerecievedmsg= databaseReferencestudents.child(key).child("Messages").child("Received Messages").push();
                        Map newreceivedmessage = new HashMap();
                        newreceivedmessage.put("Created by", teachername);
                        newreceivedmessage.put("message",message);
                        newreceivedmessage.put("timestamp", ServerValue.TIMESTAMP);
                        databaseReferencerecievedmsg.setValue(newreceivedmessage);
                        databaseReferencestudents.child(key).child("Messages").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if(snapshot.exists() && snapshot.getChildrenCount()>0)
                                {
                                    Map map=(Map) snapshot.getValue();
                                    if(map.get("Received Count")==null)
                                    {
//                                        Toast.makeText(MessageActivity.this, "here", Toast.LENGTH_SHORT).show();
                                        Map count= new HashMap();
                                        count.put("Received Count","0");
                                        databaseReferencestudents.child(key).child("Messages").updateChildren(count);
                                    }

                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                }
                if(row==oldrow)
                {
                    progressBar.setVisibility(View.INVISIBLE);
                    messagepage.setAlpha(1f);
                    Intent intent=new Intent(MessageActivity.this, ViewStudentActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    Toast.makeText(MessageActivity.this, "Message Sent", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        }
);

    }
    private void fetchteacherinfo(){
       databaseReferenceuser.addListenerForSingleValueEvent(teacherval);

    }

    ValueEventListener sval= new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            oldrow=(int) dataSnapshot.getChildrenCount();
            if(dataSnapshot.exists() && dataSnapshot.getChildrenCount()>0)
            {
//                    Toast.makeText(MessageActivity.this, "hurray", Toast.LENGTH_SHORT).show();
                for(DataSnapshot match:dataSnapshot.getChildren())
                {
                    fetchstudentsinfo(match.getKey());
                }
            }else{
                progressBar.setVisibility(View.INVISIBLE);
                messagepage.setAlpha(1f);
                Toast.makeText(MessageActivity.this, "No Students Register", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };


    ValueEventListener teacherval=new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            if(snapshot.exists())
            {
                teachername=snapshot.child("name").getValue().toString();
                }


        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    };

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i=new Intent(getApplicationContext(),ViewStudentActivity.class);
        startActivity(i);
        finish();
        overridePendingTransition(R.anim.leftstart,R.anim.rightend);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() ==android.R.id.home) {
            Intent i=new Intent(getApplicationContext(),ViewStudentActivity.class);
            startActivity(i);
            finish();
            overridePendingTransition(R.anim.leftstart,R.anim.rightend);
        }
        return super.onOptionsItemSelected(item);
    }
}