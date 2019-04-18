package com.jzkj.shanpai.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.jzkj.shanpai.R;
import com.jzkj.shanpai.util.CameraUtil;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import sp.base.utils.LogUtil;

public class CameraActivity extends Activity implements SurfaceHolder.Callback, Camera.PreviewCallback {

    private static final String TAG = CameraActivity.class.getSimpleName();


    @BindView(R.id.sur_view)
    SurfaceView mSurfaceView;
    @BindView(R.id.focus_index)
    ImageView ivFocus;

    private Camera.Parameters parameters;
    private SurfaceHolder mSurfaceHolder;
    private Bitmap bitmapRotated;
    private int viewWidth,viewHeight;

    private Camera mCamera;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        ButterKnife.bind(this);
        getSurfaceHolder();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if(mSurfaceView!=null){
            viewWidth = mSurfaceView.getWidth();
            viewHeight = mSurfaceView.getHeight();
        }
    }

    private void getSurfaceHolder(){
        mSurfaceHolder = mSurfaceView.getHolder();
        // mSurfaceView 不需要自己的缓冲区
        mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        mSurfaceHolder.addCallback(this);
    }


    @Override
    public void onPreviewFrame(byte[] data, Camera camera) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        initCamera();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        cameraRelease();
    }

    /**
     * 打开相机并设置相关参数
     */
    private void initCamera(){
        if(mCamera == null){
            mCamera = Camera.open();
            //摄像头进行旋转90度
            mCamera.setDisplayOrientation(90);
        }
        if(mCamera!=null){
            try{
                Camera.Parameters parameters = mCamera.getParameters();
                //设置预览照片的大小
                parameters.setPreviewFpsRange(viewWidth,viewHeight);
                //设置图片的格式
                parameters.setPictureFormat(ImageFormat.JPEG);
                //设置图片的质量
                parameters.set("jpeg-puality",90);
                //设置照片的大小
                parameters.setPictureSize(viewWidth,viewHeight);
                //通过SurfaceView显示预览
                mCamera.setPreviewDisplay(mSurfaceHolder);
                //
                mCamera.setPreviewCallback(this);
                //开始预览
                mCamera.startPreview();
            }catch (IOException e){
                LogUtil.e(TAG,e.getMessage());
            }
        }

        /**
         * 获取图片
         */
        final Camera.PictureCallback pictureCallback = new Camera.PictureCallback() {
            @Override
            public void onPictureTaken(byte[] data, Camera camera) {
                final Bitmap resource = BitmapFactory.decodeByteArray(data, 0, data.length);
                if (resource == null) {
                    Toast.makeText(CameraActivity.this, "拍照失败", Toast.LENGTH_SHORT).show();
                }
                final Matrix matrix = new Matrix();
                matrix.setRotate(90);
                final Bitmap bitmap = Bitmap.createBitmap(resource, 0, 0, resource.getWidth(), resource.getHeight(), matrix, true);
            }
        };

        /**
         *自动对焦 对焦成功后 就进行拍照
         */
        Camera.AutoFocusCallback autoFocusCallback = new Camera.AutoFocusCallback() {
            @Override
            public void onAutoFocus(boolean success, Camera camera) {
                if (success) {//对焦成功

                    camera.takePicture(new Camera.ShutterCallback() {//按下快门
                        @Override
                        public void onShutter() {
                            //按下快门瞬间的操作
                        }
                    }, new Camera.PictureCallback() {
                        @Override
                        public void onPictureTaken(byte[] data, Camera camera) {//是否保存原始图片的信息

                        }
                    }, pictureCallback);
                }
            }
        };



    }

    /**
     * 释放资源
     */
    private void cameraRelease(){
        if(mCamera != null){
            mCamera.setPreviewCallback(null);
            mCamera.stopPreview();
            mCamera.release();
        }
    }

}
