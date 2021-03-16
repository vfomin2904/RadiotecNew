package ru.radiotec.site.entity;


import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name="pages")
public class Page {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="page_id")
    private int id;

    @NotEmpty
    @Column(name="content")
    private String content;

    public Page() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
