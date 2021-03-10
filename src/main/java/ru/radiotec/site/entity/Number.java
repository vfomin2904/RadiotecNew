package ru.radiotec.site.entity;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
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

    @Length(min=1, max=10)
    @Column(name="num_num")
    private String name;

    @Column(name="num_descript")
    private String descript;

    @NotEmpty
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

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public String getYear() {
        if(year != null && year.length()>0){
            return year.substring(0,4);
        }else{
            return year;
        }

    }

    public void setYear(String year) {
        this.year = year;
    }

    @Override
    public int compareTo(Object o) {
        String[] num1 = this.getName().split("-");
        String[] num2 =  ((Number) o).getName().split("-");
        return Integer.parseInt(num1[0])  - Integer.parseInt(num2[0]);
    }

    @Override
    public String toString() {
        return this.getYear()+" : "+this.getName();
    }
}
