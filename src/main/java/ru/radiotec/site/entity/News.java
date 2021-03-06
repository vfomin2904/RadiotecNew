package ru.radiotec.site.entity;

import javax.persistence.*;

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
    private String shortNews;

    @Column(name="longnews")
    private String longNews;

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

    public String getShortNews() {
        return shortNews;
    }

    public void setShortNews(String shortNews) {
        this.shortNews = shortNews;
    }

    public String getLongNews() {
        return longNews;
    }

    public void setLongNews(String longNews) {
        this.longNews = longNews;
    }
}
