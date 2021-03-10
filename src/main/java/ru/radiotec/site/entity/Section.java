package ru.radiotec.site.entity;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="razdel_numbers")
public class Section {

    @Id

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="razd_id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "number", referencedColumnName = "num_id", insertable = false, updatable = false)
    private Number number;

    @NotEmpty
    @Column(name="razd_name")
    private String name;

    @Column(name="number")
    private int numberId;

    @Column(name="post")
    private int post;

    @Column(name="r_sort")
    private int sort;

    @NotEmpty
    @Column(name="razd_name_eng")
    private String nameEng;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "section")
    @OrderBy("id ASC")
    private Set<Article> articles = new HashSet<>();

    public Set<Article> getArticles() {
        return this.articles;
    }
    public void setArticles(Set<Article> articles) {
        this.articles = articles;
    }

    public void addArticle(Article article) {
        article.setSection(this);
        this.articles.add(article);
    }

    public Number getNumber() {
        return this.number;
    }

    public void setNumber(Number number) {
        this.number = number;
    }

    public int getNumberId() {
        return numberId;
    }

    public void setNumberId(int numberId) {
        this.numberId = numberId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPost() {
        return post;
    }

    public void setPost(int post) {
        this.post = post;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getNameEng() {
        return nameEng;
    }

    public void setNameEng(String nameEng) {
        this.nameEng = nameEng;
    }
}
