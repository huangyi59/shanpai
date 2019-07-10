package com.jzkj.shanpai.study;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.LruCache;

import com.jzkj.shanpai.R;

import java.io.File;

import okhttp3.internal.cache.DiskLruCache;

public class CacheActivity extends AppCompatActivity {

    private static final long DISK_CACHE_SIZE = 1024 * 1024 * 50;
    private LruCache<String, Bitmap> mCache;
    private DiskLruCache mDiskLruCache;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cache);
    }

    private void test() {
        Bitmap bitmap = BitmapFactory.decodeFile("");
    }

    /**
     * LruCache的核心思想 优先淘汰近期最少使用的缓存对象
     */
    private void test1() {
        int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        int cacheSize = maxMemory / 8;
        mCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                mCache.put(key,value);
                return value.getRowBytes() * value.getHeight() / 1024;
            }

            @Override
            protected void entryRemoved(boolean evicted, String key, Bitmap oldValue, Bitmap newValue) {
                super.entryRemoved(evicted, key, oldValue, newValue);
                mCache.remove(key);
            }
        };
    }

    /**
     * DiskLruCache磁盘缓存
     */
    private void test2(){
        File file = new File("bitmap");
        if(!file.exists()){
            file.mkdirs();
        }
        //mDiskLruCache = DiskLruCache.o
    }

}
