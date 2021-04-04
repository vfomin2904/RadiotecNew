package ru.radiotec.site.entity;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name="newsbase")
public class News {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id_news")
    private int id;

    @Column(name="date_news")
    private String date;

    @Column(name="shortnews")
    private String name;

    @NotEmpty
    @Column(name="longnews")
    private String longNews;

    @Column(name="shortnews_eng")
    private String nameEng;

    @Column(name="longnews_eng")
    private String longNewsEng;

    @Column(name="type")
    private int type;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLongNews() {
        return longNews;
    }

    public void setLongNews(String longNews) {
        this.longNews = longNews;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getNameEng() {
        return nameEng;
    }

    public void setNameEng(String nameEng) {
        this.nameEng = nameEng;
    }

    public String getLongNewsEng() {
        return longNewsEng;
    }

    public void setLongNewsEng(String longNewsEng) {
        this.longNewsEng = longNewsEng;
    }
}
