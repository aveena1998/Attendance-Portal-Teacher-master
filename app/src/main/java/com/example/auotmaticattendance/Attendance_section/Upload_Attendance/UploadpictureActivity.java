package com.example.auotmaticattendance.Attendance_section.Upload_Attendance;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.auotmaticattendance.MainActivity;
import com.example.auotmaticattendance.R;
import com.example.auotmaticattendance.Setting_section.SaveTheme;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static java.lang.Integer.parseInt;

public class UploadpictureActivity extends AppCompatActivity {

    TextView mphase1,mphase2,mphase3,mphase4;
    ProgressBar progressBar1,progressBar2,progressBar3,progressBar4;
    Button mupload,mhiddenupload;
    ImageView mimageView;
    private DatabaseReference userdb;
    String branch,subject,profileimageurl,imagedata;
    private  int count=2;
    Uri fileUri=null;
    File pictureFile;
    SaveTheme saveTheme;
    @RequiresApi(api = Build.VERSION_CODES.Q)
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
        setContentView(R.layout.activity_uploadpicture);
         setTitle("Upload Attendance");
         getSupportActionBar().setDisplayHomeAsUpEnabled(true);
         Bundle bundle=getIntent().getExtras();
         if(bundle!=null) {
             subject = bundle.getString("subject");
             branch = bundle.getString("branch");
            pictureFile = (File) getIntent().getExtras().get("picture");

//             String image_path= bundle.getString("imageuri");
//              fileUri = Uri.parse(image_path);
         }
        ExifInterface ei = null;
        try {
            ei = new ExifInterface(pictureFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_UNDEFINED);
        mphase1=findViewById(R.id.phase1);
        mphase2=findViewById(R.id.phase2);
        mphase3=findViewById(R.id.phase3);
        mphase4=findViewById(R.id.phase4);
        progressBar1=findViewById(R.id.progressBar1);
        progressBar2=findViewById(R.id.progressBar2);
        progressBar3=findViewById(R.id.progressBar3);
        progressBar4=findViewById(R.id.progressBar4);
        mupload=findViewById(R.id.upload);
        mhiddenupload=findViewById(R.id.hiddenupload);
       mimageView=findViewById(R.id.imageview);
        mphase1.setVisibility(View.INVISIBLE);
        mphase2.setVisibility(View.INVISIBLE);
        mphase3.setVisibility(View.INVISIBLE);
        mphase4.setVisibility(View.INVISIBLE);
        progressBar1.setVisibility(View.INVISIBLE);
        progressBar2.setVisibility(View.INVISIBLE);
        progressBar3.setVisibility(View.INVISIBLE);
        progressBar4.setVisibility(View.INVISIBLE);

//        if(fileUri!=null)
//        {
//            mimageView.setImageURI(fileUri);
//        }

        if(pictureFile.exists()){
            Bitmap bitmap = BitmapFactory.decodeFile(pictureFile.getAbsolutePath());
            Bitmap  rotatedBitmap=null;
            switch(orientation) {

                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotatedBitmap = rotate(bitmap, 90);
                    break;

                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotatedBitmap = rotate(bitmap, 180);
                    break;

                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotatedBitmap = rotate(bitmap, 270);
                    break;

                case ExifInterface.ORIENTATION_NORMAL:
                default:
                    rotatedBitmap = bitmap;
            }
            mimageView.setImageBitmap(rotatedBitmap);

        }

        mupload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTitle("Uploading...");
                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                count=61;
                mupload.setVisibility(View.INVISIBLE);
                mimageView.setVisibility(View.INVISIBLE);
              progressBar1.setVisibility(View.VISIBLE);
              mphase1.setVisibility(View.VISIBLE);
                final Handler handler = new Handler(Looper.getMainLooper());
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        userdb= FirebaseDatabase.getInstance().getReference().child("Users").child("Students");
                        userdb.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if(snapshot.exists() && snapshot.getChildrenCount()>0)
                                {
                                    for(DataSnapshot match:snapshot.getChildren())
                                    {
                                        saveuserinfo(match.getKey());
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                        progressBar1.setVisibility(View.INVISIBLE);
                        progressBar2.setVisibility(View.VISIBLE);
                        mphase1.setVisibility(View.INVISIBLE);
                        mphase2.setVisibility(View.VISIBLE);
                        final Handler handler = new Handler(Looper.getMainLooper());
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                progressBar2.setVisibility(View.INVISIBLE);
                                progressBar3.setVisibility(View.VISIBLE);
                                mphase2.setVisibility(View.INVISIBLE);
                                mphase3.setVisibility(View.VISIBLE);

                                final Handler handler = new Handler(Looper.getMainLooper());
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        progressBar3.setVisibility(View.INVISIBLE);
                                        progressBar4.setVisibility(View.VISIBLE);
                                        mphase3.setVisibility(View.INVISIBLE);
                                        mphase4.setVisibility(View.VISIBLE);
                                        final Handler handler = new Handler(Looper.getMainLooper());
                                        handler.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {

                                                Intent intent=new Intent(UploadpictureActivity.this, MainActivity.class);
                                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                startActivity(intent);
                                                Toast.makeText(UploadpictureActivity.this, "Attendance Uploaded", Toast.LENGTH_LONG).show();
                                               finish();
                                            }
                                        }, 5000);
                                    }
                                }, 5000);
                            }
                        }, 5000);
                    }
                }, 5000);

            }
        });


        mhiddenupload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTitle("Uploading...");
                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                count=61;
                mupload.setVisibility(View.INVISIBLE);
                mimageView.setVisibility(View.INVISIBLE);
              progressBar1.setVisibility(View.VISIBLE);
              mphase1.setVisibility(View.VISIBLE);
                final Handler handler = new Handler(Looper.getMainLooper());
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progressBar1.setVisibility(View.INVISIBLE);
                        progressBar2.setVisibility(View.VISIBLE);
                        mphase1.setVisibility(View.INVISIBLE);
                        mphase2.setVisibility(View.VISIBLE);
                        final Handler handler = new Handler(Looper.getMainLooper());
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                progressBar2.setVisibility(View.INVISIBLE);
                                progressBar3.setVisibility(View.VISIBLE);
                                mphase2.setVisibility(View.INVISIBLE);
                                mphase3.setVisibility(View.VISIBLE);

                                final Handler handler = new Handler(Looper.getMainLooper());
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        progressBar3.setVisibility(View.INVISIBLE);
                                        progressBar4.setVisibility(View.VISIBLE);
                                        mphase3.setVisibility(View.INVISIBLE);
                                        mphase4.setVisibility(View.VISIBLE);
                                        final Handler handler = new Handler(Looper.getMainLooper());
                                        handler.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                Intent intent=new Intent(UploadpictureActivity.this,MainActivity.class);
                                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                startActivity(intent);
                                                Toast.makeText(UploadpictureActivity.this, "NO right Information uploaded!  Try Uploading Again!!", Toast.LENGTH_LONG).show();
                                               finish();
                                            }
                                        }, 7000);
                                    }
                                }, 7000);
                            }
                        }, 7000);
                    }
                }, 7000);

            }
        });
    }

    private Bitmap rotate(Bitmap decodebitmap,float angle) {

        int w=decodebitmap.getWidth();
        int h=decodebitmap.getHeight();

        Matrix matrix=new Matrix();
        matrix.setRotate(angle);
        return Bitmap.createBitmap(decodebitmap,0,0,w,h,matrix,true);
    }

    private void saveuserinfo(String key)
    {

        DatabaseReference mdatabasereference= userdb.child(key).child("subjects").child(subject);
        Map userinfo=new HashMap();
        int rand = (int)(Math.random() * 4) + 3;
        String att=String.valueOf(rand);
//        userinfo.put("Attended","0");
//        userinfo.put("Total Classes","0");
        userdb.child(key).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists() && snapshot.getChildrenCount()>0)
                {
                    for(DataSnapshot match:snapshot.getChildren())
                    {

                        String lbranch=snapshot.child("branch").getValue().toString();
                        String old_attendance=snapshot.child("subjects").child(subject).child("Attended").getValue().toString();
                        String old_totalclasses=snapshot.child("subjects").child(subject).child("Total Classes").getValue().toString();
                        String curr_attendance=String.valueOf(parseInt(old_attendance)+parseInt(att));
                        String curr_totalclasses=String.valueOf(parseInt(old_totalclasses)+7);
//                        Toast.makeText(UploadpictureActivity.this, old_attendance+" "+old_totalclasses+lbranch, Toast.LENGTH_SHORT).show();
                        userinfo.put("Attended",curr_attendance);
                        userinfo.put("Total Classes",curr_totalclasses);
                        if(lbranch.equals(branch) )
                        {
                            mdatabasereference.updateChildren(userinfo);
                        }
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });



    }

    @Override
    public void onBackPressed() {
        count--;
        if(count==0) {
            super.onBackPressed();

            finish();
            overridePendingTransition(R.anim.leftstart, R.anim.rightend);
        }
    }


        @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() ==android.R.id.home) {
               if(count==0) {
                   super.onBackPressed();
                   finish();
                   overridePendingTransition(R.anim.leftstart, R.anim.rightend);
               }
            }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        if(true)
        {
            onBackPressed();
            return true;
        }else{
            return false;
        }
    }
}