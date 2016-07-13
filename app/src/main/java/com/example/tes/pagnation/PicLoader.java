package com.example.tes.pagnation;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.LruCache;
import android.widget.ImageView;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by no1 on 2016/7/11.
 * @缓存适配器图片
 */
public class PicLoader {
    private LruCache<String,Bitmap> mCache;
    public PicLoader() {

        int cacheSize = (int) Runtime.getRuntime().maxMemory()/4;
        mCache = new LruCache<String,Bitmap>(cacheSize){
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                return bitmap.getByteCount();
            }
        };
    }

    public void imgLoader(String url,ImageView imageView){
    //先从缓存拿，没有则线程下载
       Bitmap bitmap = getBitmapFromCache(url);
        if(bitmap != null){
            System.out.println("直接从缓存取图片");
            imageView.setImageBitmap(bitmap);
        }else{
            System.out.println("即将进行网络图片下载："+url);
            new getImgFromNetWork(imageView,url).execute(url);
        }
    }


    /**
     * @添加到缓存
     * @param key
     * @param bitmap
     */
    public void addBitmapToCache(String key,Bitmap bitmap){
        if(getBitmapFromCache(key) == null && bitmap!=null){
            mCache.put(key,bitmap);
        }
    }

    /**
     * @获取缓存
     * @param key
     * @return
     */
    public Bitmap getBitmapFromCache(String key){
        return  mCache.get(key);
    }


    /**
     * @异步请求图片
     */
    class getImgFromNetWork extends AsyncTask<String,Void,Bitmap>{
        private Bitmap bitmap =null;
        private ImageView imageView;
        private String url;
        private HttpURLConnection conn;

        public getImgFromNetWork(ImageView imageView,String url) {
            this.imageView = imageView;
            this.url = url;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            System.out.println("缓存找不到，即将进行网络请求");
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            url = params[0];
            return getImgByURL(url);
        }

        public  Bitmap getImgByURL(String url){
            URL address = null;
            try {
                address = new URL(url);
                conn = (HttpURLConnection) address.openConnection();
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                bitmap = BitmapFactory.decodeStream(conn.getInputStream());
                if(bitmap != null && getBitmapFromCache(url) == null){
                    System.out.println("即将设置缓存："+url);
                    addBitmapToCache(url,bitmap);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                if(conn!=null){
                    conn.disconnect();
                }
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if(imageView.getTag()!=null && imageView.getTag().equals(url)){
                imageView.setImageBitmap(bitmap);
            }
        }
    }

}
