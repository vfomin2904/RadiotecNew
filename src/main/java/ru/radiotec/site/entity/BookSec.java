package ru.radiotec.site.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="booksec")
public class BookSec {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="sec_id")
    private int id;

    @Column(name="sec_name")
    private String name;

    @Column(name="redaktor")
    private String redaktor;

    @Column(name="sec_keyw")
    private String keywords;


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "bookSec")
    @OrderBy("id ASC")
    Set<Books> books = new HashSet();

    public BookSec(){
        
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRedaktor(String redaktor) {
        this.redaktor = redaktor;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getRedaktor() {
        return redaktor;
    }

    public String getKeywords() {
        return keywords;
    }

    public Set<Books> getBooks() {
        return books;
    }

    public void setBooks(Set<Books> books) {
        this.books = books;
    }
}
