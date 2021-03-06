package ru.radiotec.site.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="nomera")
public class Number implements Comparable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="num_id")
    private int id;

    @Column(name="jr_num")
    private int journalId;

    @Column(name="num_num")
    private String number;

    @Column(name="num_descript")
    private String descript;

    @Column(name="num_year")
    private String year;

    @Column(name="num_act")
    private int active;


    @ManyToOne
    @JoinColumn(name = "jr_num", referencedColumnName = "journ_id", insertable = false, updatable = false)
    private Journals journal;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "number")
    @OrderBy("id ASC")
    private Set<Section> sections = new HashSet<>();

    public Number(){

    }

    public Set<Section> getSections() {
        return this.sections;
    }
    public void setSections(Set<Section> sections) {
        this.sections = sections;
    }

    public void addSection(Section section) {
        section.setNumber(this);
        this.sections.add(section);
    }

    public Journals getJournal() {
        return this.journal;
    }

    public void setJournal(Journals journal) {
        this.journal = journal;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getJournalId() {
        return journalId;
    }

    public void setJournalId(int journalId) {
        this.journalId = journalId;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDescript() {
        return descript;
    }

    public void setDescript(String descript) {
        this.descript = descript;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public String getYear() {
        return year.substring(0,4);
    }

    public void setYear(String year) {
        this.year = year;
    }

    @Override
    public int compareTo(Object o) {
        String[] num1 = this.getNumber().split("-");
        String[] num2 =  ((Number) o).getNumber().split("-");
        return Integer.parseInt(num1[0])  - Integer.parseInt(num2[0]);
    }
}
