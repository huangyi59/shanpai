package com.jzkj.shanpai.study;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jzkj.shanpai.R;
import com.jzkj.shanpai.study.annotation.UseCase;

import java.lang.reflect.Method;
import java.util.List;

public class AnnotationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_annotation);
    }

    @UseCase(id = 10,description = "test")
    private void test(){}

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void test1(List<Integer> list, Class<?> clz){
        for(Method m:clz.getDeclaredMethods()){
            UseCase useCase = m.getAnnotation(UseCase.class);

            if(useCase!=null){
                list.remove(new Integer(useCase.id()));
            }
        }
    }

}
