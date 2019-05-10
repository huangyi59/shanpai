package com.jzkj.shanpai.study.android.bean;

import java.io.Serializable;

/**
 * Serialzable和Parcelable可以完成对象的序列化过程
 * 静态成员变量属于类不属于对象
 */
public class UserS implements Serializable{
    public UserS(int userId, String userName, boolean isMale) {
        this.userId = userId;
        this.userName = userName;
        this.isMale = isMale;
    }

    /**
     * 序列化反序列化无非就是将对象存到某个地方，然后在读取出来
     * serialVersionUID是用来辅助序列化和反序列过程的 确认序列化的对象和反序列化的对象是不是同一个值
     * serialVersionUID是根据当前类的结构生成的hash值
     */

    private static final long serialVersionUID = 100000L;
    public int userId;
    public String userName;
    public boolean isMale;

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", isMale=" + isMale +
                '}';
    }
}
