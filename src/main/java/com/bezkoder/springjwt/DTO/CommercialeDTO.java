package com.bezkoder.springjwt.DTO;

import com.bezkoder.springjwt.models.*;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CommercialeDTO {
    private Long id;
    private String username;
    private String email;
    private String name;
    private String phone;
    private String title;
    private String linkedIn;
    private Set<Role> roles = new HashSet<>();
    private List<Langues> languesList;
    private List<Formations> formationsList;
    private List<Experience> experienceList;
    private List<Skill> skillList;
    private List<Affectation> affectationList;
    private List<Certificats> certificatsList ;

    private Long commercialeId;

    private Long gestionnaireId;

    public String getTitle() {
        return title;
    }

    public String getLinkedIn() {
        return linkedIn;
    }

    public void setLinkedIn(String linkedIn) {
        this.linkedIn = linkedIn;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Langues> getLanguesList() {
        return languesList;
    }

    public Long getGestionnaireId() {
        return gestionnaireId;
    }

    public void setGestionnaireId(Long gestionnaireId) {
        this.gestionnaireId = gestionnaireId;
    }

    public Long getCommercialeId() {
        return commercialeId;
    }

    public void setCommercialeId(Long commercialeId) {
        this.commercialeId = commercialeId;
    }

    public List<Certificats> getCertificatsList() {
        return certificatsList;
    }

    public void setCertificatsList(List<Certificats> certificatsList) {
        this.certificatsList = certificatsList;
    }

    public List<Affectation> getAffectationList() {
        return affectationList;
    }

    public void setAffectationList(List<Affectation> affectationList) {
        this.affectationList = affectationList;
    }

    public List<Skill> getSkillList() {
        return skillList;
    }

    public void setSkillList(List<Skill> skillList) {
        this.skillList = skillList;
    }

    public List<Experience> getExperienceList() {
        return experienceList;
    }

    public void setExperienceList(List<Experience> experienceList) {
        this.experienceList = experienceList;
    }

    public List<Formations> getFormationsList() {
        return formationsList;
    }

    public void setFormationsList(List<Formations> formationsList) {
        this.formationsList = formationsList;
    }

    public void setLanguesList(List<Langues> languesList) {
        this.languesList = languesList;
    }

    public Long getId() {
        return id;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
