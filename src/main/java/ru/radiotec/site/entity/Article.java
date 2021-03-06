package ru.radiotec.site.entity;

import javax.persistence.*;

@Entity
@Table(name="articles")
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="art_id")
    private int id;

    @Column(name="art_page")
    private String page;

    @Column(name="authors")
    private String authors;

    @Column(name="art_name")
    private String name;

    @Column(name="descript")
    private String descript;

    @Column(name="literature")
    private String literature;

    @Column(name="authors_eng")
    private String authorsEng;

    @Column(name="art_name_eng")
    private String nameEng;

    @Column(name="descript_eng")
    private String descriptEng;

    @Column(name="literature_eng")
    private String literatureEng;

    @Column(name="keyword")
    private String keywords;

    @Column(name="keyword_eng")
    private String keywordsEng;

    @Column(name="udk")
    private String udk;

    @Column(name="doi")
    private String doi;

    @Column(name="citata")
    private String citata;

    @Column(name="citata_eng")
    private String citataEng;

    @Column(name="rubr_vak")
    private String vak;

    @Column(name="article_text")
    private String articleText;

    @Column(name="art_pdf")
    private String file;

    @Column(name="razd_id")
    private int sectionId;

    @ManyToOne
    @JoinColumn(name = "razd_id", referencedColumnName = "razd_id", insertable = false, updatable = false)
    private Section section;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescript() {
        return descript;
    }

    public void setDescript(String descript) {
        this.descript = descript;
    }

    public String getLiterature() {
        return literature;
    }

    public void setLiterature(String literature) {
        this.literature = literature;
    }

    public String getAuthorsEng() {
        return authorsEng;
    }

    public void setAuthorsEng(String authorsEng) {
        this.authorsEng = authorsEng;
    }

    public String getNameEng() {
        return nameEng;
    }

    public void setNameEng(String nameEng) {
        this.nameEng = nameEng;
    }

    public String getDescriptEng() {
        return descriptEng;
    }

    public void setDescriptEng(String descriptEng) {
        this.descriptEng = descriptEng;
    }

    public String getLiteratureEng() {
        return literatureEng;
    }

    public void setLiteratureEng(String literatureEng) {
        this.literatureEng = literatureEng;
    }

    public String[] getKeywords() {
        return keywords.split(",");
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getKeywordsEng() {
        return keywordsEng;
    }

    public void setKeywordsEng(String keywordsEng) {
        this.keywordsEng = keywordsEng;
    }

    public String getUdk() {
        return udk;
    }

    public void setUdk(String udk) {
        this.udk = udk;
    }

    public String getDoi() {
        return doi;
    }

    public void setDoi(String doi) {
        this.doi = doi;
    }

    public String getCitata() {
        return citata;
    }

    public void setCitata(String citata) {
        this.citata = citata;
    }

    public String getCitataEng() {
        return citataEng;
    }

    public void setCitataEng(String citataEng) {
        this.citataEng = citataEng;
    }

    public String getVak() {
        return vak;
    }

    public void setVak(String vak) {
        this.vak = vak;
    }

    public String getArticleText() {
        return articleText;
    }

    public void setArticleText(String articleText) {
        this.articleText = articleText;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public int getSectionId() {
        return sectionId;
    }

    public void setSectionId(int sectionId) {
        this.sectionId = sectionId;
    }

    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
    }
}
