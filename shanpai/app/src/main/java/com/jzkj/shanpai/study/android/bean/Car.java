package com.jzkj.shanpai.study.android.bean;

import android.util.Log;

public class Car {
    private boolean waxOn = false;

    public synchronized void waxed(){
        waxOn = true;
        notifyAll();
    }

    public synchronized void buffed(){
        waxOn = false;
        notifyAll();
    }

    public synchronized void waittingWaxed(){
        while (waxOn == false) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
                Log.e("tag,","interrput:waittingWaxed");
            }
        }
    }

    public synchronized void waittingBuffed(){
        while (waxOn == true) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
                Log.e("tag,","interrput:waittingBuffed");
            }
        }
    }


}
