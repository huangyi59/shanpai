package com.jzkj.shanpai.study.android.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class UserP implements Parcelable {

    private int userId;
    private String userName;

    public UserP(int userId, String userName) {
        this.userId = userId;
        this.userName = userName;
    }

    /**
     * 从序列化对象中创建原始对象
     */
    protected UserP(Parcel in) {
        userId = in.readInt();
        userName = in.readString();
    }

    public static final Creator<UserP> CREATOR = new Creator<UserP>() {
        /**
         * 从序列化后的对象中创建原始对象
         * @param in
         * @return
         */
        @Override
        public UserP createFromParcel(Parcel in) {
            return new UserP(in);
        }

        @Override
        public UserP[] newArray(int size) {
            return new UserP[size];
        }
    };

    /**
     * 返回当前对象的描述内容
     * @return
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * 将当前对象写入到序列化结构中
     * @param out
     * @param flags
     */
    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(userId);
        out.writeString(userName);
    }

}
