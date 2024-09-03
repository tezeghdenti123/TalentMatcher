package com.bezkoder.springjwt.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Consultant extends User {

    private String title;
    private String linkedIn;
    @OneToMany(mappedBy = "consultant", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Langues> languesList;
    @OneToMany(mappedBy = "consultant", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Formations> formationsList;
    @OneToMany(mappedBy = "consultant", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Experience> experienceList;
    @OneToMany(mappedBy = "consultant", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Skill> skillList;
    @OneToMany(mappedBy = "consultant", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference(value = "cons_aff")
    private List<Affectation> affectationList ;
    @OneToMany(mappedBy = "consultant", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Certificats> certificatsList ;
    @ManyToOne
    @JoinColumn(name = "commerciale_id")
    @JsonBackReference(value = "cons_com")
    private Commerciale commerciale;
    @ManyToOne
    @JoinColumn(name = "gestionnaireRH_id")
    @JsonBackReference(value = "cons_gest")
    private GestionnaireRH gestionnaireRH;


    public List<Affectation> getAffectationList() {
        return affectationList;
    }

    public void setAffectationList(List<Affectation> affectationList) {
        this.affectationList = affectationList;
    }

    public List<Formations> getFormationsList() {
        return formationsList;
    }

    public List<Experience> getExperienceList() {
        return experienceList;
    }

    public void setExperienceList(List<Experience> experienceList) {
        this.experienceList = experienceList;
    }

    public void setFormationsList(List<Formations> formationsList) {
        this.formationsList = formationsList;
    }

    public String getLinkedIn() {
        return linkedIn;
    }

    public void setLinkedIn(String linkedIn) {
        this.linkedIn = linkedIn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public List<Langues> getLanguesList() {
        return languesList;
    }

    public void setLanguesList(List<Langues> languesList) {
        this.languesList = languesList;
    }


    public List<Certificats> getCertificatsList() {
        return certificatsList;
    }

    public void setCertificatsList(List<Certificats> certificatsList) {
        this.certificatsList = certificatsList;
    }

    public void addLanguage(Langues langues){
        if(languesList ==null){
            languesList =new ArrayList<>();
            languesList.add(langues);
            langues.setConsultant(this);

        }
        else{
            languesList.add(langues);
            langues.setConsultant(this);
        }

    }

    public List<Skill> getSkillList() {
        return skillList;
    }

    public void setSkillList(List<Skill> skillList) {
        this.skillList = skillList;
    }

    public Commerciale getCommerciale() {
        return commerciale;
    }

    public GestionnaireRH getGestionnaireRH() {
        return gestionnaireRH;
    }

    public void setGestionnaireRH(GestionnaireRH gestionnaireRH) {
        this.gestionnaireRH = gestionnaireRH;
    }

    public void setCommerciale(Commerciale commerciale) {
        this.commerciale = commerciale;
    }
}
