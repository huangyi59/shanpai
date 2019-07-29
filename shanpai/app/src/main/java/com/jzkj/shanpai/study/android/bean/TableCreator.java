package com.jzkj.shanpai.study.android.bean;

import android.util.Log;

import com.jzkj.shanpai.study.annotation.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class TableCreator {

    private static final String TAG = TableCreator.class.getName();

    public static void main(String[] args) throws Exception {
        for (String className : args) {
            Class<?> cl = Class.forName(className);
            DBTable dbTable = cl.getAnnotation(DBTable.class);
            if (dbTable == null) {
                Log.e(TAG, "No DBTable annotations in class " + className);
            }

            String tabName = dbTable.name();
            if (tabName.length() < 1)
                tabName = cl.getName().toUpperCase();

            List<String> columnDefs = new ArrayList<>();
            for (Field field : cl.getDeclaredFields()) {
                String columnName = null;
                Annotation[] anns = field.getDeclaredAnnotations();
                if (anns.length < 1) return;

                if (anns[0] instanceof SQLInteger) {
                    SQLInteger sInt = (SQLInteger) anns[0];
                    if (sInt.name().length() < 1) {
                        columnName = field.getName().toUpperCase();
                    } else {
                        columnName = sInt.name();
                    }

                    columnDefs.add(columnName + " int ");
                    getConstraints(sInt.constraints());
                }

                if (anns[0] instanceof SQLString) {
                    SQLString sqlString = (SQLString) anns[0];
                    if (sqlString.name().length() < 1) {
                        columnName = field.getName().toUpperCase();
                    } else {
                        columnName = sqlString.name();
                    }
                    columnDefs.add(columnName + " varchar(" + sqlString.value() + ")");
                    getConstraints(sqlString.contraints());
                }
            }

            StringBuilder builder = new StringBuilder("create table " + tabName + "(");
            for (String columnDef : columnDefs)
                builder.append("\n " + columnDef + "，");

            //SQL 结束符分号不能丢
            String tableCreate = builder.substring(0, builder.length() - 1) + ");";

        }
    }

    private static String getConstraints(Constraints con) {
        String constraints = "";
        if (!con.allowNull())
            constraints += " not null ";

        if (con.primaryKey())
            constraints += " PRIMARY KEY ";

        if (con.unique()) {
            constraints += " UNIQUE ";
        }
        return constraints;
    }

}
