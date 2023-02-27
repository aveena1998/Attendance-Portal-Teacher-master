package com.example.auotmaticattendance.Attendance_section.Upload_Attendance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;

import com.example.auotmaticattendance.Profile_section.EditProfileActivity;
import com.example.auotmaticattendance.R;
import com.example.auotmaticattendance.Setting_section.SaveTheme;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

public class selectbranchsubActivity extends AppCompatActivity {


    Spinner mbranch,msubject;
    String branch,subject;
    File file;
    private Button mtakeliveAttendance,mgalleryAttendance;
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
        setContentView(R.layout.activity_selectbranchsub);
        mbranch=findViewById(R.id.branch);
        msubject=findViewById(R.id.subject);
        mtakeliveAttendance=findViewById(R.id.takeliveAttendance);
        mgalleryAttendance=findViewById(R.id.takegalleryAttendance);


        mbranch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i
                    , long id) {
                String selectedBranch = (String) mbranch.getItemAtPosition(i);
                branch=selectedBranch;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        msubject.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {
                String selectedSubject = (String) msubject.getItemAtPosition(i);
                subject=selectedSubject;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mgalleryAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                CropImage.activity().setAspectRatio(4,6).start(selectbranchsubActivity.this);
                CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .start(selectbranchsubActivity.this);
            }
        });




        mtakeliveAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(selectbranchsubActivity.this, UploadAttendanceActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("branch",branch);
                bundle.putString("subject",subject);
                intent.putExtras(bundle);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                overridePendingTransition(R.anim.rightstart,R.anim.leftend);
            }
        });

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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Intent intent=new Intent(selectbranchsubActivity.this, UploadpictureActivity.class);
                Uri imageuri = result.getUri();
                File file = new File(imageuri.getPath());
                Bundle bundle = new Bundle();
                bundle.putString("branch",branch);
                bundle.putString("subject",subject);
                intent.putExtra("picture",file);
                intent.putExtras(bundle);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                overridePendingTransition(R.anim.rightstart,R.anim.leftend);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

}