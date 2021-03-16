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

    @NotEmpty
    @Column(name="date_news")
    private String date;

    @NotEmpty
    @Column(name="shortnews")
    private String name;

    @NotEmpty
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
}
