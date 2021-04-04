package ru.radiotec.site.entity;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="booksec")
public class BookSec {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="sec_id")
    private int id;

    @NotEmpty
    @Column(name="sec_name")
    private String name;

    @Column(name="redaktor")
    private String redaktor;

    @Column(name="sec_keyw")
    private String keywords;

    @NotEmpty
    @Column(name="sec_name_eng")
    private String nameEng;

    @Column(name="redaktor_eng")
    private String redaktorEng;

    @Column(name="sec_keyw_eng")
    private String keywordsEng;


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "bookSec")
    @OrderBy("sort ASC")
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

    public String getNameEng() {
        return nameEng;
    }

    public void setNameEng(String nameEng) {
        this.nameEng = nameEng;
    }

    public String getRedaktorEng() {
        return redaktorEng;
    }

    public void setRedaktorEng(String redaktorEng) {
        this.redaktorEng = redaktorEng;
    }

    public String getKeywordsEng() {
        return keywordsEng;
    }

    public void setKeywordsEng(String keywordsEng) {
        this.keywordsEng = keywordsEng;
    }
}
