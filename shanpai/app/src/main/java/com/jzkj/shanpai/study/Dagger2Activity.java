package com.jzkj.shanpai.study;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.jzkj.shanpai.R;
import com.jzkj.shanpai.study.android.bean.Person;

import java.nio.file.Path;

import javax.inject.Inject;

public class Dagger2Activity extends AppCompatActivity {

    @Inject
    Person person;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dagger2);
        person.setName("test");
        person.setAge(0);
        Log.e("tag",person.toString());
    }

}
