package com.jzkj.shanpai.study.android.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import com.jzkj.shanpai.Book;
import com.jzkj.shanpai.IMyAidlInterface;

import java.util.List;

public class MyService extends Service {

    public List<Book> mBookList;

    public final IMyAidlInterface.Stub mBinder = new IMyAidlInterface.Stub() {

        @Override
        public List<Book> getBookList() throws RemoteException {
            synchronized (mBookList){
                return mBookList;
            }
        }

        @Override
        public void addBook(Book book) throws RemoteException {
            synchronized (mBookList){
                if(!mBookList.contains(book)){
                    mBookList.add(book);
                }
            }
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }
}
