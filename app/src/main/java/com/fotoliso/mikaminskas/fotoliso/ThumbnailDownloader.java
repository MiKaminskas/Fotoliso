package com.fotoliso.mikaminskas.fotoliso;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;

import java.io.IOException;
import java.util.BitSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class ThumbnailDownloader<T> extends HandlerThread {
    private static final String TAG = "ThumbnailDownloader";
    private static final int MESSAGE_DOWNLOAD = 0;
    private Handler requestHandler;
    private ConcurrentMap<T,String> requestMap = new ConcurrentHashMap<>();
    private Handler responseHandler;
    private ThumbnailDownloadListener<T> thumbnailDownloadListener;

    public interface  ThumbnailDownloadListener<T>{
        void onThumbnailDownloaded(T target, Bitmap thumbnail);
    }
    public void setThumbnailDownloadListener(ThumbnailDownloadListener<T> listener){
        thumbnailDownloadListener = listener;
    }
    public ThumbnailDownloader(Handler handler) {
        super(TAG);
        this.responseHandler = handler;
    }

    @Override
    protected void onLooperPrepared() {
        requestHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == MESSAGE_DOWNLOAD){
                    T target = (T) msg.obj;
                    Log.d(TAG," Request for URL" + requestMap.get(target));
                    handeleRequest(target);
                }
            }
        };
    }

    public void queueThumbnail(T target, String url){
        Log.i(TAG,"Got a URL: " + url);
        if (url==null){
            requestMap.remove(target);
        }
        else {
            requestMap.put(target,url);
            requestHandler.obtainMessage(MESSAGE_DOWNLOAD,target).sendToTarget();
        }
    }
    private void handeleRequest(final T target){


        try {
            final String url = requestMap.get(target);
            if (url == null){
                return;
            }
            byte[] bitmapBytes = new byte[0];
            bitmapBytes = new FotolisoFetchr().getUrlBytes(url);
            final Bitmap bitmap = BitmapFactory.decodeByteArray(bitmapBytes, 0 , bitmapBytes.length);
            Log.d(TAG,"Bitmap created");

            responseHandler.post(new Runnable() {
                @Override
                public void run() {
                    Log.d(TAG,"achived1");
                    if (requestMap.get(target) != url){
                        return;
                    }
                    Log.d(TAG,"achived2");
                    requestMap.remove(target);
                    Log.d(TAG,"achived3");
                    thumbnailDownloadListener.onThumbnailDownloaded(target,bitmap);
                }
            });
        } catch (IOException e) {
           Log.e(TAG, " bitmap download error");
            e.printStackTrace();
        }
    }
    public void clearQueue(){
        responseHandler.removeMessages(MESSAGE_DOWNLOAD);
    }
}
