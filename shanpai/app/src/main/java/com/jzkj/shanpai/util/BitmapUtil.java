package com.jzkj.shanpai.util;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class BitmapUtil {

    public static Bitmap decodeSampledBitmapFromResource(Resources resources,int resId,int reqWidth,int reqHeight){
        final BitmapFactory.Options options = new BitmapFactory.Options();
        //inJustDecodeBounds 此参数为true时，BitmapFactory只会解析图片的原始宽高信息
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(resources,resId,options);
        options.inSampleSize = calculateInSampleSize(options,reqWidth,reqHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(resources, resId, options);
    }

    public static int calculateInSampleSize(BitmapFactory.Options options,int reqWidth,int reqHeight){
        if(reqWidth == 0 || reqHeight == 0)
            return 1;

        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if(height >reqHeight || width >reqWidth){
            final int halfHeight = height /2;
            final int halfWidth  = width /2;
            while((halfHeight/inSampleSize)>=reqHeight && (halfWidth/inSampleSize)>=reqWidth){
                inSampleSize *=2;
            }
        }
        return inSampleSize;
    }

}
