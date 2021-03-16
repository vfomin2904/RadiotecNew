package ru.radiotec.site.entity;


import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name="books")
public class Books {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @NotEmpty
    @Column(name="bookname")
    private String name;

    @Column(name="bookauthor")
    private String author;

    @Column(name="booksection")
    private int section;

    @ManyToOne
    @JoinColumn(name="booksection", referencedColumnName = "sec_id", insertable = false, updatable = false)
    private BookSec bookSec;

    @Column(name="pages")
    private int pages;

    @Column(name="fpict")
    private int fpict;

    @Column(name="description")
    private String description;

    @Column(name="bookyear")
    private String year;

    @Column(name="publisher")
    private int publisher;

    @ManyToOne
    @JoinColumn(name="publisher", referencedColumnName = "id", insertable = false, updatable = false)
    private Publisher publish;

    @Column(name="isbn")
    private String isbn;

    @Column(name="price")
    private Integer price;

    @Column(name="editionnumber")
    private String editionNumber;

    @Column(name="bookcover")
    private int bookcover;

    @ManyToOne
    @JoinColumn(name="bookcover", referencedColumnName = "id", insertable = false, updatable = false)
    private BookCover cover;

    @Column(name="booksize")
    private int booksize;

    @ManyToOne
    @JoinColumn(name="booksize", referencedColumnName = "id", insertable = false, updatable = false)
    private BookSize size;

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

    public int getSection() {
        return section;
    }

    public void setSection(int section) {
        this.section = section;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getFpict() {
        return fpict;
    }

    public void setFpict(int fpict) {
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

    public String getEditionNumber() {
        return editionNumber;
    }

    public void setEditionNumber(String editionNumber) {
        this.editionNumber = editionNumber;
    }

    public int getBookover() {
        return bookcover;
    }

    public void setBookcover(int bookcover) {
        this.bookcover = bookcover;
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

    public Publisher getPublish() {
        return publish;
    }

    public void setPublish(Publisher publish) {
        this.publish = publish;
    }

    public int getBookcover() {
        return bookcover;
    }

    public BookCover getCover() {
        return cover;
    }

    public void setCover(BookCover cover) {
        this.cover = cover;
    }

    public BookSize getSize() {
        return size;
    }

    public void setSize(BookSize size) {
        this.size = size;
    }
}
