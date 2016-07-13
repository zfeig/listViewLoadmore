package com.example.tes.pagnation;

/**
 * Created by no1 on 2016/7/12.
 * @内容详情实体
 */
public class NewsDetailEntity {
    private String id;
    private String title;
    private String content;
    private int age;
    private String img;
    private String thumb;
    private String address;
    private String teacher;
    private String article;

    public String getArticle() {
        return article;
    }

    public void setArticle(String article) {
        this.article = article;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public NewsDetailEntity(String id, String title, String content, int age,String thumb,String img,String address, String teacher,String article) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.age = age;
        this.thumb = thumb;
        this.img = img;
        this.address = address;
        this.teacher = teacher;
        this.article = article;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }
}
