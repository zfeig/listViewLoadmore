package com.example.tes.pagnation;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by no1 on 2016/7/12.
 * @通过id获取某个id的列表详情
 */
public class DetailActivity extends Activity {
    private String detailUrl = "http://192.168.145.162:8000/api-detail.php?id=";
    private ProgressDialog dailog;
    private ImageView avatar;
    private ImageView img;
    private TextView title;
    private TextView no;
    private TextView content;
    private TextView article;
    private  TextView address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail);
        dailog = new ProgressDialog(this);
        dailog.setMessage("loading");
        String id = getIntent().getStringExtra("id");
        initView();
        getEntityById(id);
    }

    private void initView() {
        avatar = (ImageView) findViewById(R.id.avatar);
        img = (ImageView) findViewById(R.id.img);
        title = (TextView) findViewById(R.id.title);
        no = (TextView) findViewById(R.id.no);
        content = (TextView) findViewById(R.id.content);
        article = (TextView) findViewById(R.id.article);
        address = (TextView) findViewById(R.id.address);
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
          switch (msg.what){
              case 1:
                  NewsDetailEntity detail = (NewsDetailEntity) msg.obj;
                  title.setText(detail.getTitle());
                  no.setText(detail.getId());
                  address.setText(detail.getAddress());
                  content.setText(detail.getContent());
                  article.setText(detail.getArticle());
                  dailog.dismiss();
                  new HttpImg(detail.getThumb(),avatar).start();
                  new HttpImg(detail.getImg(),img).start();
                  break;
          }
        }
    };

    private void getEntityById(String id){
       dailog.show();
        detailUrl+=id;

        //从网络获取数据
        new Thread(){
            @Override
            public void run() {
                String res = Util.httpGet(detailUrl);
                NewsDetailEntity detail = Util.parseJson2Entity(res);
                Message msg = new Message();
                msg.what =1;
                msg.obj = detail;
                handler.sendMessage(msg);
            }
        }.start();
    }
}
