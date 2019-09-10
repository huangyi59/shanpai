package com.jzkj.shanpai.study.android.manager;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jzkj.shanpai.R;

/**
 * Glide缓存机制
 */
public class GlideManager {

    /**
     * Glide 使用默认的内存缓存和磁盘缓存
     */
    private void loadImage(Context context, ImageView img){
        Glide.with(context)
                .load("")
                .override(200,200)
                //缩略图
                .thumbnail(0.f)
                //占位图
                .placeholder(R.drawable.ic_launcher)
                //加载失败后显示的图片
                .error(R.drawable.ic_launcher)
                //跳过内存缓存
                .skipMemoryCache(true)
                //只缓存处理过后的图片 DiskCacheStrategy.NONE跳过磁盘缓存 内存缓存和磁盘缓存都是LRU
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(img);
    }

}
