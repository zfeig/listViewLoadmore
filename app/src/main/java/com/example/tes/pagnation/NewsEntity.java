package com.example.tes.pagnation;

/**
 * Created by no1 on 2016/7/8.
 * @新闻列表实体
 */
public class NewsEntity {
    private String title;    //标题
    private String content;  //内容
    private String thumb;
    private String id;
    public NewsEntity(String id,String title, String content, String thumb) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.thumb = thumb;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }
}
