package com.example.tes.pagnation;

import android.os.Handler;
import android.os.Message;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by no1 on 2016/7/9.
 * @基本工具类
 */
public class Util {

    /**
     * @发送消息到消息队列中
     * @param hander
     * @param msg
     * @param data
     */
    public static void sendMsg(Handler hander,Message msg,List data){
        msg.what =1;
        msg.obj = data;
        hander.sendMessage(msg);
    }
    /**
     * @get请求
     * @param url
     * @return
     */
    public static String httpGet(String url){
        String data = null;
        System.out.println("请求地址："+url);
        HttpURLConnection conn = null;
        try{
            URL address = new URL(url);
            conn = (HttpURLConnection) address.openConnection();
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            StringBuilder sb = new StringBuilder();
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String msg = "";
            while((msg = br.readLine())!=null){
                sb.append(msg);
            }
            data = sb.toString();
            System.out.println("获取结果："+data);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(conn != null){
                conn.disconnect();
            }
        }
        return data;
    }

    /**
     * @get请求
     * @param url
     * @return
     */
    public static String httpGet(String url,int page){
        String data = null;
        url+="?page="+page;
        System.out.println("请求地址："+url);
        return httpGet(url);
    }

    /**
     * @解析json存储为集合
     * @param data
     * @return
     */
    public static List<NewsEntity> parseJson2List(String data){
        List<NewsEntity> result = new ArrayList<NewsEntity>();
        try{
            JSONObject object = new JSONObject(data);
            int status  = object.getInt("status");
            if(status == 200){
                JSONArray item = object.getJSONArray("result");
                for(int i=0;i<item.length();i++){
                    JSONObject tmpObj = item.getJSONObject(i);
                    String id =tmpObj.getString("id");
                    String title = tmpObj.getString("title");
                    String thumb = tmpObj.getString("thumb");
                    String img = tmpObj.getString("img");
                    String content = tmpObj.getString("content");
                    int age = tmpObj.getInt("age");
                    String address = tmpObj.getString("address");
                    JSONObject study = tmpObj.getJSONObject("study");
                    String no = study.getString("no");
                    String teacher = study.getString("teacher");
                    NewsEntity news = new NewsEntity(id,title,content,thumb);
                    result.add(news);
                }

            }else{
                result = null;
                System.out.println("获取失败或无数据！");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }


    /**
     * @解析json存储为实体
     * @param data
     * @return
     */
    public static NewsDetailEntity parseJson2Entity(String data){
      NewsDetailEntity  result = null;
        try{
            JSONObject object = new JSONObject(data);
            int status  = object.getInt("status");
            if(status == 200){
                JSONObject item = object.getJSONObject("result");

                    String id =item.getString("id");
                    String title = item.getString("title");
                    String thumb = item.getString("thumb");
                    String img = item.getString("img");
                    String article = item.getString("article");
                    String content = item.getString("content");
                    int age = item.getInt("age");
                    String address = item.getString("address");
                    JSONObject study = item.getJSONObject("study");
                    String no = study.getString("no");
                    String teacher = study.getString("teacher");
                result = new NewsDetailEntity(id,title,content,age,thumb,img,address,teacher,article);
            }else{
                result = null;
                System.out.println("获取失败或无数据！");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }






}
