package ru.radiotec.site.entity;


import javax.persistence.*;

@Entity
@Table(name="books")
public class Books {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="bookname")
    private String name;

    @Column(name="bookauthor")
    private String author;

    @Column(name="booksection")
    private String section;

    @ManyToOne
    @JoinColumn(name="booksection", referencedColumnName = "sec_id", insertable = false, updatable = false)
    private BookSec bookSec;

    @Column(name="pages")
    private String pages;

    @Column(name="fpict")
    private String fpict;

    @Column(name="description")
    private String description;

    @Column(name="bookyear")
    private String year;

    @Column(name="publisher")
    private int publisher;

    @Column(name="isbn")
    private String isbn;

    @Column(name="price")
    private Integer price;

    @Column(name="editionnumber")
    private String editionnumber;

    @Column(name="bookcover")
    private int bookover;

    @Column(name="booksize")
    private int booksize;

    @Column(name="publisheddate")
    private String publisheddate;

    @Column(name="bookudk")
    private String udk;

    @Column(name="bookbbk")
    private String bbk;

    @Column(name="cover_img")
    private String coverImg;

    @Column(name="booknew")
    private String bookNew;

    @Column(name="content")
    private String content;

    public Books(){

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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getPages() {
        return pages;
    }

    public void setPages(String pages) {
        this.pages = pages;
    }

    public String getFpict() {
        return fpict;
    }

    public void setFpict(String fpict) {
        this.fpict = fpict;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public int getPublisher() {
        return publisher;
    }

    public void setPublisher(int publisher) {
        this.publisher = publisher;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        if(price == null){
            price = 0;
        }
        this.price = price;
    }

    public String getEditionnumber() {
        return editionnumber;
    }

    public void setEditionnumber(String editionnumber) {
        this.editionnumber = editionnumber;
    }

    public int getBookover() {
        return bookover;
    }

    public void setBookover(int bookover) {
        this.bookover = bookover;
    }

    public int getBooksize() {
        return booksize;
    }

    public void setBooksize(int booksize) {
        this.booksize = booksize;
    }

    public String getPublisheddate() {
        return publisheddate;
    }

    public void setPublisheddate(String publisheddate) {
        this.publisheddate = publisheddate;
    }

    public String getUdk() {
        return udk;
    }

    public void setUdk(String udk) {
        this.udk = udk;
    }

    public String getBbk() {
        return bbk;
    }

    public void setBbk(String bbk) {
        this.bbk = bbk;
    }


    public String getCoverImg() {
        return coverImg;
    }

    public void setCoverImg(String coverImg) {
        this.coverImg = coverImg;
    }

    public String getBookNew() {
        return bookNew;
    }

    public void setBookNew(String bookNew) {
        this.bookNew = bookNew;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public BookSec getBookSec() {
        return bookSec;
    }

    public void setBookSec(BookSec bookSec) {
        this.bookSec = bookSec;
    }
}
