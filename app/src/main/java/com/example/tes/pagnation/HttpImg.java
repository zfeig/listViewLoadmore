package com.example.tes.pagnation;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.widget.ImageView;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by no1 on 2016/6/27.
 * @通过网络下载图片并设置图片控件的图像
 */
public class HttpImg extends Thread {
    private String url;
    private ImageView imageView;
    private Handler handler = new Handler();
    private HttpURLConnection conn;

    HttpImg(String url,ImageView imageView){
        this.url = url;
        this.imageView = imageView;
    }
    @Override
    public void run() {
        try {
            URL address = new URL(url);
            conn = (HttpURLConnection) address.openConnection();
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            final Bitmap bitmap = BitmapFactory.decodeStream(conn.getInputStream());

            handler.post(new Runnable() {
                @Override
                public void run() {
                        imageView.setImageBitmap(bitmap);
                }
            });
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(conn!=null){
                conn.disconnect();
            }
        }
    }
}

