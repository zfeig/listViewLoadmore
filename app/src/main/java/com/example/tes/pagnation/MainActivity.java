package com.example.tes.pagnation;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import android.widget.Toast;
import java.util.List;

/**
 * @项目入口活动地址
 */
public class MainActivity extends Activity implements AbsListView.OnScrollListener{
    private ListView listView;
    private Button loadmore;
    private View loadmoreview;
    private NewsAdapter adapter;

    private int visibleLastIndex = 0;   //最后的可视项索引
    private static String ADDR = "http://192.168.145.162:8000/api-page.php"; //api地址
    private  static  int page = 1;
    private ProgressDialog dialog;
    private  List dataStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //加载进度条
        dialog = new ProgressDialog(MainActivity.this);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("loading");

        //获取loadmore视图及控件，设置事件监听
        loadmoreview = getLayoutInflater().inflate(R.layout.loadmore,null);
        loadmore = (Button) loadmoreview.findViewById(R.id.loadmore);
        //设置点击事件
        loadmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
                loadmore.setText("正在加载");
                page += 1;
                new Thread(){
                    @Override
                    public void run() {
                        final List<NewsEntity> data = loadmoreData(page);//更新adapter数据
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                dialog.dismiss();
                                if(data == null){
                                    listView.removeFooterView(loadmoreview);
                                    Toast.makeText(MainActivity.this, "没有更多数据了!", Toast.LENGTH_LONG).show();
                                }else{
                                    for(int i= 0;i<data.size();i++){
                                        adapter.addNewsEntity((NewsEntity) data.get(i));
                                    }
                                    adapter.notifyDataSetChanged();
                                    loadmore.setText("加载更多");
                                }
                            }
                        }, 2000);
                    }
                }.start();

            }
        });

        //listview初始化
        listView = (ListView) findViewById(R.id.listView);
        listView.addFooterView(loadmoreview);
        initAdapter();
        listView.setOnScrollListener(this);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                NewsEntity news = (NewsEntity) parent.getAdapter().getItem(position);//获取适配器再通过位置获取实体对象
                Intent intent = new Intent(MainActivity.this,DetailActivity.class);
                intent.putExtra("id",news.getId());
                startActivity(intent);
                //Toast.makeText(MainActivity.this,"消息：title"+news.getTitle()+",id:"+news.getId(),Toast.LENGTH_LONG).show();
            }
        });
    }

    /**
     * @初始化handler设置数据
     */
     Handler handler = new Handler();

    /**
     * @初始化adpter
     */
    private void initAdapter() {
        new Thread(){
            @Override
            public void run() {
                super.run();
                String msg =  Util.httpGet(ADDR, 1);
                dataStore = Util.parseJson2List(msg);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        adapter = new NewsAdapter(dataStore,MainActivity.this,R.layout.list_item);
                        listView.setAdapter(adapter);
                    }
                });

            }
        }.start();
    }

    /**
     * @加载更多数据
     */
    private List<NewsEntity> loadmoreData(int page) {
        String msg =  Util.httpGet(ADDR, page);
        List<NewsEntity> data = Util.parseJson2List(msg);
        return  data;
    }


    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

        this.visibleLastIndex = firstVisibleItem+visibleItemCount-1;//可见屏幕底端索引， 可见屏幕最顶端索引+每屏可见数量
        System.out.println("每屏可见数量："+visibleItemCount);
        System.out.println("可见屏幕顶端索引："+firstVisibleItem);
        System.out.println("可见屏幕底端索引1："+this.visibleLastIndex);
        System.out.println("可见屏幕底端索引2："+totalItemCount);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        page = 1;//退出时初始化page并销毁活动
        finish();
    }
}
