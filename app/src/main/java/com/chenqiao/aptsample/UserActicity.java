package com.chenqiao.aptsample;

import android.os.Bundle;
import android.os.PersistableBundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.chenqiao.annotations.Builder;
import com.chenqiao.annotations.Optional;
import com.chenqiao.annotations.Required;


@Builder
public class UserActicity extends AppCompatActivity {

    @Required
    String name;

    @Required
    int age;

    @Optional
    String title;

    @Optional
    String company;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
    }
}
