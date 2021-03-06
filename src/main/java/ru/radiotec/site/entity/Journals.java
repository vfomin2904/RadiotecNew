package ru.radiotec.site.entity;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name="journals")
public class Journals {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="journ_id")
    private int id;

    @Column(name="menu_name")
    private String menu_name;

    @Column(name="type")
    private String type;

    @Column(name="redaktor")
    private String redaktor;

    @Column(name="descript")
    private String descript;

    @Column(name="redkol")
    private String redkol;

    @Column(name="num_year")
    private String num_year;

    @Column(name="komplekt")
    private String komplect;

    @Column(name="undercover")
    private String undercover;

    @Column(name="vak")
    private int vak;

    @Column(name="rospech")
    private int rospech;

    @Column(name="pochta")
    private String pochta;

    @Column(name="issn")
    private String issn;

    @Column(name="cover")
    private String cover;

    @Column(name="journ_name")
    private String name;

    @Column(name="keywords")
    private String keywords;


    @Column(name="jr_active")
    private int jr_active;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "journal")
    private Set<Number> numbers = new HashSet<>();

    public Journals() {

    }

    public Set<Number> getNumbers() {
        return this.numbers;
    }
    public void setNumbers(Set<Number> numbers) {
        this.numbers = numbers;
    }

    public void addNumbers(Number number) {
        number.setJournal(this);
        this.numbers.add(number);
    }

    public int getId() {
        return id;
    }

    public String getMenu_name() {
        return menu_name;
    }

    public String getType() {
        return type;
    }

    public String getRedaktor() {
        return redaktor;
    }

    public String getDescript() {
        return descript;
    }

    public String getRedkol() {
        return redkol;
    }

    public String getNum_year() {
        return num_year;
    }

    public String getKomplect() {
        return komplect;
    }

    public String getUndercover() {
        return undercover;
    }

    public int getVak() {
        return vak;
    }

    public int getRospech() {
        return rospech;
    }

    public String getPochta() {
        return pochta;
    }

    public String getIssn() {
        return issn;
    }

    public String getCover() {
        return cover;
    }

    public String getName() {
        return name;
    }

    public String getKeywords() {
        return keywords;
    }

    public int getJr_active() {
        return jr_active;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setMenu_name(String menu_name) {
        this.menu_name = menu_name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setRedaktor(String redaktor) {
        this.redaktor = redaktor;
    }

    public void setDescript(String descript) {
        this.descript = descript;
    }

    public void setRedkol(String redkol) {
        this.redkol = redkol;
    }

    public void setNum_year(String num_year) {
        this.num_year = num_year;
    }

    public void setKomplect(String komplect) {
        this.komplect = komplect;
    }

    public void setUndercover(String undercover) {
        this.undercover = undercover;
    }

    public void setVak(int vak) {
        this.vak = vak;
    }

    public void setRospech(int rospech) {
        this.rospech = rospech;
    }

    public void setPochta(String pochta) {
        this.pochta = pochta;
    }

    public void setIssn(String issn) {
        this.issn = issn;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public void setJr_active(int jr_active) {
        this.jr_active = jr_active;
    }
}
