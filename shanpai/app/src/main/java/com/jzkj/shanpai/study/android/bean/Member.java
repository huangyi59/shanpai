package com.jzkj.shanpai.study.android.bean;

import com.jzkj.shanpai.study.annotation.*;

@DBTable(name = "Member")
public class Member {
    @SQLString(30)
    String firstName;

    @SQLString(50)
    String lastName;

    @SQLInteger
    Integer age;

    @SQLString(value = 30,contraints = @Constraints(primaryKey = true))
    String handle;

    //方法区 和类相关和对象无关 只有一份存储空间
    static int memberConunt;

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Integer getAge() {
        return age;
    }

    public String getHandle() {
        return handle;
    }

    public static int getMemberConunt() {
        return memberConunt;
    }
}
