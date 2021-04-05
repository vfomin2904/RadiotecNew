package ru.radiotec.site.entity;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.*;

@Entity
@Table(name="journals")
public class Journals implements Comparable<Journals>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="journ_id")
    private int id;

    @NotEmpty
    @Column(name="menu_name")
    private String menuName;

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

    @NotEmpty
    @Column(name="journ_name")
    private String name;

    @Column(name="keywords")
    private String keywords;

    @Column(name="guide")
    private String guide;

    @Column(name="price_policy")
    private String pricePolicy;

    @Column(name="agreement")
    private String agreement;

    @Column(name="sending_materials")
    private String sendingMaterials;

    @Column(name="contacts")
    private String contacts;

    @Column(name="goals")
    private String goals;

    @Column(name="journals_sections")
    private String journalsSections;

    @Column(name="review")
    private String review;

    @Column(name="free_access")
    private String freeAccess;

    @Column(name="archiving")
    private String archiving;

    @Column(name="ethics")
    private String ethics;

    @Column(name="founder")
    private String founder;

    @Column(name="publication_fee")
    private String publicationFee;

    @Column(name="conflicts")
    private String conflicts;

    @Column(name="plagiarism")
    private String plagiarism;

    @Column(name="print")
    private String print;

    @Column(name="sendingArticle")
    private String sendingArticle;

    @Column(name="rules")
    private String rules;

    @Column(name="copyright")
    private String copyright;

    @Column(name="privacy")
    private String privacy;

    @Column(name="sponsors")
    private String sponsors;

    @Column(name="history")
    private String history;



    @NotEmpty
    @Column(name="menu_name_eng")
    private String menuNameEng;

    @Column(name="type_eng")
    private String typeEng;

    @Column(name="redaktor_eng")
    private String redaktorEng;

    @Column(name="descript_eng")
    private String descriptEng;

    @Column(name="redkol_eng")
    private String redkolEng;

    @Column(name="num_year_eng")
    private String num_yearEng;

    @Column(name="komplekt_eng")
    private String komplectEng;

    @Column(name="undercover_eng")
    private String undercoverEng;

    @NotEmpty
    @Column(name="journ_name_eng")
    private String nameEng;

    @Column(name="keywords_eng")
    private String keywordsEng;

    @Column(name="guide_eng")
    private String guideEng;

    @Column(name="price_policy_eng")
    private String pricePolicyEng;

    @Column(name="agreement_eng")
    private String agreementEng;

    @Column(name="sending_materials_eng")
    private String sendingMaterialsEng;

    @Column(name="contacts_eng")
    private String contactsEng;

    @Column(name="goals_eng")
    private String goalsEng;

    @Column(name="journals_sections_eng")
    private String journalsSectionsEng;

    @Column(name="review_eng")
    private String reviewEng;

    @Column(name="free_access_eng")
    private String freeAccessEng;

    @Column(name="archiving_eng")
    private String archivingEng;

    @Column(name="ethics_eng")
    private String ethicsEng;

    @Column(name="founder_eng")
    private String founderEng;

    @Column(name="publication_fee_eng")
    private String publicationFeeEng;

    @Column(name="conflicts_eng")
    private String conflictsEng;

    @Column(name="plagiarism_eng")
    private String plagiarismEng;

    @Column(name="print_eng")
    private String printEng;

    @Column(name="sending_article_eng")
    private String sendingArticleEng;

    @Column(name="rules_eng")
    private String rulesEng;

    @Column(name="copyright_eng")
    private String copyrightEng;

    @Column(name="privacy_eng")
    private String privacyEng;

    @Column(name="sponsors_eng")
    private String sponsorsEng;

    @Column(name="history_eng")
    private String historyEng;

    @Column(name="link")
    private String link;


    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    @Column(name="jr_active")
    private int active;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "journal")
    @OrderBy("id DESC")
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

    public int getActive() {
        return active;
    }

    public void setId(int id) {
        this.id = id;
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

    public void setActive(int active) {
        this.active = active;
    }

    public String getGuide() {
        return guide;
    }

    public void setGuide(String guide) {
        this.guide = guide;
    }

    public String getPricePolicy() {
        return pricePolicy;
    }

    public void setPricePolicy(String pricePolicy) {
        this.pricePolicy = pricePolicy;
    }

    public String getAgreement() {
        return agreement;
    }

    public void setAgreement(String agreement) {
        this.agreement = agreement;
    }

    public String getSendingMaterials() {
        return sendingMaterials;
    }

    public void setSendingMaterials(String sendingMaterials) {
        this.sendingMaterials = sendingMaterials;
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    public String getGoals() {
        return goals;
    }

    public void setGoals(String goals) {
        this.goals = goals;
    }

    public String getJournalsSections() {
        return journalsSections;
    }

    public void setJournalsSections(String journalsSections) {
        this.journalsSections = journalsSections;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getFreeAccess() {
        return freeAccess;
    }

    public void setFreeAccess(String freeAccess) {
        this.freeAccess = freeAccess;
    }

    public String getArchiving() {
        return archiving;
    }

    public void setArchiving(String archiving) {
        this.archiving = archiving;
    }

    public String getEthics() {
        return ethics;
    }

    public void setEthics(String ethics) {
        this.ethics = ethics;
    }

    public String getFounder() {
        return founder;
    }

    public void setFounder(String founder) {
        this.founder = founder;
    }

    public String getPublicationFee() {
        return publicationFee;
    }

    public void setPublicationFee(String publicationFee) {
        this.publicationFee = publicationFee;
    }

    public String getConflicts() {
        return conflicts;
    }

    public void setConflicts(String conflicts) {
        this.conflicts = conflicts;
    }

    public String getPlagiarism() {
        return plagiarism;
    }

    public void setPlagiarism(String plagiarism) {
        this.plagiarism = plagiarism;
    }

    public String getPrint() {
        return print;
    }

    public void setPrint(String print) {
        this.print = print;
    }

    public String getSendingArticle() {
        return sendingArticle;
    }

    public void setSendingArticle(String sendingArticle) {
        this.sendingArticle = sendingArticle;
    }

    public String getRules() {
        return rules;
    }

    public void setRules(String rules) {
        this.rules = rules;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public String getPrivacy() {
        return privacy;
    }

    public void setPrivacy(String privacy) {
        this.privacy = privacy;
    }

    public String getSponsors() {
        return sponsors;
    }

    public void setSponsors(String sponsors) {
        this.sponsors = sponsors;
    }

    public String getHistory() {
        return history;
    }

    public void setHistory(String history) {
        this.history = history;
    }

    public String getMenuNameEng() {
        return menuNameEng;
    }

    public void setMenuNameEng(String menuNameEng) {
        this.menuNameEng = menuNameEng;
    }

    public String getTypeEng() {
        return typeEng;
    }

    public void setTypeEng(String typeEng) {
        this.typeEng = typeEng;
    }

    public String getRedaktorEng() {
        return redaktorEng;
    }

    public void setRedaktorEng(String redaktorEng) {
        this.redaktorEng = redaktorEng;
    }

    public String getDescriptEng() {
        return descriptEng;
    }

    public void setDescriptEng(String descriptEng) {
        this.descriptEng = descriptEng;
    }

    public String getRedkolEng() {
        return redkolEng;
    }

    public void setRedkolEng(String redkolEng) {
        this.redkolEng = redkolEng;
    }

    public String getNum_yearEng() {
        return num_yearEng;
    }

    public void setNum_yearEng(String num_yearEng) {
        this.num_yearEng = num_yearEng;
    }

    public String getKomplectEng() {
        return komplectEng;
    }

    public void setKomplectEng(String komplectEng) {
        this.komplectEng = komplectEng;
    }

    public String getUndercoverEng() {
        return undercoverEng;
    }

    public void setUndercoverEng(String undercoverEng) {
        this.undercoverEng = undercoverEng;
    }

    public String getNameEng() {
        return nameEng;
    }

    public void setNameEng(String nameEng) {
        this.nameEng = nameEng;
    }

    public String getKeywordsEng() {
        return keywordsEng;
    }

    public void setKeywordsEng(String keywordsEng) {
        this.keywordsEng = keywordsEng;
    }

    public String getGuideEng() {
        return guideEng;
    }

    public void setGuideEng(String guideEng) {
        this.guideEng = guideEng;
    }

    public String getPricePolicyEng() {
        return pricePolicyEng;
    }

    public void setPricePolicyEng(String pricePolicyEng) {
        this.pricePolicyEng = pricePolicyEng;
    }

    public String getAgreementEng() {
        return agreementEng;
    }

    public void setAgreementEng(String agreementEng) {
        this.agreementEng = agreementEng;
    }

    public String getSendingMaterialsEng() {
        return sendingMaterialsEng;
    }

    public void setSendingMaterialsEng(String sendingMaterialsEng) {
        this.sendingMaterialsEng = sendingMaterialsEng;
    }

    public String getContactsEng() {
        return contactsEng;
    }

    public void setContactsEng(String contactsEng) {
        this.contactsEng = contactsEng;
    }

    public String getGoalsEng() {
        return goalsEng;
    }

    public void setGoalsEng(String goalsEng) {
        this.goalsEng = goalsEng;
    }

    public String getJournalsSectionsEng() {
        return journalsSectionsEng;
    }

    public void setJournalsSectionsEng(String journalsSectionsEng) {
        this.journalsSectionsEng = journalsSectionsEng;
    }

    public String getReviewEng() {
        return reviewEng;
    }

    public void setReviewEng(String reviewEng) {
        this.reviewEng = reviewEng;
    }

    public String getFreeAccessEng() {
        return freeAccessEng;
    }

    public void setFreeAccessEng(String freeAccessEng) {
        this.freeAccessEng = freeAccessEng;
    }

    public String getArchivingEng() {
        return archivingEng;
    }

    public void setArchivingEng(String archivingEng) {
        this.archivingEng = archivingEng;
    }

    public String getEthicsEng() {
        return ethicsEng;
    }

    public void setEthicsEng(String ethicsEng) {
        this.ethicsEng = ethicsEng;
    }

    public String getFounderEng() {
        return founderEng;
    }

    public void setFounderEng(String founderEng) {
        this.founderEng = founderEng;
    }

    public String getPublicationFeeEng() {
        return publicationFeeEng;
    }

    public void setPublicationFeeEng(String publicationFeeEng) {
        this.publicationFeeEng = publicationFeeEng;
    }

    public String getConflictsEng() {
        return conflictsEng;
    }

    public void setConflictsEng(String conflictsEng) {
        this.conflictsEng = conflictsEng;
    }

    public String getPlagiarismEng() {
        return plagiarismEng;
    }

    public void setPlagiarismEng(String plagiarismEng) {
        this.plagiarismEng = plagiarismEng;
    }

    public String getPrintEng() {
        return printEng;
    }

    public void setPrintEng(String printEng) {
        this.printEng = printEng;
    }

    public String getSendingArticleEng() {
        return sendingArticleEng;
    }

    public void setSendingArticleEng(String sendingArticleEng) {
        this.sendingArticleEng = sendingArticleEng;
    }

    public String getRulesEng() {
        return rulesEng;
    }

    public void setRulesEng(String rulesEng) {
        this.rulesEng = rulesEng;
    }

    public String getCopyrightEng() {
        return copyrightEng;
    }

    public void setCopyrightEng(String copyrightEng) {
        this.copyrightEng = copyrightEng;
    }

    public String getPrivacyEng() {
        return privacyEng;
    }

    public void setPrivacyEng(String privacyEng) {
        this.privacyEng = privacyEng;
    }

    public String getSponsorsEng() {
        return sponsorsEng;
    }

    public void setSponsorsEng(String sponsorsEng) {
        this.sponsorsEng = sponsorsEng;
    }

    public String getHistoryEng() {
        return historyEng;
    }

    public void setHistoryEng(String historyEng) {
        this.historyEng = historyEng;
    }

    @Override
    public int compareTo(Journals o) {
        return this.getId()-o.getId();
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
