package com.example.tes.pagnation;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;

/**
 * Created by no1 on 2016/7/8.
 */
public class NewsAdapter extends BaseAdapter {
    public List<NewsEntity> data;
    private static Context context;
    private static int resoureId;
    private PicLoader picLoader;
    public NewsAdapter(List<NewsEntity> data,Context context,int resoureId) {
        this.data = data;
        this.context = context;
        this.resoureId = resoureId;
        picLoader = new PicLoader();
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        NewsEntity news = data.get(position);
        if(convertView == null){
          convertView = LayoutInflater.from(context).inflate(resoureId,null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.title.setText(news.getTitle());
        viewHolder.content.setText(news.getContent());
        //防止图片错乱
        viewHolder.thumb.setImageResource(R.drawable.girl_micro);//设置默认背景图
        viewHolder.thumb.setTag(news.getThumb());//给图片设置记号
        //new HttpImg(news.getThumb(),viewHolder.thumb).start();//子进程设置头像
        picLoader.imgLoader(news.getThumb(),viewHolder.thumb);
        return convertView;
    }

    /**
     * @添加适配器数据
     * @param news
     */
    public void  addNewsEntity(NewsEntity news){
        data.add(news);
    }

    public class ViewHolder{
        private TextView title;
        private TextView content;
        private ImageView thumb;
        public ViewHolder(View contentView) {
            this.title = (TextView) contentView.findViewById(R.id.title);
            this.content = (TextView) contentView.findViewById(R.id.content);
            this.thumb = (ImageView) contentView.findViewById(R.id.thumb);
        }
    }
}
